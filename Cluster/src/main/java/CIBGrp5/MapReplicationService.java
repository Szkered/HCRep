package CIBGrp5;

import com.hazelcast.core.*;

public interface MapReplicationService{
    
    public void replicate(EntryEvent<Object, Object> event);
    public EntryListener getMapListener();
    
}
