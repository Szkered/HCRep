package CIBGrp5;

import com.hazelcast.core.*;
import com.hazelcast.config.*;

import java.util.Map;
import java.util.Queue;

public class App {

    // private static final String TARGET = "128.199.169.148:5701";
    // private static final String TARGET = "10.242.159.23:5701";
    private static final String TARGET = "localhost:5702";
    
    public static void main( String[] args ) {
	Config cfg = new Config();
	cfg.getGroupConfig()
	    .setName("d1")
	    .setPassword("d1");
	NetworkConfig netcfg = new NetworkConfig();
	JoinConfig join = netcfg.getJoin();
	join.getMulticastConfig()
	    .setEnabled(true);
	join.getTcpIpConfig()
	    .setEnabled(false) // addr to fill
	    .addMember(TARGET);
	cfg.setNetworkConfig(netcfg);

	Updater.setTargetCluster(TARGET);
	
	HazelcastInstance Instance = Hazelcast.newHazelcastInstance(cfg);

	// Map
	IMap<Integer, Long> map = Instance.getMap("timestamp");
	long num = 1;
	map.put( 1, num );
	map.addEntryListener(new myEntryListener(), true);
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

    private static class myEntryListener implements EntryListener<Integer, Long> {
	    
	@Override
	public void entryAdded(EntryEvent<Integer, Long> event) {
	    System.out.println("entryAdded:" + event);
	    Updater.getInstance().updateAdd(event);
	}

	@Override
	public void entryRemoved(EntryEvent<Integer, Long> event) {
	    System.out.println("entryRemoved:" + event);
	    Updater.getInstance().updateRemove(event);
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
