package CIBGrp5;

import com.hazelcast.core.*;
import com.hazelcast.config.*;

import java.util.Map;
import java.util.Queue;

public class App {
    public static void main( String[] args ) {
	Config cfg = new Config();
	// cfg.getGroupConfig()
	//     .setName("altarf")
	//     .setPassword("altarf-pass");
	NetworkConfig netcfg = new NetworkConfig();
	JoinConfig join = netcfg.getJoin();
	join.getMulticastConfig()
	    .setEnabled(false);
	join.getTcpIpConfig()
	    .addMember("128.199.169.148:5701")
	    .setEnabled(true); // addr to fill
	cfg.setNetworkConfig(netcfg);

	Updater.setTargetCluster("127.0.0.1:5701");
	
	HazelcastInstance Instance = Hazelcast.newHazelcastInstance(cfg);

	// Map
	IMap<Integer, Long> map = Instance.getMap("timestamp");
	long num = 1;
	map.addEntryListener(new myEntryListener(), true);
	System.out.println("EntryListener registered");
	
	map.put( 1, num );
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

    private static class myEntryListener implements EntryListener<Integer, Long> {

	private static Updater u = Updater.getInstance();
	    
	@Override
	public void entryAdded(EntryEvent<Integer, Long> event) {
	    System.out.println("entryAdded:" + event);
	    u.updateAdd(event);
	}

	@Override
	public void entryRemoved(EntryEvent<Integer, Long> event) {
	    System.out.println("entryRemoved:" + event);
	    u.updateRemove(event);
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
}
