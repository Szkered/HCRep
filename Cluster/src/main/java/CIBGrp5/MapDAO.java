package CIBGrp5;

import com.hazelcast.core.*;

public interface MapDAO extends DistributedDAO{

    public void addListener(EntryListener<Object, Object> mapListener);
    
}
