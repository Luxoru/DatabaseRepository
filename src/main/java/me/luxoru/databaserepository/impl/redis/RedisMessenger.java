package me.luxoru.databaserepository.impl.redis;

public interface RedisMessenger {

    default void onMessage(String channel, String message) {
    }

    default void onPMessage(String pattern, String channel, String message) {
    }

    String[] getChannels();


    default boolean isUsingPatterns() {
        return false;
    }
}
