#!/usr/bin/env python3
# Author Tom Edward Clark

from transport_layer import StopAndWaitHeader
from switchyard.lib.userlib import *
from expiringdict import ExpiringDict

class IpProcessor():

    def __init__(self, inet6, inet4):
        self.ipv6_address = inet6
        self.ipv4_address = inet4
        self.stopandwait = None
        self.ethernet = None

        '''
        NOTE TO STUDENTS:

        The following address maps are hardcoded.
        Your implementation will start with an empty table.
        ARP and NDP protocols should then populate these tables.

        Note:
        Dictionaries may not necessarily be the best data structure for your table.
        '''
        # my_dict=dict()  # dictionary table declare

        self.ipv4_cache = ExpiringDict(max_len=35, max_age_seconds=30)  # Dictionary initilaised with traits attaching my_dict
        self.ipv6_cache = ExpiringDict(max_len=35, max_age_seconds=30)  # Dictionary initilaised with traits attaching my_dict

        self.ipv6_2mac = {
            IPv6Address("fe80::200:ff:fe00:1"): EthAddr("00:00:00:00:00:01"),
            IPv6Address("fe80::200:ff:fe00:2"): EthAddr("00:00:00:00:00:02"),
            IPv6Address("fe80::200:ff:fe00:3"): EthAddr("00:00:00:00:00:03"),
            IPv6Address("fe80::200:ff:fe00:4"): EthAddr("00:00:00:00:00:04")
        }

        self.ipv4_2mac = {
            IPv4Address("10.0.0.1"): EthAddr("00:00:00:00:00:01"),
            IPv4Address("10.0.0.2"): EthAddr("00:00:00:00:00:02"),
            IPv4Address("10.0.0.3"): EthAddr("00:00:00:00:00:03"),
            IPv4Address("10.0.0.4"): EthAddr("00:00:00:00:00:04")
        }

    def setstack(self, ethernet, stopandwait):
        self.ethernet = ethernet
        self.stopandwait = stopandwait

    '''
    NOTE TO STUDENTS:

    You will need to implement the basic actions of the network layer.
    Currently it simply passes data between the layer above and below,
      skipping a vital step ...
    Think about what actions belong in this header and add them befor
      attempting to implement any ARP/NDP behaviour.

    * accept_packet():
        - Called from the network layer below, passes packet data up the stack
        - Processes IP information
    * send_packet():
        - Called from the network layer above, passes packet data down the stack
        - Adds IP information

    You are welcome to change the arguments for these functions or add other
      similar/complementary functions to assist in processessing. However, this
      is not essential for completing the lab.
    '''

    def accept_packet(self, pkt):

        # Get Header of packet_data
        ethertype = pkt[Ethernet].ethertype

        # Delete Ethernet header
        del pkt[0]

        # Handle different header protocols - arp , ipv4, ipv6
        if ethertype == EtherType.ARP:
            # ARP Type Handler
            arp_hd = pkt.get_header_by_name("Arp")
            if arp_hd.operation == ArpOperation.Request:
                self.handleArpRequest(pkt)
            elif arp_hd.operation == ArpOperation.Reply:
                self.handleArpReply(pkt)

        elif ethertype == EtherType.IPv4 or ethertype == EtherType.IP:
            ipv4_hd = pkt.get_header_by_name("IPv4")
            del pkt[0]
            # IPv$ Type Handler & get UDP
            if ipv4_hd.protocol == IPProtocol.UDP:
                # Delete the UDP Header
                del pkt[0]
                src_addr = ipv4_hd.src
                self.stopandwait.accept_packet(pkt, src_address=src_addr)
            else:
                print("Error - Non UDP type packet \n")

        elif ethertype == EtherType.IPv6:
            # UDP or ICMP
            ipv6_hd = pkt.get_header_by_name("IPv6")
            del pkt[0]
            print("IPv6type: ", ipv6_hd.nextheader, "\n")

            if(ipv6_hd.nextheader == IPProtocol.ICMPv6):
                del pkt[0]
            else:
                print("Network Layer - AP - Packet Lossed ( Not covered by conditions )\n")


    # Send them Arp request
    def handleArpRequest(self, pkt):

        arp_req_hd = pkt.get_header_by_name("Arp")
        # print("Network layer - HandleArpRequest: ", arp_req_hd.senderprotoaddr, " -> ", arp_req_hd.targetprotoaddr)
        # Check if the destination IP matches IP
        if arp_req_hd.targetprotoaddr == self.ipv4.address:

            # Create ARP
            arp = ARP( operation = ArpOperation.Reply,
                       senderprotoaddr = self.ipv4_address,
                       targethwaddr = arp_req_hd.senderhwaddr,
                       targetprotoaddr = arp_req_hd.senderprotoaddr
            )
            self.ethernet.send_packet(arp, arp_req_hd.senderhwaddr)
        else:
            print("Ip address is not ARP target address")


    # Recieve Arp Reply
    def handleArpReply(self, pkt):

        # Grab pkt header
        arp_rep_hd = pkt.get_header_by_name("Arp")

        # Check if for this ip
        if arp_rep_hd.targetprotoaddr == self.ipv4_address:

            # Add to expiring dictionary ( The temp cache to hold ip addr to mac addr connections)
            self.ipv4_cache[arp_rep_hd.senderprotoaddr] = arp_rep_hd.senderhwaddr

            print("Creating new IPv4 Cache Entry: ", arp_rep_hd.senderprotoaddrm, " -> ", arp_rep_hd.senderhwaddr, "\n")


    # Send IPv4 if there is entry in the table otherwise send arp
    def sendIPv4(self, pkt, dst_ip):

        if self.ipv4_cache.get(dst_ip) is None:

            arp = ARP ( operation=ArpOperation.Request,
                        senderprotoaddr=self.ipv4_address,
                        targethwaddr='ff:ff:ff:ff:ff:ff',
                        targetprotoaddr=dst_ip
            )

            # Add the arp header onto the packet_data
            self.ethernet.send_packet(arp, "ff:ff:ff:ff:ff:ff")
        else:
            # Send IPv4 with UDP header
            if(type(pkt) == type(StopAndWaitHeader())):
                ipv4 = IPv4(protocol = IPProtocol.UDP, src=self.ipv4_address, dst=dst_ip) + pkt
                self.ethernet.send_packet(ipv4, self.ipv4_cache.get(dst_ip))

    # Send IPv6 request
    def sendIPv6(self, pkt, dst_ip):

        if self.ipv6_cache.get(dst_ip) is None:
            ipv6 = IPv6(nextheader = IPProtocol.ICMPv6,
            src = self.ipv6_address,
            dst = dst_ip
            ) + ICMPv6(icmpv6type = ICMPv6Type.NeighborSolicitation)
        self.ethernet.send_packet(ipv6, "ff:ff:ff:ff:ff:ff")


    # Send Packet Down a layer ( To Link Layer )
    def send_packet(self, pkt, dst_ip):

        if type(dst_ip) == IPv6Address:
            self.sendIPv6(pkt, dst_ip)
        elif type(dst_ip) == IPv4Address:
            # IPv4 Handles ARP requests
            self.sendIPv4(pkt, dst_ip)


    def __str__(self):
        return "IP network layer ({} & {})".format(self.ipv6_address, self.ipv4_address)
