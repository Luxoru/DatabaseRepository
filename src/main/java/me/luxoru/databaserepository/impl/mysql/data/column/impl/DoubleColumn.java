package me.luxoru.databaserepository.impl.mysql.data.column.impl;


import me.luxoru.databaserepository.impl.mysql.data.column.Column;

public class DoubleColumn extends Column<Double> {
    public DoubleColumn(String name, int length, boolean nullable) {
        super(name, length, nullable);
    }

    protected DoubleColumn(String name, Double value) {
        super(name, value);
    }

    @Override
    public String getType() {
        return "DOUBLE";
    }
}
