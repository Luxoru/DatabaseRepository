import lombok.extern.slf4j.Slf4j;
import me.luxoru.databaserepository.impl.redis.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.redisson.api.RedissonClient;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;


public class RedisTest {


    private RedisDatabase database;
    private RedissonClient client;


    @Test
    public void connect(){
         this.database = new RedisDatabase()
                .addMasterNode(new RedisNode("SLAVE", "127.0.0.1",RedisConfigurations.DEFAULT_PORT, RedisNodeType.MASTER))
                .connect(new RedisConfigurations(0));

         this.client = database.getClient();

    }


    @Test
    public void setAndGet(){

        connect();

        int number = ThreadLocalRandom.current().nextInt(0,9999);

        client.getBucket("number").set(number);

        int valueReceived = (int) client.getBucket("number").get();



        assertEquals(number, valueReceived);


    }

    @Test
    public void test(){
        String channel = "redis:COMMAND";
        System.out.println(Arrays.toString(channel.split(":")));
    }

    @Test
    public void subAndPub() throws InterruptedException {
        connect();

        database.getMessangerService().addSubscriber(new RedisMessenger() {

            @Override
            public void onPMessage(String pattern, String channel, String message) {
                System.out.println("Received: " + message);
                System.out.println("Channel: "+channel);
            }

            @Override
            public String[] getChannels() {
                return new String[]{"test.*"};
            }

            @Override
            public boolean isUsingPatterns() {
                return true;
            }
        });

        Thread.sleep(1000);


        database.getMessangerService().dispatch("test.sus", "SUSSY WHAT IS UP!");
        database.getMessangerService().dispatch("test.sadas.xs", "SADSA WHAT IS UP!");

    }

}
