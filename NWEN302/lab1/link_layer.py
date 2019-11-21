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
        
        print("Accepting: ")
        print(packet_data)
        
        # save EtherType to send to network layer for processing
        etype = packet_data[Ethernet].ethertype
        
        # remove first header
        del packet_data[Ethernet]  

        self.ip.accept_packet(packet_data, etype)

    def send_packet(self, packet_data, dst_mac, etype):
        
        if etype == EtherType.ARP:
            packet_data.senderhwaddr = self.mac_address
       # elif (etype == EtherType.IPv6 and packet_data[IPv6].nextheader == 0x3A and (packet_data[ICMPv6].icmptype == ICMPv6Type.NeighborSolicitation or packet_data[ICMPv6].icmptype == ICMPv6Type.NeighborAdvertisement)):
        #    packet_data[ICMPv6].icmpdata.options.append(ICMPv6OptionSourceLinkLayerAddress(address=self.mac_address))
       #     
        eth = Ethernet(src=self.mac_address, dst=dst_mac, ethertype=etype)

        
        packet = Packet() + eth + packet_data
        print("Sending: ")
        print(packet)
        
        self.physical.send_packet(self.interface, packet)
        

    def __str__(self):
        return "Ethernet link layer ({})".format(self.ipv6_address)
