package CIBGrp5;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import java.util.ArryaList;
import java.util.List;

public class App {
    public static void main( String[] args ){
	ClientConfig clientConfig = new ClientConfig();
	ClientNetworkConfig netcfg = new ClientNetworkConfig();

	List<String> address_list = new ArryaList<String>();
	address_list.add("169.108.136.42:5701");

	
	netcfg.setAddresses(address_list);
	clientConfig.setNetworkConfig(netcfg);
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        IMap map = client.getMap( "customers" );
        System.out.println( "Map Size:" + map.size() );
	System.out.println(map.get(4));
    }
}
