package CIBGrp5;

import com.hazelcast.core.*;
import com.hazelcast.client.*;
import com.hazelcast.client.config.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;


public class Updater {

    private static Updater instance;
    private static HazelcastInstance hzInstance;
    private static String targetCluster;

    private Updater(String address) {
    	// init updater
	ClientConfig clientConfig = new ClientConfig();
	ClientNetworkConfig netcfg = new ClientNetworkConfig();
	List<String> address_list = new ArrayList<String>();

	address_list.add(address);
	// address_list.add("128.199.169.148:5702"); // connect to Droplet
	netcfg.setAddresses(address_list);
	clientConfig.setNetworkConfig(netcfg);
	
	hzInstance = HazelcastClient.newHazelcastClient(clientConfig);	
    }

    public static Updater getInstance(){
	if(instance == null) {
	    instance = new Updater(targetCluster);
	}
	else {}
	return instance;
    }
    

    public static void setTargetCluster(String address) {
	System.out.println("[info] (" + Updater.class.getSimpleName() + " setTargetCluster) -> " + address);
	targetCluster = address;
    }

    public void updateAdd(EntryEvent ee) {
	IMap map = hzInstance.getMap(ee.getName());
	System.out.println("updating " + map);
	// q.offer((String) ee.getItem());
    }

    public void updateRemove(EntryEvent ee) {
	IMap map = hzInstance.getMap(ee.getName());
	System.out.println("updating " + map);
	// Queue<String> q = hzInstance.getQueue("queue");
	// q.poll();
    }
}
