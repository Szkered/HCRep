package CIBGrp5;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ICollection;
import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;    

public class App 
{
    public static void main( String[] args ) {
	HazelcastInstance hz = Hazelcast.newHazelcastInstance();
	ICollection<String> q = hz.getQueue("queue");
	q.addItemListener(new CollectionItemListener<String>(), true);
	System.out.println("Listener started");
    }
    
    private static class CollectionItemListener<E>
	implements ItemListener<E> {

	@Override
	public void itemAdded(ItemEvent<E> ie) {
	    System.out.println("Item added:" + ie.getItem());
	}
    
	@Override
	public void itemRemoved(ItemEvent<E> ie) {
	    System.out.println("Item removed:" + ie.getItem());
	}
    
    }
}
