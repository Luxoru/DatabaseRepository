package me.luxoru.databaserepository.impl.mysql.data.column.impl;


import me.luxoru.databaserepository.impl.mysql.data.column.Column;

public class LongColumn extends Column<Long> {
    public LongColumn(String name, boolean nullable) {
        super(name, 1, nullable);
    }

    public LongColumn(String name, Long value) {
        super(name, value);
    }

    @Override
    public String getType() {
        return "LONG";
    }
}
