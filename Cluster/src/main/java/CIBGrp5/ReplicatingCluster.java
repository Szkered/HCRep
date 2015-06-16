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
    private String ip;
    private ClusterConfig clusterConfig;
    private ReplicationService replicationService = new MapReplicationServiceImpl();
    private List<String> instanceNames = new ArrayList<String2>();
    private List<String> replicatorNames = new ArrayList<String>();
    
    public ReplicatingCluster(){
	try{
	    this.ip = Inet4Address.getLocalHost().getHostAddress();	    
	}
	catch (UnknownHostException e){
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
	this.clusterConfig = new SuperClusterConfig().getClusterConfig(this.ip);
    }
    
    public void startInstance(String instanceName){
	this.clusterConfig.config.setInstanceName(instanceName);
	Hazelcast.newHazelcastInstance(this.clusterConfig.config);
	this.instanceNames.add(instanceName);
    }

    public void startReplicationService(){
	ArrayList<DistributedDAO> targetDAOs = new ArrayList<DistributedDAO>();
	for(ClientConfig config : this.clusterConfig.replicationTargetConfigs){
	    HazelcastInstance replicationTarget =
		HazelcastClient.newHazelcastClient(config);
	    targetDAOs.add(new MapDAOImpl(replicationTarget));
	    this.replicatorNames.add(replicationTarget.getName());
	}
	this.replicationService = new MapReplicationServiceImpl(targetDAOs);
    }

}
