# Copyright (C) 2011 Nippon Telegraph and Telephone Corporation.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
# implied.
# See the License for the specific language governing permissions and
# limitations under the License.

from ryu.base import app_manager
from ryu.controller import ofp_event
from ryu.controller.handler import CONFIG_DISPATCHER, MAIN_DISPATCHER, DEAD_DISPATCHER
from ryu.controller.handler import set_ev_cls
from ryu.ofproto import ofproto_v1_3
from ryu.lib.packet import packet
from ryu.lib.packet import ethernet
from ryu.lib.packet import ether_types

from ryu.lib.packet import ipv4 # TASK ONE 
from ryu.lib import hub # TASK TWO
from operator import attrgetter #TASK TWO

#addresses assigned by mininet, incrementations of 10.0.0.X for hosts h1,h2 and h3,
h1_ipv4 = '10.0.0.1'
h2_ipv4 = '10.0.0.2'
h3_ipv4 = '10.0.0.3'
# variables for counting traffic in and out of h1
h1_in_count = 0
h1_out_count = 0 
h1_total_count = 0
h2_out_count = 0
h3_out_count = 0
MAX_COUNT = 10

#Registration of the Simple Switch Controller Application
class SimpleSwitch13(app_manager.RyuApp):
    alueOFP_VERSIONS = [ofproto_v1_3.OFP_VERSION]


#Initilizing the Controller Application
    def __init__(self, *args, **kwargs):
        super(SimpleSwitch13, self).__init__(*args, **kwargs)
        self.mac_to_port = {}
	# TASK TWO
	self.datapaths = {}
	self.monitor_thread = hub.spawn(self._monitor)
#Listening for New Switches
#Registering an Event Handler
    @set_ev_cls(ofp_event.EventOFPSwitchFeatures, CONFIG_DISPATCHER)
    def switch_features_handler(self, ev):
        datapath = ev.msg.datapath
        ofproto = datapath.ofproto
        parser = datapath.ofproto_parser

        # install table-miss flow entry
        #
        # We specify NO BUFFER to max_len of the output action due to
        # OVS bug. At this moment, if we specify a lesser number, e.g.,
        # 128, OVS will send Packet-In with invalid buffer_id and
        # truncated packet data. In that case, we cannot output packets
        # correctly.  The bug has been fixed in OVS v2.1.0.
#Building the Flow Entry
        match = parser.OFPMatch()
        actions = [parser.OFPActionOutput(ofproto.OFPP_CONTROLLER,
                                          ofproto.OFPCML_NO_BUFFER)]
        self.add_flow(datapath, 0, match, actions)
#Add Flow Helper Function
    def add_flow(self, datapath, priority, match, actions, buffer_id=None):
        ofproto = datapath.ofproto
        parser = datapath.ofproto_parser
#Constructing the Instructions 
        inst = [parser.OFPInstructionActions(ofproto.OFPIT_APPLY_ACTIONS,
                                             actions)]
#Constructing the Flow Mod
        if buffer_id:
            mod = parser.OFPFlowMod(datapath=datapath, buffer_id=buffer_id,
                                    priority=priority, match=match,
                                    instructions=inst)
        else:
            mod = parser.OFPFlowMod(datapath=datapath, priority=priority,
                                    match=match, instructions=inst)
#Sending the Flow Mod
        datapath.send_msg(mod)
#Packet In Handler
    @set_ev_cls(ofp_event.EventOFPPacketIn, MAIN_DISPATCHER)
    def _packet_in_handler(self, ev):
	global h1_out_count, h2_out_count, h3_out_count
#Sanity Checking for Message Length
        # If you hit this you might want to increase
        # the "miss_send_length" of your switch
        if ev.msg.msg_len < ev.msg.total_len:
            self.logger.debug("packet truncated: only %s of %s bytes",
                              ev.msg.msg_len, ev.msg.total_len)
#Pulling Important Data
        msg = ev.msg
        datapath = msg.datapath
        ofproto = datapath.ofproto
        parser = datapath.ofproto_parser
        in_port = msg.match['in_port']
#Parsing the Submitted Packet
        pkt = packet.Packet(msg.data)
        eth = pkt.get_protocols(ethernet.ethernet)[0]
#Ignoring the LLDP Packets
        if eth.ethertype == ether_types.ETH_TYPE_LLDP:
            # ignore lldp packet
            return
#Learning the Source MAC
        dst = eth.dst
        src = eth.src
	self.logger.info('src: %s', src)

        dpid = datapath.id
        self.mac_to_port.setdefault(dpid, {})

        self.logger.info("packet in %s %s %s %s", dpid, src, dst, in_port)

        # learn a mac address to avoid FLOOD next time.
        self.mac_to_port[dpid][src] = in_port
#Destination Lookup
        if dst in self.mac_to_port[dpid]:
            out_port = self.mac_to_port[dpid][dst]
        else:
            out_port = ofproto.OFPP_FLOOD
	
	
#Implementation of IPv4 Traffic Blocking (TASK ONE)
	if self.check_if_block(pkt):
	    actions = []
	    #grab ipv4 source and destination from packet
	    ipv4_data = pkt.get_protocols(ipv4.ipv4)[0]
	    ipv4_src = ipv4_data.src
	    ipv4_dst = ipv4_data.dst
            #block flow
            self.drop_flow(datapath, ipv4_src, ipv4_dst, ofproto, parser)

	else:
            actions = [parser.OFPActionOutput(out_port)]
