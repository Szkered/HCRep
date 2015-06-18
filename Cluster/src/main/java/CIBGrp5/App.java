package CIBGrp5;

import com.hazelcast.core.*;

public class App{
    public static void main(String[] args) {
	OperationManager.startInstance("master-node");
	OperationManager.startReplicationService();

    }
}
