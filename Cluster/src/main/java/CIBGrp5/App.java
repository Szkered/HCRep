package CIBGrp5;

import com.hazelcast.core.*;
import com.hazelcast.config.*;

import java.util.Map;
import java.util.Queue;

public class App implements EntryListener<Integer, Long> {
    public static void main( String[] args ) {
	Config cfg = new Config();
	NetworkConfig netcfg = new NetworkConfig();
	JoinConfig join = netcfg.getJoin();
	join.getMulticastConfig()
	    .setEnabled(false);
	join.getTcpIpConfig()
	    .addMember("128.199.169.148:5701")
	    .setEnabled(true); // addr to fill
	cfg.setNetworkConfig(netcfg);
	HazelcastInstance Instance = Hazelcast.newHazelcastInstance();

	// Map
	IMap<Integer, Long> map = Instance.getMap("timestamp");
	long num = 1;
	map.addEntryListener(new App(), true);
	map.put( 1, num );
	System.out.println("EntryListener registered");

	// System.out.println( "Customer with key 1: " + map.get(1) );
	// System.out.println( "Map Size:" + map.size() );

	//Queue
	// Queue<String> q = Instance.getQueue( "queue" );
	// q.offer( "Viva" );
	// q.offer( "La" );
	// q.offer( "Vida" );
	// System.out.println( "1: " + q.poll() );
	// System.out.println( "2: "+ q.peek() );
	// System.out.println( "queue size: " + q.size() );
    }
    
    @Override
    public void entryAdded(EntryEvent<Integer, Long> event) {
	System.out.println("entryAdded:" + event);
    }

    @Override
    public void entryRemoved(EntryEvent<Integer, Long> event) {
	System.out.println("entryRemoved:" + event);
    }

    @Override
    public void entryUpdated(EntryEvent<Integer, Long> event) {
	System.out.println("entryUpdated:" + event);
    }

    @Override
    public void entryEvicted(EntryEvent<Integer, Long> event) {
	System.out.println("entryEvicted:" + event);
    }

    @Override
    public void mapEvicted(MapEvent event) {
	System.out.println("mapEvicted:" + event);

    }
        
    @Override
    public void mapCleared(MapEvent event) {
	System.out.println("mapCleared:" + event);
    }
}
