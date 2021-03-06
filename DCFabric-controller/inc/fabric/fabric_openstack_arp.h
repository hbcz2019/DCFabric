#ifndef INC_FABRIC_FABRIC_OPENSTACK_ARP_H_
#define INC_FABRIC_FABRIC_OPENSTACK_ARP_H_
#include "gnflush-types.h"
#include "fabric_host.h"
#include "forward-mgr.h"

#define SECURITY_CHECK_IPPACKET_INVALID  					0
#define SECURITY_CHECK_IPPACKET_NOT_CAMEFROM_SECURITY  		1
#define SECURITY_CHECK_IPPACKET_CAMEFROM_SECURITY_THROUGH  	2
#define SECURITY_CHECK_IPPACKET_CAMEFROM_SECURITY_DENY		3




// void fabric_openstack_arp_handle(gn_switch_t *sw, packet_in_info_t *packet_in);
//void fabric_openstack_ip_handle(gn_switch_t *sw, packet_in_info_t *packet_in);
p_fabric_host_node openstack_save_host_info(gn_switch_t *sw,UINT1* sendmac,UINT4 sendip, UINT4 targetip, UINT4 inport);
p_fabric_host_node openstack_find_dst_port(p_fabric_host_node src_node,UINT4 targetip);
p_fabric_host_node openstack_find_ip_dst_port(p_fabric_host_node src_node,UINT4 targetip);
INT4 openstack_arp_flood(p_fabric_host_node src_port,UINT4 sendip,UINT4 targetip,packet_in_info_t *packet_in);
INT4 openstack_arp_reply(p_fabric_host_node src_port,p_fabric_host_node dst_port,UINT4 sendip,UINT4 targetip,packet_in_info_t *packet_in);
INT4 openstack_arp_remove_ip_from_flood_list(UINT4 sendip);
INT4 openstack_arp_reply_output(UINT8 external_dpid,p_fabric_host_node src,p_fabric_host_node dst,UINT4 targetIP, packet_in_info_t *packet_in);
INT4 openstack_ip_p_flood(p_fabric_host_node src_port,UINT4 sendip,UINT4 targetip,UINT1* src_mac,packet_in_info_t *packet_in);
INT4 openstack_ip_packet_output(p_fabric_host_node src_port,p_fabric_host_node dst_port,UINT4 sendip,UINT4 targetip,packet_in_info_t *packet_in);
INT4 openstack_ip_p_install_flow(param_set_p param_set, INT4 foward_type);
void fabric_opnestack_create_arp_flood(UINT4 src_ip, UINT4 dst_ip, UINT1* src_mac);
INT4 openstack_ip_packet_compute_src_dst_forward(p_fabric_host_node src_port,p_fabric_host_node dst_port,packet_in_info_t *packet_in, param_set_p param_set);
INT4 openstack_ip_p_broadcast(packet_in_info_t *packet_in);
INT4 openstack_ip_packet_check_access(gn_switch_t *sw,p_fabric_host_node src_port, p_fabric_host_node dst_port, packet_in_info_t *packet_in, param_set_p param);
void remove_flows_by_sw_port(UINT8 sw_dpid, UINT4 port);
void fabric_openstack_packet_output(gn_switch_t *sw, packet_in_info_t *packet_in_info,UINT4 outport);
void fabric_opnestack_create_arp_request(UINT4 src_ip, UINT4 dst_ip, UINT1* src_mac, gn_switch_t* sw, UINT4 outPort);

INT4 openstack_ip_install_deny_flow(gn_switch_t* sw, ip_t* ip);
INT4 openstack_ip_remove_deny_flow(UINT1* src_mac,UINT4 ip);
INT4 internal_packet_compute_floating_vip_forward(p_fabric_host_node src_port, p_fabric_host_node dst_port, UINT4 targetip,
		param_set_p param_set, ip_t *ip, external_port_p epp, external_floating_ip_p fip);
INT4 external_packet_in_compute_vip_forward(p_fabric_host_node src_port, p_fabric_host_node dst_port, UINT4 targetip,
		param_set_p param_set, ip_t *ip, external_port_p epp, external_floating_ip_p fip);
INT4 create_arp_flood_parameter(UINT4 dst_ip, p_fabric_host_node dst_port, param_set_p param);
void fabric_opnestack_create_icmp_flood(gn_switch_t* sw, UINT4 src_ip, UINT4 dst_ip, UINT1* src_mac, UINT1* dst_mac);
void fabric_opnestack_create_icmp_flood_allsw( UINT4 src_ip, UINT4 dst_ip, UINT1* src_mac, UINT1* dst_mac);
void fabric_opnestack_create_clb_arpflood(gn_switch_t* sw, UINT4 src_ip, UINT4 dst_ip, UINT1* src_mac, UINT1* dst_mac);

gn_switch_t* get_ext_sw_by_dpid(UINT8 dpid);


#if 1
p_fabric_host_node openstack_find_ip_dst_port_ipv6(p_fabric_host_node src_node,UINT1* targetip);
p_fabric_host_node openstack_save_host_info_ipv6(gn_switch_t *sw,UINT1* sendmac,UINT1* sendip,UINT4 inport);
void fabric_openstack_install_fabric_flows_ipv6(p_fabric_host_node src_port,p_fabric_host_node dst_port,
										   security_param_p src_security, security_param_p dst_security);
#endif
#endif /* INC_FABRIC_FABRIC_OPENSTACK_ARP_H_ */
