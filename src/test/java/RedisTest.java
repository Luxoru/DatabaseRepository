import lombok.extern.slf4j.Slf4j;
import me.luxoru.databaserepository.impl.redis.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

@Slf4j(topic = "RedisTest")
public class RedisTest {


    private RedisDatabase database;
    private RedissonClient client;


    @Test
    public void connect(){
         this.database = new RedisDatabase()
                .addMasterNode(new RedisNode("MASTER", "127.0.0.1",RedisConfigurations.DEFAULT_PORT, RedisNodeType.MASTER))
                .connect(new RedisConfigurations(0));

         this.client = database.getClient();
    }


    @Test
    public void setAndGet(){

        connect();

        int number = ThreadLocalRandom.current().nextInt(0,9999);

        client.getBucket("number").set(number);

        int valueReceived = (int) client.getBucket("number").get();

        log.debug("Value set {} and received {}", number, valueReceived);

        assertEquals(number, valueReceived);


    }

    @Test
    public void subAndPub() throws InterruptedException {
        connect();



        Thread.sleep(1000);

        database.getMessangerService().dispatch("test", "WHAT IS UP!");


    }

}
