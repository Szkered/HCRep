package CIBGrp5;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;
import java.util.Queue;

public class App {
    public static void main( String[] args ) {
	HazelcastInstance Instance = Hazelcast.newHazelcastInstance();

	// Map
	// Map<Integer, String> map = Instance.getMap("a");
	// map.put( 1, "A" );
	// map.put( 2, "B" );
	// map.put( 3, "C" );

	// System.out.println( "Customer with key 1: " + map.get(1) );
	// System.out.println( "Map Size:" + map.size() );

	//Queue
	Queue<String> q = Instance.getQueue( "queue" );
	q.offer( "Je" );
	q.offer( "Te" );
	q.offer( "Veux" );
	System.out.println( "1: " + q.poll() );
	System.out.println( "2: "+ q.peek() );
	System.out.println( "queue size: " + q.size() );
    }
}
