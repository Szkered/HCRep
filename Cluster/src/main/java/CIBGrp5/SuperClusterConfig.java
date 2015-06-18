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
	this.clusters.add(new Cluster("10.21.114.35", "singapore", "singapore_pass"));
	this.clusters.add(new Cluster("128.199.169.148", "london", "london_pass"));
	// this.clusters.add(new Cluster("", "nyc", "nyc_pass"));
    }

    public ClusterConfig getClusterConfig(String address){
	ClusterConfig clusterConfig = new ClusterConfig();
	for(Cluster cluster : clusters){
	    if(cluster.address.equals(address)){
		clusterConfig.
		    setConfig(generateConfig(cluster.name, cluster.password));
	    }else{
		clusterConfig.
		    addReplicationTargetConfig(generateReplicationTargetConfig
					       (cluster.name, cluster.password, cluster.address));
	    }
	}
	return clusterConfig;
    }

    public Config generateConfig(String name, String password){
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
