package CIBGrp5;

import com.hazelcast.core.*;
import com.hazelcast.client.*;
import com.hazelcast.client.config.*;

import java.io.*;

public class ReplicateTask implements Runnable, Serializable, HazelcastInstanceAware{
    private final EntryEvent<Object, Object> event;
    private HazelcastInstance instance;

    public ReplicateTask(EntryEvent<Object, Object> event){
	this.event = event;
    }

    @Override
    public void run(){
	IMap map = this.instance.getMap(event.getName());
	switch(event.getEventType()){
	case ADDED:
	    map.set(event.getKey(), event.getValue());
	case UPDATED:
	    map.put(event.getKey(), event.getValue());
	case REMOVED:
	    map.delete(event.getKey());
	}
    }
    
    @Override
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance){
	this.instance = hazelcastInstance;
    }
}