#Adding a Flow Entry
        # install a flow to avoid packet_in next time
            if out_port != ofproto.OFPP_FLOOD:
                match = parser.OFPMatch(in_port=in_port, eth_dst=dst)
            # verify if we have a valid buffer_id, if yes avoid to send both
            # flow_mod & packet_out
                if msg.buffer_id != ofproto.OFP_NO_BUFFER:
                    self.add_flow(datapath, 1, match, actions, msg.buffer_id)
                    return
                else:
                    self.add_flow(datapath, 1, match, actions)
#Forwarding the Packet Recieved by the Controller
        data = None
        if msg.buffer_id == ofproto.OFP_NO_BUFFER:
            data = msg.data

        out = parser.OFPPacketOut(datapath=datapath, buffer_id=msg.buffer_id,
                                  in_port=in_port, actions=actions, data=data)
        datapath.send_msg(out)
# TASK ONE METHODS ---------------------------------------------------------------------------------

    def drop_flow(self, datapath, ip_src, ip_dst, ofproto, parser):
	#eth_type=0x0800 added from: https://mailman.stanford.edu/pipermail/mininet-discuss/2016-March/006767.html
	#information from https://stackoverflow.com/questions/41023354/ryu-controller-drop-packet
	#to help get the mod instuction working to drop flow.
        match = parser.OFPMatch(ipv4_src=ip_src, eth_type=0x0800)
        instruction = [parser.OFPInstructionActions(ofproto.OFPIT_CLEAR_ACTIONS, [])]
        mod = parser.OFPFlowMod(datapath = datapath,
	    priority = ofproto.OFP_DEFAULT_PRIORITY,
	    command=ofproto.OFPFC_ADD,         
            match = match,
            instructions = instruction)
        datapath.send_msg(mod)

    def check_if_block(self, p_data):
        for protocol in p_data.protocols:
            if protocol.protocol_name == 'ipv4':
		if (protocol.src == h1_ipv4 and h1_out_count >= MAX_COUNT):
		    print h1_out_count
   		    return True
		if (protocol.src == h2_ipv4 and h2_out_count >= MAX_COUNT):
		    return True
		if (protocol.src == h3_ipv4 and h3_out_count >= MAX_COUNT):
		    return True
        return False
# TASK ONE METHODS END --------------------------------------------------------------------------

# TASK TWO METHODS -------------------------------------------------------------------------------
# From: https://osrg.github.io/ryu-book/en/html/switching_hub.html#ch-switching-hub
    @set_ev_cls(ofp_event.EventOFPStateChange,
                [MAIN_DISPATCHER, DEAD_DISPATCHER])
    def _state_change_handler(self, ev):
        datapath = ev.datapath
        if ev.state == MAIN_DISPATCHER:
            if datapath.id not in self.datapaths:
                self.logger.debug('register datapath: %016x', datapath.id)
                self.datapaths[datapath.id] = datapath
        elif ev.state == DEAD_DISPATCHER:
            if datapath.id in self.datapaths:
                self.logger.debug('unregister datapath: %016x', datapath.id)
                del self.datapaths[datapath.id]

    def _monitor(self):
        while True:
            for dp in self.datapaths.values():
                self._request_stats(dp)
            hub.sleep(10)

    def _request_stats(self, datapath):
        self.logger.debug('send stats request: %016x', datapath.id)
        ofproto = datapath.ofproto
        parser = datapath.ofproto_parser

        req = parser.OFPFlowStatsRequest(datapath)
        datapath.send_msg(req)

        req = parser.OFPPortStatsRequest(datapath, 0, ofproto.OFPP_ANY)
        datapath.send_msg(req)

    @set_ev_cls(ofp_event.EventOFPFlowStatsReply, MAIN_DISPATCHER)
    def _flow_stats_reply_handler(self, ev):
	global h1_in_count, h1_out_count, h1_total_count, h2_out_count, h3_out_count, MAX_COUNT
        body = ev.msg.body

#        for stat in sorted([flow for flow in body if flow.priority == 1],
#                           key=lambda flow: (flow.match['in_port'],
#                                             flow.match['eth_dst'])):
#		DO STUFF HERE TO GET INFO TO DELETE FLOW
    @set_ev_cls(ofp_event.EventOFPPortStatsReply, MAIN_DISPATCHER)
    def _port_stats_reply_handler(self, ev):
	global h1_in_count, h1_out_count, h1_total_count, h2_out_count, h3_out_count, MAX_COUNT
        body = ev.msg.body

        for stat in sorted(body, key=attrgetter('port_no')):
            if stat.port_no == 1:
                h1_out_count = stat.tx_packets
            elif stat.port_no == 2:
                h2_out_count = stat.tx_packets
            elif stat.port_no == 3:
                h3_out_count = stat.tx_packets
	
	#Print out h1 traffic information
	self.logger.info('------------------------------------------------------------')
	self.logger.info('h1 out traffic: %s, h2 out traffic: %s, h3 out traffic: %s, MAX TRAFFIC COUNT: %s', h1_out_count, h2_out_count, h3_out_count, MAX_COUNT)
	self.logger.info('------------------------------------------------------------')

# TASK TWO METHODS END ---------------------------------------------------------------------------------------------------
