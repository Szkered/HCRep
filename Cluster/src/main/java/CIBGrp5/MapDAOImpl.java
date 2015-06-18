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

    @Override
    public void setMapListener(EntryListener<Object, Object> mapListener){
	this.mapListener = mapListener;
    }

    @Override
    public Boolean submitDAOTask(EntryEventType type, String name, Object key, Object value, Boolean replicate){
	MapDAOTask t = new MapDAOTask(type, name, key, value, replicate, this.mapListener);
	Future<Boolean> result = this.ex.submitToKeyOwner(t, key);
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

    /**
     *  We need to figure out how to check the operation is successful
     *
     */
    @Override
    public Boolean create(String name, Object key, Object value, Boolean replicate){
	return this.submitDAOTask(EntryEventType.ADDED, name, key, value, replicate);
    }

    @Override
    public Object read(String name, Object key){
	// TO-DO: use distributed queries here
	return instance.getMap(name).get(key);
    }

    @Override
    public Boolean update(String name, Object key, Object value, Boolean replicate){
	return this.submitDAOTask(EntryEventType.UPDATED, name, key, value, replicate);
    }

    /**
     *   Do we need to check whether the actual value matches with the expected?
     *
     */
    @Override
    public Boolean delete(String name, Object key){
	return this.submitDAOTask(EntryEventType.REMOVED, name, key, null, false);
    }
}
