package CIBGrp5;

import com.hazelcast.config.*;
import com.hazelcast.client.config.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ConfigFactory{
    private String address = null;
    private Config config = null;
    private List<ClientConfig> targetConfigs = new ArrayList<ClientConfig>();
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

    public ConfigFactory(){
	this.clusters.add(new Cluster("199.253.242.3", "singapore", "singapore_pass"));
	this.clusters.add(new Cluster("128.199.169.148", "london", "london_pass"));
	this.clusters.add(new Cluster("45.55.74.252", "nyc", "nyc_pass"));
	this.address = this.getPublicIP();
	this.generateClusterConfig();
    }

    public Config getConfig(){
	return this.config;
    }

    public List<ClientConfig> getTargetConfigs(){
	return this.targetConfigs;
    }

    public void generateClusterConfig(){
	for(Cluster cluster : clusters){
	    if(cluster.address.equals(this.address)){
		this.config = generateConfig(cluster.name, cluster.password);
	    }else{
		this.targetConfigs.add
		    (generateTargetConfig(cluster.name, cluster.password, cluster.address));
	    }
	}
    }

    public Config generateConfig(String name, String password){
	Config config = new Config();
	config.getGroupConfig()
	    .setName(name)
	    .setPassword(password);
	return config;
    }
    
    public ClientConfig generateTargetConfig
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

    public String getPublicIP(){
	try {
	    URL whatismyip = new URL("http://checkip.amazonaws.com");
	    BufferedReader in = new BufferedReader
		(new InputStreamReader(whatismyip.openStream()));
	    String ip = in.readLine(); //you get the IP as a String
	    return ip;
	}
	catch (IOException e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
	return null;
    }
    
}
