package me.luxoru.databaserepository.impl.neo4j;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class UpdateQueryBuilder {

    private final NeoQueryBuilder queryBuilder;


    private StringBuilder builder = new StringBuilder();

    public UpdateQueryBuilder update(char oldChar, Node newNode){



        for(Map.Entry<String, Object> entry: newNode.getProperties().entrySet()){
            builder.append(" SET ");
            builder.append("%s.%s = '%s'".formatted(oldChar,entry.getKey(), entry.getValue()));
        }

        return this;

    }

    public String getQuery(){
        return builder.toString();
    }


    public NeoQueryBuilder build(){
        return queryBuilder;
    }
}
