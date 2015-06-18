package CIBGrp5;

import com.hazelcast.core.*;
import com.hazelcast.config.*;
import com.hazelcast.client.*;
import com.hazelcast.client.config.*;

import java.util.Map;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;

import java.util.Enumeration;
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;


public class OperationManager{
    private static SuperClusterConfig s = new SuperClusterConfig();
    private static List<String> instanceNames = new ArrayList<String>();
    private static MapReplicationService mapReplicationService;
    
    public static HazelcastInstance startInstance(String instanceName){
	Config config = s.getClusterConfig(getMachineIP()).getConfig();
	config.setInstanceName(instanceName);
	instanceNames.add(instanceName);
	return Hazelcast.newHazelcastInstance(config);
    }

    public static void startReplicationService(){
	mapReplicationService =
	    new MapReplicationServiceImpl(s.getClusterConfig(getMachineIP()).
					  getReplicationTargetConfigs());
	/**
	 *  DEBUG
	 *
	 */
	HazelcastInstance i = Hazelcast.getHazelcastInstanceByName("master-node");
	i.getMap("timestamp").addEntryListener(mapReplicationService.getMapListener(), true);
    }

    public static String getMachineIP(){
	try {
	    Enumeration e = NetworkInterface.getNetworkInterfaces();
	    while(e.hasMoreElements()){
		NetworkInterface n = (NetworkInterface) e.nextElement();
		Enumeration ee = n.getInetAddresses();
		while (ee.hasMoreElements()){
		    InetAddress i = (InetAddress) ee.nextElement();
		    if(i instanceof Inet4Address && !i.getHostAddress().equals("127.0.0.1")){
			return i.getHostAddress();
		    }
		}
	    }
	}
	catch (SocketException e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
	return null;
    }

}
