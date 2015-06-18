package CIBGrp5;

import com.hazelcast.core.*;

public interface MapDAO extends DistributedDAO{

    public void setMapListener(EntryListener<Object, Object> mapListener);
    public Boolean submitDAOTask(EntryEventType type, String name, Object key, Object value, Boolean replicate);
    
}
