package CIBGrp5;

import java.io.*;
import java.net.*;

public class App{
    public static void main(String[] args) {
	OperationManager.startInstance("master-node");
	OperationManager.startReplicationService();
    }
}
