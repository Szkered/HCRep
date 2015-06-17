package CIBGrp5;

import com.hazelcast.core.*;
import com.hazelcast.config.*;
import com.hazelcast.client.*;
import com.hazelcast.client.config.*;

import java.util.Map;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class ReplicatingCluster{
    private String address;
    private ClusterConfig clusterConfig;
    private ReplicationService replicationService;
    private List<String> instanceNames = new ArrayList<String>();
    
    public ReplicatingCluster(){
	try{
	    this.address = Inet4Address.getLocalHost().getHostAddress();	    
	}
	catch (UnknownHostException e){
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
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

}
