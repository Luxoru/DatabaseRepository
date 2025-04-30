package me.luxoru.databaserepository.impl.neo4j;

public class RelationQueryBuilder {


    private final char a;
    private final char b;
    private final String relation;
    private final boolean isBiDirectional;
    private final StringBuilder relationBuilder;
    private final NeoQueryBuilder builder;

    public RelationQueryBuilder(char a, char b, String relation, boolean isBiDirectional, NeoQueryBuilder builder, StringBuilder relationBuilder){
        this.a = a;
        this.b = b;
        this.relation = relation;
        this.isBiDirectional = isBiDirectional;
        this.relationBuilder = relationBuilder;
        this.builder = builder;
    }

    public NeoQueryBuilder create(){

        relationBuilder.append(" MERGE (%s)-[:%s]->(%s)".formatted(a,relation,b));
        if(isBiDirectional){
            relationBuilder.append(" MERGE (%s)-[:%s]->(%s)".formatted(b,relation,a));
        }

        return builder;
    }

    public NeoQueryBuilder remove(){

        relationBuilder.append(" MATCH (%s)-[rel:%s]->(%s) DELETE rel".formatted(a,relation,b));
        if(isBiDirectional){
            relationBuilder.append(" WITH %s, %s".formatted(a,b));
            relationBuilder.append(" MATCH (%s)-[rel:%s]->(%s) DELETE rel".formatted(b,relation,a));
        }

        return builder;
    }

}
