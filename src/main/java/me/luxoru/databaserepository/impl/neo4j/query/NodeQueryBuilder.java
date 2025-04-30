package me.luxoru.databaserepository.impl.neo4j;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.scripted.commons.StringUtils;

@RequiredArgsConstructor
@Getter
public class NodeQueryBuilder {

    private final Node node;
    private char nodeTag = StringUtils.generateRandomString(1, false).toLowerCase().charAt(0);
    private final NeoQueryBuilder builder;

    private boolean withProperties;
    private boolean withRelationships;
    private String relationshipMatch;

    public NodeQueryBuilder withProperties(boolean withProperties){
        this.withProperties = withProperties;
        return this;
    }

    public NodeQueryBuilder withTag(char tag){
        this.nodeTag = tag;
        return this;
    }

    public NodeQueryBuilder matchRelationship(String relationship){
        this.relationshipMatch = relationship;
        return this;
    }

    public NodeQueryBuilder withRelationships(boolean withRelationships){
        this.withRelationships = withRelationships;
        return this;
    }

    public NeoQueryBuilder create(){
        return builder;
    }


}
