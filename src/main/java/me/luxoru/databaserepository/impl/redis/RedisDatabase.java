package me.luxoru.databaserepository.impl.redis;

import me.luxoru.databaserepository.IDatabase;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;

import java.util.HashSet;
import java.util.Set;

public class RedisDatabase implements IDatabase<RedisConfigurations> {

    private final Set<RedisNode> nodes = new HashSet<>();
    private final Object LOCK = new Object();

    @Override
    public IDatabase<RedisConfigurations> connect(RedisConfigurations configurations) {
        MasterSlaveServersConfig config = configurations.getConfig();

        for(RedisNode node : nodes){
            if(node.getType() == RedisNodeType.MASTER){
                config.setMasterAddress("redis://"+node.getHost()+":"+node.getPort());
            }
            else{
                config.addSlaveAddress("redis://"+node.getHost()+":"+node.getPort());
            }
            if(node.getPassword() != null && !node.getPassword().isEmpty() && !node.getPassword().isBlank()){
                config.setPassword(node.getPassword());
            }
        }

        Config redisConfig = new Config();

        return null;

    }

    public RedisDatabase addMasterNode(String nodeName, String host, int port){
        return addMasterNode(nodeName,host,port,null);
    }

    public RedisDatabase addMasterNode(String nodeName, String host, int port, String password){
        return addMasterNode(new RedisNode(nodeName,host,port,RedisNodeType.MASTER,password));
    }

    public RedisDatabase addMasterNode(RedisNode node){
        synchronized (LOCK){
            if(node.getType() != RedisNodeType.MASTER){
                throw new IllegalArgumentException("Expected Master node type but got "+node.getType());
            }
            nodes.add(node);
        }
        return this;
    }

    public RedisDatabase addSlaveNode(String nodeName, String host, int port){
        return addSlaveNode(nodeName,host,port,null);
    }

    public RedisDatabase addSlaveNode(String nodeName, String host, int port, String password){
        return addSlaveNode(new RedisNode(nodeName,host,port,RedisNodeType.SLAVE,password));
    }

    public RedisDatabase addSlaveNode(RedisNode node){
        synchronized (LOCK){
            nodes.add(node);
        }
        return this;
    }


    public RedisNode getNode(String nodeName){
        synchronized (LOCK){
            for(RedisNode node : nodes){
                if(node.getNodeName().equals(nodeName)){
                    return node;
                }
            }
        }
        return null;
    }




}
