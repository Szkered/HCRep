package CIBGrp5;

import com.hazelcast.core.*;
import com.hazelcast.config.*;
import com.hazelcast.client.*;
import com.hazelcast.client.config.*;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.io.Serializable;

public class MapDAOTask
    implements Callable<Boolean>,
	       Serializable,
	       HazelcastInstanceAware{
	
    final EntryEventType type;
    final String name;
    final Object key;
    final Object value;
    final Boolean replicate;
    final EntryListener mapListener;
    private HazelcastInstance instance;

	
    MapDAOTask(EntryEventType type,
	       String name,
	       Object key,
	       Object value,
	       Boolean replicate,
	       EntryListener mapListener){
	this.type = type;
	this.name = name;
	this.key = key;
	this.value = value;
	this.replicate = replicate;
	this.mapListener = mapListener;
    }
	

    @Override
    public Boolean call(){
	IMap map = this.instance.getMap(this.name);
	switch(this.type){
	case ADDED:
	    map.set(this.key, this.value);
	    map.addEntryListener(this.mapListener, true);
	case UPDATED:
	    map.put(this.key, this.value);
	    if(this.replicate){
		map.addEntryListener(this.mapListener, true);
	    }
	case REMOVED:
	    map.delete(this.key);
	}
	/**
	 *  need improvement here
	 *
	 */
	return true;
    }

    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
	this.instance = hazelcastInstance;
    }

}
