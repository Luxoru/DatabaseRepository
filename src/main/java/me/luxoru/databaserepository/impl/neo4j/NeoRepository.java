package net.scripted.database.neo4j;

import org.neo4j.driver.Driver;
import org.neo4j.driver.SessionConfig;
import org.neo4j.driver.Value;
import org.neo4j.driver.async.AsyncSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class NeoRepository {

    private final Driver driver;


    public NeoRepository(Driver driver){
        this.driver = driver;
    }

    public CompletableFuture<Node> createNode(Node node){
        AsyncSession session = createSession();
        CompletableFuture<Node> future = new CompletableFuture<>();
        String query = new NeoQueryBuilder("MERGE")
                .withNode(node)
                    .withProperties(true)
                    .create()
                .withReturn(true)
                .build();

        return executeQuery(session, query)
                .thenCompose(nodes -> {
                    future.complete(nodes.getFirst());
                    return future;
                });
    }

    public CompletableFuture<Node> updateNode(Node oldNode, Node newNode){

        AsyncSession session = createSession();
        CompletableFuture<Node> future = new CompletableFuture<>();

        String query = new NeoQueryBuilder("MATCH")
                .withNode(oldNode)
                    .withProperties(true)
                    .withTag('a')
                    .create()

                .withUpdateQuery()
                    .update('a', newNode)
                    .build()
                .withReturn(true)
                .build();


        System.out.println(query);

        return executeQuery(session, query).thenCompose(nodes -> {
            if(nodes.isEmpty()){
                future.completeExceptionally(new IllegalStateException("No nodes updated!"));
            }
            else{
                future.complete(nodes.getFirst());
            }
            return future;
        });
    }

    public CompletableFuture<Node> getNode(Node node){
        AsyncSession session = createSession();
        CompletableFuture<Node> future = new CompletableFuture<>();
        String query = new NeoQueryBuilder("MATCH")
                .withNode(node)
                    .withProperties(true)
                    .create()
                .withReturn(true)
                .build();

        return executeQuery(session, query).thenCompose(nodes -> {
            if(nodes.isEmpty()){
                future.complete(null);
            }
            if(nodes.size() > 1){
                future.completeExceptionally(new IllegalStateException("More than one node found!"));
            }
            if(nodes.size() == 1){
                future.complete(node);
            }
            return future;
        });
    }

    public CompletableFuture<List<Node>> getNodeRelations(Node node, String relation){
        AsyncSession session = createSession();
        CompletableFuture<List<Node>> future = new CompletableFuture<>();
        List<Node> relations = new ArrayList<>();

        String query = new NeoQueryBuilder("MATCH")
                .withNode(node)
                    .withProperties(true)
                    .matchRelationship(relation)
                    .create()
                .withReturn(true)
                .build();

        System.out.println(query);

        return executeQuery(session, query)
                .thenCompose(nodes -> {
                    relations.addAll(nodes);
                    future.complete(relations);
                    return future;
                });

    }


    public CompletableFuture<Boolean> createRelation(Node nodeA, Node nodeB, String relation, boolean isBiDirectional){

        CompletableFuture<Boolean> future = new CompletableFuture<>();
        AsyncSession session = createSession();

        String query = new NeoQueryBuilder("MATCH")
                .withNode(nodeA)
                    .withProperties(true)
                    .withTag('a')
                    .create()
                .withNode(nodeB)
                    .withProperties(true)
                    .withTag('b')
                    .create()
                .relation('a', 'b', relation, isBiDirectional)
                .create()
                .withReturn(true)
                .build();

        System.out.println(query);

        executeQuery(session, query).whenComplete((nodes, err) ->{

            if(err != null){
                future.completeExceptionally(err);
                return;
            }

            future.complete(true);

        }).exceptionally(err -> {
            future.completeExceptionally(err);
            return null;
        });

        return future;
    }

    public CompletableFuture<Boolean> removeRelation(Node nodeA, Node nodeB, String relation, boolean isBiDirectional){
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        AsyncSession session = createSession();

        String query = new NeoQueryBuilder("MATCH")
                .withNode(nodeA)
                    .withProperties(true)
                    .withTag('a')
                    .create()
                .withNode(nodeB)
                    .withProperties(true)
                    .withTag('b')
                    .create()
                .relation('a', 'b', relation, isBiDirectional)
                    .remove()
                .withReturn(true)
                .build();

        executeQuery(session, query).whenComplete((nodes, err) ->{

            if(err != null){
                future.completeExceptionally(err);
                return;
            }

            future.complete(true);

        }).exceptionally(err -> {
            future.completeExceptionally(err);
            return null;
        });

        return future;
    }


    private CompletableFuture<List<Node>> executeQuery(AsyncSession session, String query) {
        CompletableFuture<List<Node>> future = new CompletableFuture<>();
        List<Node> nodes = new ArrayList<>();
        session.executeWriteAsync(tx ->
                tx.runAsync(query).thenCompose(resultCursor ->
                    resultCursor.forEachAsync(record -> {
                        Node node = new Node();
                        List<String> keys = record.keys();
                        for(String key : keys){
                            Value value = record.get(key);
                            Iterable<String> labels = value.asNode().labels();
                            labels.forEach(node::setLabel);
                        }
                        for(Value value : record.values()){
                            Iterable<String> properties = value.keys();
                            for(String property : properties){
                                node.addProperty(property, value.get(property));
                            }
                        }
                        nodes.add(node);
                    }))

        ).whenComplete((res, err) -> {
            session.closeAsync();
            if(err != null){
                future.completeExceptionally(err);
                return;
            }
            future.complete(nodes);

        }).exceptionally(err -> {
            future.completeExceptionally(err);
            return null;
        });
        return future;
    }

    private AsyncSession createSession() {
        return driver.session(
                AsyncSession.class,
                SessionConfig.builder().withDatabase("neo4j").build()
        );
    }
}
