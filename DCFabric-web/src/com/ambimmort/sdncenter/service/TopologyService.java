/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.sdncenter.service;

import com.ambimmort.sdncenter.util.RestClient;
import com.ambimmort.sdncenter.util.StatusCode;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class TopologyService {

    private static final String LINK_URL = "/gn/topo/links/json";
    private final static String HOST_URL = "/gn/topo/hosts/json";
    private static String STATIC_URL = "/gn/path/stats/json";
    private final static String topo_fabric_setup = "/gn/fabric/setup/json";
    private final static String topo_fabric_delete = "/gn/fabric/delete/json";
    private final static String topo_path_optimal_path = "gn/fabric/getpath/json";
    private final static String topo_path_optimal_path_all = "/gn/path_optimal_all/json";
    private final static String topo_path_optimal_path_part = "/gn/fabric/setupparts/json";
    private final static String topo_path_optimal_path_save = "/gn/path_optimal_save/json";
    public JSONObject getSwitchLink(String ip, String port) throws IOException, Exception {
        JSONArray nodes = new JSONArray();
        JSONArray relation = new JSONArray();

        String resp = RestClient.getInstance().get("http://" + ip + ":" + port + LINK_URL);
        JSONObject respObj = JSONObject.fromObject(resp);
        if (respObj.getInt("retCode") != StatusCode.SUCCESS) {
            throw new Exception(respObj.getString("retMsg"));
        }
        JSONArray resp_Obj = respObj.getJSONArray("linkTopo");
        Iterator it = resp_Obj.iterator();
        while (it.hasNext()) {
//            String dpid = (String) it.next();
            JSONObject ob = (JSONObject) it.next();
            String dpid = ob.getString("srcDPID");
            nodes.add(dpid);

//            JSONArray ports = respObj.getJSONArray(dpid);
//            Iterator pit = ports.iterator();	
            Iterator pit = ob.getJSONArray("neighbors").iterator();
            while (pit.hasNext()) {
                JSONObject destPort = (JSONObject) pit.next();
                JSONObject item = new JSONObject();
                item.put("src_switch", dpid);
                item.put("src_port", destPort.getString("srcPort"));
                item.put("dst_switch", destPort.getString("dstDPID"));
                item.put("dst_port", destPort.getString("dstPort"));
                relation.add(item);
            }
        }
//        while (it.hasNext()) {
//            String dpid = (String) it.next();
//            nodes.add(dpid);
//
//            JSONArray ports = respObj.getJSONArray(dpid);
//            Iterator pit = ports.iterator();
//            while (pit.hasNext()) {
//                JSONObject destPort = (JSONObject) pit.next();
//                JSONObject item = new JSONObject();
//                item.put("src_switch", dpid);
//                item.put("src_port", destPort.getString("src-port"));
//                item.put("dst_switch", destPort.getString("dst-switch"));
//                item.put("dst_port", destPort.getString("dst-port"));
//                relation.add(item);
//            }
//        }

        JSONObject rs = new JSONObject();
        rs.put("nodes", nodes);
        rs.put("relation", relation);

        return rs;
    }
    private static long stringToLong(String values) {
        long value = new BigInteger(values.replaceAll(":", ""), 16).longValue();
        return value;
    }
    private String checkMininet(String dpid) {
    	if(dpid.equalsIgnoreCase("00:00:00:00:00:AB:CD:44")||dpid.equalsIgnoreCase("00:00:00:00:00:A0:10:29")||dpid.equalsIgnoreCase("67:8C:08:9E:01:A8:01:48")||dpid.equalsIgnoreCase("67:8C:08:9E:01:A7:FF:EF")){
    	    return "0";
    	}else{
	        if(dpid.length()<10){
	            return "";
	        }
	        Long mininetid = stringToLong(dpid);
	       if(mininetid<=100){
	            return "1";
	        }else if(mininetid<=200){
	          return "2";
	        }else if(mininetid<=300){
	          return "3";
	        }else if(mininetid<=400){
	          return "4";
	        }else if(mininetid<=500){
	          return "5";
	        }else if(mininetid<=600){
	          return "6";
	        }else{
    	      return "0";
	        }
    	}
    }
    private String checkCore(String dpid) {
    	long mininetid = stringToLong(dpid);
    	  if((mininetid>=1&&mininetid<=4) || (mininetid>=101&&mininetid<=104) ||(mininetid>=201&&mininetid<=204)||(mininetid>=301&&mininetid<=304)||(mininetid>=401&&mininetid<=404)||(mininetid>=501&&mininetid<=504)||(mininetid>=601&&mininetid<=604)){
    	   return "true";
    	  }else{
    	   return "false";
    	  }
    	 }
    private String checkPhy(String dpid) {
    	String tempPhy = dpid;
    	if(tempPhy.equalsIgnoreCase("OF|00:00:00:00:00:AB:CD:44")||tempPhy.equalsIgnoreCase("OF|00:00:00:00:00:A0:10:29")||tempPhy.equalsIgnoreCase("OF|67:8C:08:9E:01:A8:01:48")||tempPhy.equalsIgnoreCase("OF|67:8C:08:9E:01:A7:FF:EF")||tempPhy.equalsIgnoreCase("00:00:00:1e:08:09:df:00")||tempPhy.equalsIgnoreCase("00:00:00:1e:08:09:ec:36")){
    	return "1";
    	}
    	return "0";
    	}
    public ArrayList<Map<String,Object>> getNodeData(String ip, String port) throws IOException, Exception {
       Map<String,Object> map = new HashMap<String,Object>();
       ArrayList<Map<String,Object>> arr = new ArrayList<Map<String,Object>>();
       String resp = RestClient.getInstance().get("http://" + ip + ":" + port + LINK_URL);
       JSONObject respObj = JSONObject.fromObject(resp);
       if (respObj.getInt("retCode") != StatusCode.SUCCESS) {
           throw new Exception(respObj.getString("retMsg"));
       }
       JSONArray resp_Obj = respObj.getJSONArray("linkTopo");
       Iterator it = resp_Obj.iterator();
       while (it.hasNext()) {
           JSONObject ob = (JSONObject) it.next();
           String dpid = ob.getString("srcDPID");

//           JSONArray ports = respObj.getJSONArray(dpid);
//           Iterator pit = ports.iterator();
           Iterator pit = ob.getJSONArray("neighbors").iterator();
           Map<String,Object> m2 = new HashMap<String,Object>();
           Set<String> s2 =  new HashSet<String>();
           m2.put("id", "OF|"+dpid);
           m2.put("mac",dpid.substring(6));
           m2.put("name",dpid.substring(dpid.length()-5,dpid.length()));
           m2.put("core", checkCore(dpid)); 
           m2.put("mininet",checkMininet(dpid));
           Collection<Map<String,String>> tempV = new HashSet<Map<String,String>>();
           while (pit.hasNext()) {
        	   Map<String,String> m3 = new HashMap<String,String>();
               JSONObject destPort = (JSONObject) pit.next();
               m3.put("portA", destPort.getString("srcPort"));
               m3.put("id", "OF|"+destPort.getString("dstDPID"));
               m3.put("portZ", destPort.getString("dstPort"));
               tempV.add(m3);
               s2.add("OF|"+destPort.getString("srcPort"));
           }
           m2.put("ports",s2);
           m2.put("upNode",tempV);
           m2.put("downNode", new HashSet<Map<String,String>>());
           m2.put("isPhysical",checkPhy(dpid));
           m2.put("type","switch");
           m2.put("desc",dpid);
           m2.put("x","" );
           m2.put("y","");
           arr.add(m2);
       }
        return arr;
    }
    
    public JSONObject getCountInfo(String srcDpid,String dstDpid,String ip, String port)  throws IOException, Exception {
    	JSONObject obj = new JSONObject();
        if(null!=srcDpid && !"".equals(srcDpid)){
        	srcDpid = srcDpid.substring(3);
        }
        if(null!=dstDpid && !"".equals(dstDpid)){
        	dstDpid = dstDpid.substring(3);
        }
        String resp = RestClient.getInstance().get("http://" + ip + ":" + port + STATIC_URL+"&srcDPID="+srcDpid+"&dstDPID="+dstDpid);
        JSONObject respObj = JSONObject.fromObject(resp);
        obj.put("usedRateCN", "传输速率(kbps)");
        obj.put("usedBpsCN", "传输量(Mb)");
        obj.put("usedStatusCN", "拥塞状态");
        obj.put("usedRate", respObj.getString("txKbps"));
        obj.put("usedRateRec", respObj.getString("rxKbps"));
        obj.put("usedBps", respObj.getString("txBytes"));
        obj.put("usedBpsRec", respObj.getString("rxBytes"));
        obj.put("usedStatus", respObj.getString("txUsedStatus"));
        obj.put("usedStatusRec", respObj.getString("rxUsedStatus"));
        return obj;
    }

    public JSONObject setFabric(String ip, String port) throws IOException {
    	JSONArray nodes = new JSONArray();
        JSONArray relation = new JSONArray();
        RestClient.getInstance().post("http://" + ip + ":" + port + topo_fabric_setup,"{}");
        return null;
    }
    public JSONObject deleteFabric(String ip, String port) throws IOException {
    	JSONArray nodes = new JSONArray();
        JSONArray relation = new JSONArray();
        RestClient.getInstance().delete("http://" + ip + ":" + port + topo_fabric_delete,"{}");
        return null;
    }
    public String getInterval(String ip, String port) throws IOException {
        return "400000";
    }
    public JSONObject saveFabric(String ip, String port) throws IOException {
    	JSONArray nodes = new JSONArray();
        JSONArray relation = new JSONArray();
        
        String resp = RestClient.getInstance().get("http://" + ip + ":" + port + topo_path_optimal_path_save);
        JSONObject o = JSONObject.fromObject(resp);
        Iterator it = o.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            nodes.add(key);
            JSONArray hosts = o.getJSONArray(key);
            Iterator hit = hosts.iterator();
            while (hit.hasNext()) {
                JSONObject ob = (JSONObject)hit.next();
                JSONObject newObj = new JSONObject();
                newObj.put("dpid", key);
                newObj.put("port", ob.get("src-port"));
                newObj.put("hosts", ob.get("hosts"));
                newObj.put("total", ob.get("total"));
                relation. add(newObj);
            }
        }
        JSONObject rs = new JSONObject();
        rs.put("nodes", nodes);
        rs.put("",nodes);
        rs.put("relation", relation);     
        return rs;
    }
    public Vector<String> getSingleFabric(String ip, String port,String srcId,String dstId) throws IOException {
    	JSONObject obj = new JSONObject();
    	obj.put("srcDPID", stringToLong(srcId.replace("OF|", ""))+"");
    	obj.put("dstDPID", stringToLong(dstId.replace("OF|", ""))+"");
    	System.out.println(obj.toString());
        String resp = RestClient.getInstance().post("http://" + ip + ":" + port + topo_path_optimal_path,obj.toString());
        JSONObject o = JSONObject.fromObject(resp);
        JSONArray resp_Obj = o.getJSONArray("path");
        Iterator it = resp_Obj.iterator();
        Vector<String> v = new Vector<String>();
        while (it.hasNext()) {
        	 JSONObject ob = (JSONObject) it.next();
             String dpid = ob.getString("DPID");
             v.add(dpid);
        }
        return v;
    }
    public JSONObject getAllFabric(String ip, String port) throws IOException {
    	JSONArray nodes = new JSONArray();
        JSONArray relation = new JSONArray();
        String resp = RestClient.getInstance().get("http://" + ip + ":" + port + topo_path_optimal_path_all);
        JSONObject o = JSONObject.fromObject(resp);
        Iterator it = o.keys();
        while (it.hasNext()) {
            String key = (String) it.next();
            nodes.add(key);
            JSONArray hosts = o.getJSONArray(key);
            Iterator hit = hosts.iterator();
            while (hit.hasNext()) {
                JSONObject ob = (JSONObject)hit.next();
                JSONObject newObj = new JSONObject();
                newObj.put("dpid", key);
                newObj.put("port", ob.get("src-port"));
                newObj.put("hosts", ob.get("hosts"));
                newObj.put("total", ob.get("total"));
                relation.add(newObj);
            }
        }
        
        JSONObject rs = new JSONObject();
        rs.put("nodes", nodes);
        rs.put("relation", relation);
        
        return rs;
    }
    public JSONObject setPartFabric(String ip, String port,String dpids) throws IOException {
    	JSONObject obj = new JSONObject();
		obj.put("dpidList", dpids);
        String resp = RestClient.getInstance().post("http://" + ip + ":" + port + topo_path_optimal_path_part, obj.toString());
        return null;
    }
    
    public JSONObject getSwitchHost(String ip, String port) throws IOException, Exception {
        JSONArray nodes = new JSONArray();
        JSONArray relation = new JSONArray();

        String resp = RestClient.getInstance().get("http://" + ip + ":" + port + HOST_URL);
        // 添加解析代码
//        JSONObject o = JSONObject.fromObject(resp);
//        Iterator it = o.keys();
//        while (it.hasNext()) {
//            String key = (String) it.next();
//            nodes.add(key);
//            JSONArray hosts = o.getJSONArray(key);
//
//            Iterator hit = hosts.iterator();
//            while (hit.hasNext()) {
//                JSONObject ob = (JSONObject) hit.next();
//                JSONObject newObj = new JSONObject();
//                newObj.put("dpid", key);
//                newObj.put("port", ob.get("src-port"));
//                newObj.put("hosts", ob.get("hosts"));
//                newObj.put("total", ob.get("total"));
//                relation.add(newObj);
//            }
        JSONObject o = JSONObject.fromObject(resp);
        if (o.getInt("retCode") != StatusCode.SUCCESS) {
            throw new Exception(o.getString("retMsg"));
        }
        JSONArray resp_O = o.getJSONArray("hostTopo");
        Iterator it = resp_O.iterator();
        while (it.hasNext()) {
            JSONObject Ob = (JSONObject) it.next();
            String dpid = Ob.getString("srcDPID");
            nodes.add(dpid);
            String total = Ob.getString("total");

            Iterator hit = Ob.getJSONArray("ports").iterator();
            while (hit.hasNext()) {
                JSONObject ob = (JSONObject) hit.next();
                JSONObject newObj = new JSONObject();
                newObj.put("dpid", dpid);
                newObj.put("port", ob.getString("portNo"));
                JSONArray hosts = ob.getJSONArray("hosts");
                Iterator pit = hosts.iterator();
                while (pit.hasNext()) {
                    JSONObject Object = (JSONObject) pit.next();
                    JSONArray nO = new JSONArray();
                    JSONObject ho = new JSONObject();
                    ho.put("mac", Object.getString("hwAddr"));
                    ho.put("ip", Object.getString("ipv4Addr"));
                    nO.add(ho);
                    newObj.put("hosts", nO);
                }
                newObj.put("total", total);
                relation.add(newObj);
            }
        }
//        }
        JSONObject rs = new JSONObject();
        rs.put("nodes", nodes);
        rs.put("relation", relation);

        return rs;
    }
}
