#!/usr/bin/env python3

from switchyard.lib.userlib import *

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

    def accept_packet(self, packet_data):
        # log_debug("IpProcessor received packet from below")

        print("TODO - IpProcessor.accept_packet()")

        YOU_NEED_TO_FIX_THIS = IPv4Address("0.0.0.0")

        self.stopandwait.accept_packet(packet_data, src_address=YOU_NEED_TO_FIX_THIS)

    def send_packet(self, packet_data, dst_ip):
        # log_debug("IpProcessor received packet to be sent")

        protocol = IPProtocol.StopAndWait
        print("TODO - IpProcessor.send_packet()")

        if type(dst_ip) == IPv6Address:
            if dst_ip not in self.ipv6_2mac:
                self.ethernet.send_packet(packet_data, "00:00:00:00:00:00")
            else:
                self.ethernet.send_packet(packet_data, self.ipv6_2mac[dst_ip])

        if type(dst_ip) == IPv4Address:
            if dst_ip not in self.ipv4_2mac:
                self.ethernet.send_packet(packet_data, "00:00:00:00:00:00")
            else:
                self.ethernet.send_packet(packet_data, self.ipv4_2mac[dst_ip])

    def __str__(self):
        return "IP network layer ({} & {})".format(self.ipv6_address, self.ipv4_address)
