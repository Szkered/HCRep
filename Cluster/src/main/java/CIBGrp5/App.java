package CIBGrp5;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

import java.util.Map;
import java.util.Queue;

public class App {
    public static void main( String[] args ) {
	HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
	Map<Integer, String> customers = hazelcastInstance.getMap( "customers" );
	customers.put( 1, "A" );
	customers.put( 2, "B" );
	customers.put( 3, "C" );

	System.out.println( "Customer with key 1: " + customers.get(1) );
	System.out.println( "Map Size:" + customers.size() );

	Queue<String> queueCustomers = hazelcastInstance.getQueue( "customers" );
	queueCustomers.offer( "Tom" );
	queueCustomers.offer( "Mary" );
	queueCustomers.offer( "Jane" );
	System.out.println( "First customer: " + queueCustomers.poll() );
	System.out.println( "Second customer: "+ queueCustomers.peek() );
	System.out.println( "Queue size: " + queueCustomers.size() );
    }
}
