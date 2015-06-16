package CIBGrp5;

import com.hazelcast.config.*;
import com.hazelcast.client.config.*;

import java.util.List;
import java.util.ArrayList;

public class ClusterConfig{
    private Config config;
    private List<ClientConfig> replicationTargetConfigs = new ArrayList<ClientConfig>();

    public ClusterConfig setConfig(Config config){
	this.config = config;
	return this;
    }

    public Config getConfig(){
	return this.config;
    }
    
    public ClusterConfig addReplicationTargetConfig(ClientConfig replicationTargetConfig){
	this.replicationTargetConfigs.add(replicationTargetConfig);
	return this;
    }

    public List<ClientConfig> getReplicationTargetConfigs(){
	return this.replicationTargetConfigs;
    }
}
