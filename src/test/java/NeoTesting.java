import me.luxoru.databaserepository.configurations.AuthenticationConfigurations;
import me.luxoru.databaserepository.configurations.URIConfigurations;
import me.luxoru.databaserepository.impl.neo4j.NeoConfigurations;
import me.luxoru.databaserepository.impl.neo4j.NeoDatabase;

import me.luxoru.databaserepository.impl.neo4j.NeoRepository;
import me.luxoru.databaserepository.impl.neo4j.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

class NeoTesting {

    private static NeoDatabase databaseProvider;
    private static NeoRepository repository;
    private static boolean isConnected;


    @BeforeEach
    void init(){

        databaseProvider = new NeoDatabase("neo4j");
        databaseProvider.connect( new NeoConfigurations(
                "bolt://localhost:7687",
                "neo4j", "password")
        );
        isConnected = databaseProvider.isConnected();

        repository = new NeoRepository(databaseProvider);
    }

    @Test
    void addShit(){
        if(!isConnected){
            return;
        }
        Node des = new Node();

        des.setLabel("player");
        des.addProperty("name", "Des");

        Node bray = new Node();
        bray.setLabel("player");
        bray.addProperty("name", "Bray");

        repository.createNode(des).join();
        repository.createNode(bray).join();

        repository.createRelation(des, bray, "FRIEND", true).join();

        List<Node> friends = repository.getNodeRelations(des, "FRIEND").join();

        for(Node friend : friends){
            System.out.println("FRIEND: "+friend.getProperties().get("name"));
        }

        Node updatedDes = new Node();
        updatedDes.setLabel("player");
        updatedDes.addProperty("name", "Luxoru");
        repository.updateNode(des, updatedDes).join();




    }

}
