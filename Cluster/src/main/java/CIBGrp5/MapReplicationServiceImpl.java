package CIBGrp5;

import com.hazelcast.core.*;

import java.util.List;
import java.util.ArrayList;

public class MapReplicationServiceImpl implements ReplicationService{

    private List<MapDAO> targetDAOs;
    private EntryListener<Object, Object> mapListener = new MapListener();

    public MapReplicationServiceImpl(List<MapDAO> targetDAOs){
	this.targetDAOs = targetDAOs;
	for(MapDAO dao : targetDAOs){
	    dao.addListener(mapListener);
	}
    }

    public void replicate(EntryEvent<Object, Object> event){
	for(MapDAO dao : targetDAOs){
	    this.parseEvent(event, dao);
	}
    }
    
    public void parseEvent(EntryEvent<Object, Object> event, MapDAO dao){
	switch(event.getEventType()){

	    /**
	     *  Critical BUG here, need to specify whether to replicate
	     *
	     */
	    
	case ADDED:
	    dao.create(event.getName(), event.getKey(), event.getValue(), true);
	case UPDATED:
	    dao.update(event.getName(), event.getKey(), event.getValue(), true);
	case REMOVED:
	    dao.delete(event.getName(), event.getKey());
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
	    /**
	     *   TO-DO
	     *
	     */
	    // MapReplicationServiceImpl.this.replicate(event);
	}
        
	@Override
	public void mapCleared(MapEvent event) {
	    System.out.println("[INFO] " + event);
	    // MapReplicationServiceImpl.this.replicate(event);
	}
    }
}
