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



public class ReplicatingCluster{
    private String address;
    private ClusterConfig clusterConfig;
    private ReplicationService replicationService;
    private List<String> instanceNames = new ArrayList<String>();
    
    public ReplicatingCluster(){
	this.address = getMachineIP();
	SuperClusterConfig s = new SuperClusterConfig();
	this.clusterConfig = s.getClusterConfig(this.address);
    }
    
    public void startInstance(String instanceName){
	this.clusterConfig.getConfig().setInstanceName(instanceName);
	Hazelcast.newHazelcastInstance(this.clusterConfig.getConfig());
	this.instanceNames.add(instanceName);
    }

    public void startReplicationService(){
	List<MapDAO> targetDAOs = new ArrayList<MapDAO>();
	for(ClientConfig config : this.clusterConfig.getReplicationTargetConfigs()){
	    HazelcastInstance replicationTarget =
		HazelcastClient.newHazelcastClient(config);
	    targetDAOs.add(new MapDAOImpl(replicationTarget));
	}
	this.replicationService = new MapReplicationServiceImpl(targetDAOs);
    }

    public String getMachineIP(){
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
