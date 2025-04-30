package me.luxoru.databaserepository.impl.neo4j.query;

import lombok.RequiredArgsConstructor;
import me.luxoru.databaserepository.impl.neo4j.Node;
import me.luxoru.databaserepository.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RequiredArgsConstructor
public class NeoQueryBuilder {

    private final String queryType;

    private List<NodeQueryBuilder> nodes = new ArrayList<>();
    private boolean withReturn;
    private UpdateQueryBuilder updateQuery;
    private StringBuilder relationBuilder = new StringBuilder();

    public NodeQueryBuilder withNode(Node node){
        NodeQueryBuilder nodeQueryBuilder = new NodeQueryBuilder(node, this);
        nodes.add(nodeQueryBuilder);
        return nodeQueryBuilder;
    }

    public NeoQueryBuilder withReturn(boolean withReturn){
        this.withReturn = withReturn;
        return this;
    }

    public String build(){
        StringBuilder builder = new StringBuilder();
        List<String> returnChars = new ArrayList<>();
        for(NodeQueryBuilder node : nodes){
            builder.append(queryType.toUpperCase(Locale.ROOT));
            builder.append(" (");

            returnChars.add(String.valueOf(node.getNodeTag()));

            builder.append("%s:%s".formatted(node.getNodeTag(),node.getNode().getLabel().toLowerCase()));

            if(node.isWithProperties()){
                builder.append("{");
                for(Map.Entry<String, Object> entry : node.getNode().getProperties().entrySet()){
                    String typeName = entry.getKey();
                    String typeValue = entry.getValue().toString();

                    builder.append("%s: '%s', ".formatted(typeName, typeValue));
                }
                builder.replace(builder.length()-2, builder.length(), "");
                builder.append("}");
            }

            builder.append(")");


            if(node.getRelationshipMatch() != null){
                String returnChar = StringUtils.generateRandomString(1, false).toLowerCase();
                returnChars.add(returnChar);
                builder.append("-[:%s]->(%s)".formatted(node.getRelationshipMatch(), returnChar));
            }
            builder.append("  ");
        }

        builder.replace(builder.length()-2, builder.length(), "");

        String relationQuery = relationBuilder.toString();

        if(!(relationQuery.isEmpty() || relationQuery.isBlank())){
            builder.append(relationQuery);
        }

        if(updateQuery != null){
            builder.append(updateQuery.getQuery());
        }

        if(withReturn){
            builder.append(" RETURN ");
            for(String label : returnChars){
                builder.append("%s, ".formatted(label));
            }
            builder.replace(builder.length()-2, builder.length(), "");

        }

        return builder.toString();

    }


    public UpdateQueryBuilder withUpdateQuery() {
        this.updateQuery = new UpdateQueryBuilder(this);
        return updateQuery;
    }

    public RelationQueryBuilder relation(char a, char b, String relation, boolean isBiDirectional) {
        return new RelationQueryBuilder(a,b,relation,isBiDirectional,this, relationBuilder);
    }
}
