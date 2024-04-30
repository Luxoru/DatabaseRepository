package me.luxoru.databaserepository.impl.mysql.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.luxoru.databaserepository.impl.mysql.data.column.Column;
import me.luxoru.databaserepository.impl.mysql.data.column.impl.IntegerColumn;


@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Table {


    private final @NonNull String name;
    private final Column<?>[] columns;
    private String[] primaryKeys;

    public String getOrCreateTableQuery(boolean createIfExists){
        if(columns.length < 1){
            throw new IllegalArgumentException("Columns has to be greater or equal to 1");
        }

        StringBuilder sb = new StringBuilder("CREATE TABLE "+(createIfExists ? "" : "IF NOT EXISTS `")+name+"`(");


        for(Column<?> column : columns){
            sb.append(column.getName()).append(" ");
            sb.append(column.getType()).append(" ");

            int columnLength = column.getLength();

            if(columnLength > 1){
                sb.append("(").append(columnLength).append(")");
            }

            if(column instanceof IntegerColumn integerColumn){
                if(integerColumn.isAutoIncrement()){
                    if(integerColumn.isNullable()){
                        throw new IllegalArgumentException("Auto incrementing cannot be null");
                    }
                    sb.append("auto_increment");
                }
            }

            if(!column.isNullable()){
                sb.append(" not null");
            }

            sb.append(",");

        }



        if(primaryKeys.length > 0){
            sb.append("PRIMARY KEY (");
        }
        for(String string : primaryKeys){

            sb.append(string).append(", ");
        }

        return sb.toString().substring(0, sb.toString().length()-2) + "));";


    }


}
