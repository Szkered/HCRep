package CIBGrp5;

import com.hazelcast.core.*;
import com.hazelcast.config.*;
import com.hazelcast.client.*;
import com.hazelcast.client.config.*;

import java.util.*;

public class OperationManager{
    private static ConfigFactory factory = new ConfigFactory();
    private static List<String> instanceNames = new ArrayList<String>();
    private static MapReplicationService mapReplicationService;
    
    public static HazelcastInstance startInstance(String instanceName){
	Config config = factory.getConfig();
	config.setInstanceName(instanceName);
	instanceNames.add(instanceName);
	return Hazelcast.newHazelcastInstance(config);
    }

    public static void startReplicationService(){
	mapReplicationService = new MapReplicationServiceImpl(factory.getTargetConfigs());
	HazelcastInstance i = Hazelcast.getHazelcastInstanceByName("master-node");

	/**
	 * TO-DO: specific maps to be replicated
	 *
	 */
	i.getMap("testMap").
	    addEntryListener(mapReplicationService.getReplicatingListener(), true);
    }

}
