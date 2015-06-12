package CIBGrp5;

import com.hazelcast.core.*;
import com.hazelcast.config.*;

import java.util.Map;
import java.util.Queue;

public class TestCluster {

    // private static final String TARGET = "128.199.169.148:5701";
    // private static final String TARGET = "10.242.159.23:5701";
    // private static final String TARGET = "localhost:5702";
    private static HazelcastInstance Instance;
    
    public static void main( String[] args ) {
	init(1);

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

    public static void init(int num){
	Integer port = 5700 + num;
	Integer targetPort;
	
	Config cfg = new Config();
	cfg.getGroupConfig()
	    .setName(port.toString())
	    .setPassword(port.toString());
	
	NetworkConfig netcfg = new NetworkConfig();
	netcfg.setPort(port);
	JoinConfig join = netcfg.getJoin();
	join.getMulticastConfig()
	    .setEnabled(true);
	join.getTcpIpConfig()
	    .setEnabled(false); // addr to fill
	    // .addMember(TARGET);
	cfg.setNetworkConfig(netcfg);

	if(num == 1){
	    targetPort = 5702;
	}
	else {
	    targetPort = 5701;
	}
	Updater.setTargetCluster("localhost:" + targetPort.toString());
	Updater.setName(targetPort.toString());
	Updater.setPassword(targetPort.toString());   
	
	Instance = Hazelcast.newHazelcastInstance(cfg);
    }

    public static boolean updateIsLocal(Member member){
	for(Member m : Instance.getCluster().getMembers()){
	    if(m == member) {
		return true;
	    }
	    else {
		continue;
	    }
	}
	return false;
    }

    private static class myEntryListener implements EntryListener<Integer, Long> {
	    
	@Override
	public void entryAdded(EntryEvent<Integer, Long> event) {
	    System.out.println("entryAdded:" + event);
	    if(updateIsLocal(event.getMember())){
		Updater.getInstance().updateAdd(event);
	    }
	}

	@Override
	public void entryRemoved(EntryEvent<Integer, Long> event) {
	    System.out.println("entryRemoved:" + event);
	    if(updateIsLocal(event.getMember())){
		Updater.getInstance().updateRemove(event);
	    }
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
