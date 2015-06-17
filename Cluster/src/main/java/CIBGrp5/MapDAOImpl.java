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

public class MapDAOImpl implements MapDAO {

    private HazelcastInstance instance;
    private IExecutorService ex;
    private EntryListener<Object, Object> mapListener = null;

    public MapDAOImpl(HazelcastInstance instance){
	this.instance = instance;
	this.ex = instance.getExecutorService("exec");
    }

    public void addListener(EntryListener<Object, Object> mapListener){
	this.mapListener = mapListener;
    }

    /**
     *  We need to figure out how to check the operation is successful
     *
     */
    public boolean create(String name, Object key, Object value, Boolean replicate){
	Future<Boolean> result =
	    this.ex.submitToKeyOwner(new MapDAOTask
				     (EntryEventType.ADDED, name, key, value, replicate), key);
	try {
	    return result.get();
	}
	catch (InterruptedException e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	    return false;
	}
	catch(ExecutionException e){
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	    return false;
	}
    }
    
    public Object read(String name, Object key){
	// TO-DO: use distributed queries here
	return instance.getMap(name).get(key);
    }
    
    public boolean update(String name, Object key, Object value, Boolean replicate){
	Future<Boolean> result =
	    this.ex.submitToKeyOwner(new MapDAOTask
				     (EntryEventType.UPDATED, name, key, value, replicate), key);
	try{
	    return result.get();	    
	}
	catch(InterruptedException e){
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	    return false;
	}
	catch(ExecutionException e){
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	    return false;
	}
    }


    /**
     *   Do we need to check whether the actual value matches with the expected?
     *
     */
    public boolean delete(String name, Object key){
	Future<Boolean> result =
	    this.ex.submitToKeyOwner(new MapDAOTask
				     (EntryEventType.REMOVED, name, key, null, true), key);
	try {
	    return result.get();
	}
	catch (InterruptedException e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	    return false;
	}
	catch(ExecutionException e){
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	    return false;
	}
    }

    private class MapDAOTask
	implements Callable<Boolean>, Serializable, HazelcastInstanceAware{
	
	private EntryEventType type;
	private String name;
	private Object key;
	private Object value;
	private Boolean replicate;
	private HazelcastInstance instance;
	
	public MapDAOTask
	    (EntryEventType type, String name, Object key, Object value, Boolean replicate){
	    this.type = type;
	    this.name = name;
	    this.key = key;
	    this.value = value;
	    this.replicate = replicate;
	}
	
	@Override
	public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
	    this.instance = MapDAOImpl.this.instance;
	}
	
	@Override
	public Boolean call(){
	    IMap map = MapDAOImpl.this.instance.getMap(this.name);
	    switch(this.type){
	    case ADDED:
		map.set(this.key, this.value);
		map.addEntryListener(MapDAOImpl.this.mapListener, true);
	    case UPDATED:
		map.put(this.key, this.value);
		if(this.replicate){
		    map.addEntryListener(MapDAOImpl.this.mapListener, true);
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

    }
}
