package me.luxoru.databaserepository.impl.redis;

import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;

public final class RedisCustomConfig extends Config {


    @Override
    public void setMasterSlaveServersConfig(MasterSlaveServersConfig masterSlaveConnectionConfig) {
        super.setMasterSlaveServersConfig(masterSlaveConnectionConfig);
    }
}
