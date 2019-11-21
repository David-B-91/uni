'''
Ethernet learning switch in Python.

Note that this file currently has the code to implement a "hub"
in it, not a learning switch.  (I.e., it's currently a switch
that doesn't learn.)
'''
from switchyard.lib.userlib import *
from expiringdict import ExpiringDict

def main(net):
    my_interfaces = net.interfaces() 
    mymacs = [intf.ethaddr for intf in my_interfaces]
    #not the most robust implementation but handles basic timeout of stored addresses
    cache = ExpiringDict(max_len=100, max_age_seconds=30)
    
    while True:
        try:
            timestamp,input_port,packet = net.recv_packet()
        except NoPackets:
            continue
        except Shutdown:
            return
        
        #if switch does not have incoming address mapped already, then add it to cache.
        if cache.get(packet[0].src) is None:
            cache[packet[0].src] = input_port

        log_debug ("In {} received packet {} on {}".format(net.name, packet, input_port))
        if packet[0].dst in mymacs:
            log_debug ("Packet intended for me")
        else:
            if: cache.get(packet[0].dst) is not None:
                # send packet using mac of destination, if switch knows it.
                net.send_packet(cache.get(packet[0].dst), packet)
            else:
                for intf in my_interfaces:
                    if input_port != intf.name:
                        log_debug ("Flooding packet {} to {}".format(packet, intf.name))
                        net.send_packet(intf.name, packet)
    net.shutdown()
