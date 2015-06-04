package CIBGrp5;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.ArrayList;
import java.util.List;

public class App {
    
    public static void main( String[] args ){
	ClientConfig clientConfig = new ClientConfig();
	ClientNetworkConfig netcfg = new ClientNetworkConfig();
    
	List<String> address_list = new ArrayList<String>();
	// address_list.add("169.108.136.42:5701");
	address_list.add("127.0.0.1:5701"); // connect to local Cluster

	netcfg.setAddresses(address_list);
	clientConfig.setNetworkConfig(netcfg);
	HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
	//"169.108.136.42:5701"
        IMap map = client.getMap( "customers" );
	// map.put(4, "D");
	System.out.println( "Map Size:" + map.size() );
	System.out.println(map.get(4));
    }

    public static int addition(int a, int b) {
	return a+b;
    }
}
