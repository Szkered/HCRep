package CIBGrp5;

import com.hazelcast.core.*;

public class IMapCache {
    public static void loadCache(HazelcastInstance instance){
	IMap map = instance.getMap("Testmap");
	map.put(1, "A");
    }
}
