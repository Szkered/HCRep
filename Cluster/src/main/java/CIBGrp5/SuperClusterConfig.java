package CIBGrp5;

import com.hazelcast.config.*;
import com.hazelcast.client.config.*;

import java.util.List;
import java.util.ArrayList;

public class SuperClusterConfig{
    private List<Cluster> clusters = new ArrayList<Cluster>();

    private class Cluster{
	public String address;
	public String name;
	public String password;

	public Cluster(String address, String name, String password){
	    this.address = address;
	    this.name = name;
	    this.password = password;
	}
    }

    public SuperClusterConfig(){
	this.clusters.add(new Cluster("128.199.169.148", "singapore", "singapore_pass"));
	this.clusters.add(new Cluster("", "london", "london_pass"));
	this.clusters.add(new Cluster("", "nyc", "nyc_pass"));
    }

    public ClusterConfig getClusterConfig(String address){
	ClusterConfig clusterConfig = new ClusterConfig();
	for(Cluster cluster : clusters){
	    if(cluster.address == address){
		clusterConfig.
		    setConfig(getConfig(cluster.name, cluster.password));
	    }else{
		clusterConfig.
		    addReplicationTargetConfig(generateReplicationTargetConfig
					       (cluster.name, cluster.password, cluster.address));
	    }
	}
    }

    public Config getConfig(String name, String password){
	Config config = new Config();
	config.getGroupConfig()
	    .setName(name)
	    .setPassword(password);
	return config;
    }
    
    public ClientConfig generateReplicationTargetConfig
	(String name, String password, String address){
	List<String> addresses = new ArrayList<String>();
	addresses.add(address);
	ClientConfig replicationTargetConfig = new ClientConfig();
	// this doesn't work!
	// replicationTargetConfig.setInstanceName(name);
	replicationTargetConfig.getGroupConfig().
	    setName(name).
	    setPassword(password);
	replicationTargetConfig.getNetworkConfig().
	    setAddresses(addresses);
	return replicationTargetConfig;
    }
    
}
