package me.luxoru.databaserepository.impl.redis;

import lombok.Getter;
import me.luxoru.databaserepository.configurations.AuthenticationConfigurations;
import org.redisson.config.MasterSlaveServersConfig;

@Getter
public class RedisConfigurations extends AuthenticationConfigurations{

    public static final int DEFAULT_PORT = 6379;
    private final int databaseNumber;
    private final MasterSlaveServersConfig config;



    public RedisConfigurations(int databaseNumber) {
        super(null, -1, null, null);
        this.databaseNumber = databaseNumber;
        this.config = new MasterSlaveServersConfig();
        this.config
                .setDatabase(databaseNumber)
                .setIdleConnectionTimeout(1000)
                .setTimeout(1000)
                .setSlaveConnectionMinimumIdleSize(5);

    }

    public RedisConfigurations(int databaseNumber, MasterSlaveServersConfig config){
        super(null,-1,null,null);
        this.databaseNumber = databaseNumber;
        this.config = config;
    }


}
