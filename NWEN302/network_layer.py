#!/usr/bin/env python3

from switchyard.lib.userlib import *

class IpProcessor():

    def __init__(self, inet6, inet4):
        self.ipv6_address = inet6
        self.ipv4_address = inet4
        self.stopandwait = None
        self.ethernet = None

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

    def accept_packet(self, packet_obj, etype):

        ######################################################################


        if etype == EtherType.ARP and packet_obj[Arp].operation == ArpOperation.Request:
            #add_arp(packet_obj)
            print("To be completed")

        elif etype == EtherType.ARP and packet_obj[Arp].operation == ArpOperation.Reply:
            #add_arp(packet_obj)
            print("To be completed")
            
        elif (etype == EtherType.IPv6 
            and packet_obj[IPv6].nextheader == 0x3A 
            and packet_obj[ICMPv6].icmptype == ICMPv6Type.NeighborSolicitation):  
            # search through options for source link layer address
            #src_link = add_ndp(packet_obj)
            print("To be completed")

            #self.ethernet.send_packet(packet_resp, src_link, EtherType.IPv6)

        elif (etype == EtherType.IPv6 
            and packet_obj[IPv6].nextheader == 0x3A 
            and packet_obj[ICMPv6].icmptype == ICMPv6Type.NeighborAdvertisement):
            # if advertisement, collect info only
            #add_ndp(packet_obj)
            print("To be completed")

        else:
	        # Delete the IP header and then leave only the StopAndWait header to be processed
	        print("This should be a StopAndWait packet\n")
	        del packet_obj[0]
	        self.stopandwait.accept_packet(packet_obj, src_address=packet_obj[0].src)


        ######################################################################

    def send_packet(self, packet_data, dst_ip):
        # log_debug("IpProcessor received packet to be sent")

        # protocol = IPProtocol.StopAndWait
        # print(protocol)
        # print("TODO - IpProcessor.send_packet()")

        if type(dst_ip) == IPv6Address:
        	# First check if the MAC/IPv6 mapping exists, if it doesn't then send out a request.

        	# Otherwise:
	        packet_obj = Packet()
	        packet_obj += IPv6(src=self.ipv6_address, dst=dst_ip, nextheader=IPProtocol.UDP)
	        packet_obj += packet_data
	        self.ethernet.send_packet(packet_obj, self.ipv6_2mac[dst_ip], EtherType.IPv6)

        elif type(dst_ip) == IPv4Address:
        	# First check if the MAC/IPv4 mapping exists, if it doesn't then send out a request.

        	# Otherwise this:
            packet_obj = Packet()
            packet_obj += IPv4(src=self.ipv4_address, dst=dst_ip, protocol=IPProtocol.UDP)
            packet_obj += packet_data

            self.ethernet.send_packet(packet_obj, self.ipv4_2mac[dst_ip], EtherType.IPv4)

    def __str__(self):
        return "IP network layer ({} & {})".format(self.ipv6_address, self.ipv4_address)
