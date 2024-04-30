package me.luxoru.databaserepository.impl.mysql.data.column.impl;

import lombok.Getter;
import me.luxoru.databaserepository.impl.mysql.data.column.Column;


@Getter
public class VarcharColumn extends Column<String> {



    public VarcharColumn(String name, boolean nullable, int length) {
        super(name, length, nullable);
    }

    public VarcharColumn(String name, String value) {
        super(name, value);
    }

    @Override
    public String getType() {
        return "varchar";
    }
}
