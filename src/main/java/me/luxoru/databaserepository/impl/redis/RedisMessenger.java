package me.luxoru.databaserepository.impl.redis;

public interface RedisMessenger {

    default void onMessage(String channel, String message){}

    String[] getChannels();

}
