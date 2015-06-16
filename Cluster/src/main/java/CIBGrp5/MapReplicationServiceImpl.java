package CIBGrp5;

import com.hazelcast.core.*;

import java.util.List;
import java.util.ArrayList;

public class MapReplicationServiceImpl implements ReplicationService{

    private List<DistributedDAO> targetDAOs;
    private EntryListener<Object, Object> mapListener = new MapListener();

    public MapReplicationServiceImpl(List<DistributedDAO> targetDAOs){
	this.targetDAOs = targetDAOs;
	for(MapDAOImpl dao : targetDAOs){
	    dao.addListener(mapListener);
	}
    }

    public void replicate(EntryEvent<Object, Object> event){
	for(DistributedDAO dao : targetDAOs){
	    this.parseEvent(event, dao);
	}
    }
    
    public void parseEvent(EntryEvent<Object, Object> event, DistributedDAO dao){
	switch(event.getEventType()){
	case EntryEvent.ADDED:
	    dao.create(event.getName(), event.getKey(), event.getValue());
	case EntryEvent.UPDATED:
	    dao.update(event.getName(), event.getKey(), event.getValue());
	case EntryEvent.REMOVED:
	    dao.remove(event.getName(), event.getKey(), event.getValue());
	}
    }

    public EntryListener getEntryListener(){
	return mapListener;
    }
    
    
    private class MapListener implements EntryListener<Object, Object>{
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

	@Override
	public void mapEvicted(MapEvent event) {
	    System.out.println("[INFO] " + event);
	    MapReplicationServiceImpl.this.replicate(event);
	}
        
	@Override
	public void mapCleared(MapEvent event) {
	    System.out.println("[INFO] " + event);
	    MapReplicationServiceImpl.this.replicate(event);
	}
    }
}
