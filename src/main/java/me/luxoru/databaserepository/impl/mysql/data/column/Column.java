package me.luxoru.databaserepository.impl.mysql.data.column;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * Represents Column in MySQL database
 * T - Data type the column holds
 *
 * @author Luxoru
 */
@AllArgsConstructor
@RequiredArgsConstructor
@Getter

public abstract class Column<T> {

    private final String name;

    private T value;
    private final int length;
    private final boolean nullable;


    /**
     * Creates a Column with Type T with a value of type T
     * @param name - Name of Column
     * @param value - Value of data in Column
     */
    protected Column(String name, T value) {
        this(name, value,1, false);
    }

    /**
     * Gets MySQL type for this Column e.g varchar, int
     * @return - String formatted in MySQl syntax
     */
    public abstract String getType();

}
