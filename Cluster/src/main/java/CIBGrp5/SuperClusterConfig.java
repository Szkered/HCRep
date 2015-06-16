package CIBGrp5;

import com.hazelcast.config.*;
import com.hazelcast.client.config.*;

import java.util.List;
import java.util.ArrayList;

public class SuperClusterConfig{
    private List<Cluster> clusters = new ArrayList<Cluster>();

    private class Cluster{
	public String ip;
	public String name;
	public String password;

	public Cluster(String ip, String name, String password){
	    this.ip = ip;
	    this.name = name;
	    this.password = password;
	}
    }

    public SuperClusterConfig(){
	this.clusters.add(new Cluster("128.199.169.148", "singapore", "singapore_pass"));
	this.clusters.add(new Cluster("", "london", "london_pass"));
	this.clusters.add(new Cluster("", "nyc", "nyc_pass"));
    }

    public static ClusterConfig getClusterConfig(String ip){
	ClusterConfig clusterConfig = new ClusterConfig();
	for(Cluster cluster : clusters){
	    if(cluster.ip == ip){
		clusterConfig.
		    setConfig(getConfig(cluster.name, cluster.password));
	    }else{
		clusterConfig.
		    addReplicationTargetConfig(getReplicationTargetConfig
					       (cluster.name, cluster.password));
	    }
	}
    }

    public static Config getConfig(String name, String password){
	Config config = new Config();
	config.getGroupConfig()
	    .setName(name)
	    .setPassword(password);
	return config;
    }
    
    public static ClientConfig getReplicationTargetConfig
	(String name, String password, String address){
	List<String> addresses = new ArrayList<String>();
	addresses.add(address);
	ClientConfig replicationTargetConfig = new ClientConfig();
	replicationTargetConfig.setInstanceName(name);
	replicationTargetConfig.getGroupConfig().
	    setName(name).
	    setPassword(password);
	replicationTargetConfig.getNetworkConfig().
	    setAddresses(addresses);
	return replicationTargetConfig;
    }
    
}
