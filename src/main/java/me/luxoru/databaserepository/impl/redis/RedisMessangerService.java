package me.luxoru.databaserepository.impl.redis;

import lombok.NonNull;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;

import java.util.function.Consumer;


public class RedisMessangerService {


    private final RedissonClient client;

    public RedisMessangerService(RedisDatabase database) {
        this.client = database.getClient();
    }

    public void addSubscriber(@NonNull RedisMessenger messenger){
        addSubscriber(messenger, null);
    }

    public void addSubscriber(@NonNull RedisMessenger messenger, Consumer<Throwable> onException){

        for(String channel : messenger.getChannels()){
            RTopic topic = client.getTopic(channel);
            topic.addListenerAsync(String.class, (channelName, message) ->{
                messenger.onMessage(channel, message);
            }).exceptionally(throwable -> {
                if(onException != null){
                    onException.accept(throwable);
                }
                return null;
            });

        }

    }


    public void dispatch(String channelName, String message){

        RTopic topic = client.getTopic(channelName);
        topic.publishAsync(message);

    }

}
