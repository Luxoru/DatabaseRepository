package me.luxoru.databaserepository.impl.mysql.data.column.impl;


import me.luxoru.databaserepository.impl.mysql.data.column.Column;

public class CharColumn extends Column<Character> {
    public CharColumn(String name, Character value, int length, boolean nullable) {
        super(name, value, length, nullable);
    }

    public CharColumn(String name, Character value) {
        super(name, value);
    }

    @Override
    public String getType() {
        return "CHAR";
    }
}
