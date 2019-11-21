#!/usr/bin/env python3

from switchyard.lib.userlib import *

class EthernetProcessor():

    def __init__(self, mac, iface):
        self.mac_address = mac
        self.interface = iface
        self.ip = None
        self.physical = None

    def setstack(self, ip, physical):
        self.ip = ip
        self.physical = physical

    def accept_packet(self, packet_data):

        # remove first header
        eth = packet_data.get_header_by_name("Ethernet")
        del packet_data[0]

        print("TODO - EthernetProcessor.accept_packet()")

        self.ip.accept_packet(packet_data)

    def send_packet(self, packet_data, dst_mac):

        # decide EtherType
        placeholder = 0xffff
        print("TODO - EthernetProcessor.send_packet()")

        '''
        NOTE TO STUDENTS:

        This layer currently assumes an invalid EtherType of 0xffff.

        You need to correctly assign the Ethertype before the receiving host
          can parse the packet correctly.

        Which the accept_packet method works at the moment you may wish to add
          some sort of checks to it ...
        '''

        eth = Ethernet(src=self.mac_address, dst=dst_mac, ethertype=placeholder)
        p = Packet() + eth + packet_data
        self.physical.send_packet(self.interface, p)

    def __str__(self):
        return "Ethernet link layer ({})".format(self.ipv6_address)
