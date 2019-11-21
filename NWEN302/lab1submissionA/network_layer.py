#!/usr/bin/env python3

from transport_layer import StopAndWaitHeader
from switchyard.lib.userlib import *
from expiringdict import ExpiringDict

class IpProcessor():

    def __init__(self, inet6, inet4):
        self.ipv6_address = inet6
        self.ipv4_address = inet4
        self.stopandwait = None
        self.ethernet = None

    def setstack(self, ethernet, stopandwait):
        self.ethernet = ethernet
        self.stopandwait = stopandwait # transport layer


        '''
        NOTE TO STUDENTS:

        The following address maps are hardcoded.
        Your implementation will start with an empty table.
        ARP and NDP protocols should then populate these tables.

        Note:
        Dictionaries may not necessarily be the best data structure for your table.
        '''
        
        # advanced assignment requirements (limited size and time)
        self.ipv4_mac_map = ExpiringDict(max_len=100, max_age_seconds=30)
        self.ipv6_mac_map = ExpiringDict(max_len=100, max_age_seconds=30)

        #original hard coded mappings
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

    def accept_packet(self, packet_data, etype):

        if etype == EtherType.ARP:
            if packet_data[Arp].operation == ArpOperation.Request:
                self.arp_request(packet_data, etype)
            elif packet_data[Arp].operation == ArpOperation.Reply:
                self.arp_reply(packet_data, etype)

        # NDP stuff, Incomplete    
        elif (etype == EtherType.IPv6 and packet_data[IPv6].nextheader == 0x3A): #0x3A is IPv6-ICMP
            if packet_data[ICMPv6].icmptype == ICMPv6Type.NeighborSolicitation):
            #self.ethernet.send_packet(packet_data, src_link, EtherType.IPv6)
            elif: packet_data[ICMPv6].icmptype == ICMPv6Type.NeighborAdvertisement):
            #self.ethernet.send_packet(packet_data, src_link, EtherType.IPv6)

        
        #UDP header handling
        elif (etype == EtherType.IPv4):
	            # Delete the IP header and then leave only the StopAndWait header to be processed
                header = packet_data[IPv4]
	            del packet_data[0]
                if (header.protocol == IPProtocol.UDP:
                    # remove UDP header
                    del packet_data[0]
	                self.stopandwait.accept_packet(packet_data, src_address=header.src)
                    
        else:
            del packet_data[0]
            self.stopandwait.accept_packet(packet_data, src_address=packet_data[0].src)


    def arp_request(self, packet_data, etype):
        #check if this is correct target, if so create ARP reply
        if packet_data[Arp].targetprotoaddr == self.ipv4.address:
            arp_res_reply = Arp()
            arp_res_reply.operation = ArpOperation.Reply
            arp_res_reply.senderprotoaddr = self.ipv4_address
            arp_res_reply.targethwaddr = packet_data[Arp].senderhwaddr
            arp_res_reply.targetprotoaddr = packet_data[Arp].senderprotoaddr
            #send to link layer to add mac address
            self.ethernet.send_packet(arp_res_reply, packet_data[Arp}.senderhwaddr, etype)
            
            
    def arp_reply(self, packet_data, etype):
        #check if this is correct target, if so handle ARP reply
        if packet_data[Arp].targetprotoaddr == self.ipv4.address:
            #add ip-mac address mapping
            self.ipv4_mac_map[packet_data[Arp].senderprotoaddr] = packet_data[Arp].senderhwaddr
    
    def send_packet(self, packet_data, dst_ip):
        if type(dst_ip) == IPv6Address:
        	# check if ip-mac mapping exists. send ICMP request if not.
            if self.ipv6_mac_map.get(dst_ip) is None:
                ipv6 = IPv6()
                ipv6.nextheader = IPProtocol.ICMPv6
                ipv6.src = self.ipv6_address
                ipv6.dst = dst_ip
                icmpv6 = ICMPv6()
                icmpv6.icmpv6type = ICMPv6Type.NeighborSolicitation
                ipv6 += icmpv6
                self.ethernet.send_packet(ipv6, "ff:ff:ff:ff:ff:ff")

        else:
            # otherwise send ipv6:
            ipv6 = IPv6()
            ipv6.src = self.ipv6_address
            ipv6.dst = dst_ip
            ipv6.nextheader == IPProtocol.UDP
            ipv6 += packet_data
	        self.ethernet.send_packet(ipv6, self.ipv6_mac_map.get(dst_ip), EtherType.IPv6)

        elif type(dst_ip) == IPv4Address:
        	# check if ip-mac mapping exists. send ARP request if not.
            if self.ipv4_mac_map.get(dst_ip) is None:
                arp_req = Arp()
                arp_req.operation = ArpOperation.Request
                arp_req.senderprotoaddr = self.ipv4_address
                arp_req.targethwaddr = 'ff:ff:ff:ff:ff:ff'
                arp_req.targetprotoaddr = dst_ip
                
                self.ethernet.send_packet(arp_req, 'ff:ff:ff:ff:ff:ff', EtherType.ARP)

            else:
        	    # otherwise send ipv4 UDP
                if (type(packet_data) == type(StopAndWaitHeader())):
                    ipv4 = Ipv4()
                    ipv4.src = self.ipv4_address
                    ipv4.dst = dst_ip
                    ipv4.protocol = IPProtocol.UDP
                
                self.ethernet.send_packet(ipv4 + packet_data, self.ipv4_mac_map.get(dst_ip), EtherType.IPv4)

    def __str__(self):
        return "IP network layer ({} & {})".format(self.ipv6_address, self.ipv4_address)
