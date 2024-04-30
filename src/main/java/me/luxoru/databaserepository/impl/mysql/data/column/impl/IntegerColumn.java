package me.luxoru.databaserepository.impl.mysql.data.column.impl;

import lombok.Getter;
import me.luxoru.databaserepository.impl.mysql.data.column.Column;


/**
 * Represents an Column which stores integers in a mySQL table
 *
 * @author Luxoru
 */
@Getter
public class IntegerColumn extends Column<Integer> {

    /**
     * Stores whether Column in auto incrementing
     * @see me.luxoru.databaserepository.impl.mysql.data.Table Table - Where the Column is used to create the table with auto increment
     */
    private final boolean autoIncrement;

    /**
     * Creates a Integer Column which is used when creating a Table
     * @see me.luxoru.databaserepository.impl.mysql.data.Table
     * @param name - Name of the column
     * @param nullable - Whether the Column is nullable
     * @param autoIncrement - Whether the column can increment
     */
    public IntegerColumn(String name, boolean nullable, boolean autoIncrement) {
        super(name,1, nullable); //Lengths of all types but String are 1
        this.autoIncrement = autoIncrement;
    }

    /**
     * Used to store data in Column
     * @param name - Name of column to store data into
     * @param value - Value of data to store into Column
     */
    public IntegerColumn(String name, Integer value) {
        super(name, value);
        this.autoIncrement = false;
    }


    /**
     * Used for when creating tables used for formatting in MySQL
     * @return - Column type which is used in creating table
     * @see me.luxoru.databaserepository.impl.mysql.data.Table
     */
    @Override
    public String getType() {
        return "int";
    }
}
