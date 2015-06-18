package CIBGrp5;

public class App{
    public static void main(String[] args) {
	OperationManager.startInstance("master-node");
	OperationManager.startReplicationService();
    }
}
