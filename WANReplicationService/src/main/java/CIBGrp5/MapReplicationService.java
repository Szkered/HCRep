package CIBGrp5;

import com.hazelcast.core.*;

public interface MapReplicationService{
    
    public EntryListener<Object, Object> getReplicatingListener();

}
