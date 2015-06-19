package CIBGrp5;

import com.hazelcast.core.*;
import com.hazelcast.client.*;
import com.hazelcast.client.config.*;

import java.util.*;

public class MapReplicationServiceImpl implements MapReplicationService{

    private List<HazelcastInstance> targets = new ArrayList<HazelcastInstance>();
    private EntryListener<Object, Object> replicatingListener = new ReplicatingListener();

    public MapReplicationServiceImpl(List<ClientConfig> replicationTargetConfigs){
	for(ClientConfig clientConfig : replicationTargetConfigs){
	    HazelcastInstance target = HazelcastClient.newHazelcastClient(clientConfig);
	    targets.add(target);
	}
    }

    private void replicate(EntryEvent<Object, Object> event){
	for(HazelcastInstance target : targets){
	    IExecutorService ex = target.getExecutorService("exec");
	    ex.submit(new ReplicateTask(event));
	}
    }

    @Override
    public EntryListener<Object, Object> getReplicatingListener(){
	return this.replicatingListener;
    }

    private class ReplicatingListener implements EntryListener<Object, Object>{
	@Override
	public void entryAdded(EntryEvent<Object, Object> event) {
	    System.out.println("[INFO] " + event);
	    MapReplicationServiceImpl.this.replicate(event);
	}

	@Override
	public void entryRemoved(EntryEvent<Object, Object> event) {
	    System.out.println("[INFO] " + event);
	    MapReplicationServiceImpl.this.replicate(event);
	}

	@Override
	public void entryUpdated(EntryEvent<Object, Object> event) {
	    System.out.println("[INFO] " + event);
	    MapReplicationServiceImpl.this.replicate(event);
	}

	@Override
	public void entryEvicted(EntryEvent<Object, Object> event) {
	    System.out.println("[INFO] " + event);
	    MapReplicationServiceImpl.this.replicate(event);
	}

	/**
	 *   TO-DO
	 *
	 */
	@Override
	public void mapEvicted(MapEvent event) {
	    System.out.println("[INFO] " + event);
	    // MapReplicationServiceImpl.this.replicate(event);
	}
        
	@Override
	public void mapCleared(MapEvent event) {
	    System.out.println("[INFO] " + event);
	    // MapReplicationServiceImpl.this.replicate(event);
	}
    }
}
