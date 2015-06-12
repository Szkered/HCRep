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
    private static String name;
    private static String password;

    private Updater(String address, String name, String password) {
    	// init updater
	List<String> address_list = new ArrayList<String>();
	address_list.add(address);
	
	ClientConfig clientConfig = new ClientConfig();
	clientConfig.getGroupConfig().setName(name).setPassword(password);
	clientConfig.getNetworkConfig().setAddresses(address_list);
	
	hzInstance = HazelcastClient.newHazelcastClient(clientConfig);	
    }

    public static Updater getInstance(){
	if(instance == null) {
	    instance = new Updater(targetCluster, name, password);
	}
	else {}
	return instance;
    }

    public static void setTargetCluster(String address) {
	System.out.println("[info] (" + Updater.class.getSimpleName() + " setTargetCluster) -> " + address);
	targetCluster = address;
    }
    
    public static void setName(String name) {
	System.out.println("[info] (" + Updater.class.getSimpleName() + " setName) -> " + name);
	name = name;
    }
    
    public static void setPassword(String password) {
	System.out.println("[info] (" + Updater.class.getSimpleName() + " setPassword) -> " + password);
	password = password;
    }
    

    public void updateAdd(EntryEvent ee) {
	IMap map = hzInstance.getMap(ee.getName());
	System.out.println("updating " + map);
	map.put(ee.getKey(), ee.getValue());
	// q.offer((String) ee.getItem());
    }

    public void updateRemove(EntryEvent ee) {
	IMap map = hzInstance.getMap(ee.getName());
	System.out.println("updating " + map);
	// Queue<String> q = hzInstance.getQueue("queue");
	// q.poll();
    }
}
