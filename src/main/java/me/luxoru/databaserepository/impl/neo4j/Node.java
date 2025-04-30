package net.scripted.database.neo4j;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.*;


@Getter
public class Node {

    @Setter
    private String label;

    private final Map<String, Object> relationships = new HashMap<>();
    private final Map<String, Object> properties = new HashMap<>();

    public void addProperty(String key, Object value){
        properties.put(key, value);
    }

    public void removeProperty(String key, Object value){
        properties.remove(key);
    }

    public void addRelationship(String relationship, Object value){
        relationships.put(relationship, value);
    }

    public void removeRelationship(String relationship){
        relationships.remove(relationship);
    }

    public Map<String, Object> getProperties() {
        return Map.copyOf(properties);
    }


    public Map<String, Object> getRelationships() {
        return Map.copyOf(relationships);
    }
}
