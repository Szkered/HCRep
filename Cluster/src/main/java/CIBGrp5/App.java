package CIBGrp5;

public class App{
    public static void main(String[] args) {
	ReplicatingCluster c = new ReplicatingCluster();
	c.startInstance("master-node");
	c.startReplicationService();
    }
}
