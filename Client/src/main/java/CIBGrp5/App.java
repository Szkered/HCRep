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
	ArrayList<String> address_list = new ArrayList<String>();
	// address_list.add("128.199.169.148:5701");
	address_list.add("178.62.17.188:5701");
	
	ClientConfig clientConfig = new ClientConfig();
	// clientConfig.getGroupConfig().
	//     setName("singapore").
	//     setPassword("singapore_pass");
	clientConfig.getGroupConfig().
	    setName("london").
	    setPassword("london_pass");
	clientConfig.getNetworkConfig().
	    setAddresses(address_list);
	
	HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        IMap map = client.getMap( "timestamp" );
	long num = 1;
	System.out.println( "Map Size:" + map.size() );
	// map.put(4, num);
	// System.out.println( "Map Size:" + map.size() );
	System.out.println(map.get(4));
    }

    public static int addition(int a, int b) {
	return a+b;
    }
}
