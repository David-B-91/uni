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

    def accept_packet(self, packet_obj):
        
        print("Accepting: ")
        print(packet_obj)
        
        # save EtherType to send to network layer for processing
        etype = packet_obj[Ethernet].ethertype
        
        # remove first header
        del packet_obj[Ethernet]  

        self.ip.accept_packet(packet_obj, etype)

    def send_packet(self, packet_obj, dst_mac, etype=0xffff):

        eth = Ethernet(src=self.mac_address, dst=dst_mac, ethertype=etype)
        
        # collect headers from incoming packet object and add to output packet object
        # don't forget to tack the Ethernet header on the front
        packet_out = Packet()
        for h in packet_obj:
            packet_out.add_header(h)
        packet_out.prepend_header(eth)
        
        # attach MAC address to ARP/NDP header since it's recorded here (and if there is one)
        if etype == EtherType.ARP:
            packet_out[Arp].senderhwaddr = self.mac_address
        elif (etype == EtherType.IPv6 
            and packet_out[IPv6].nextheader == 0x3A 
            and (packet_out[ICMPv6].icmptype == ICMPv6Type.NeighborSolicitation
            or packet_out[ICMPv6].icmptype == ICMPv6Type.NeighborAdvertisement)):
            packet_out[ICMPv6].icmpdata.options.append(
                ICMPv6OptionSourceLinkLayerAddress(address=self.mac_address))
        
        print("Sending: ")
        print(packet_out)
        
        self.physical.send_packet(self.interface, packet_out)
        

    def __str__(self):
        return "Ethernet link layer ({})".format(self.ipv6_address)
