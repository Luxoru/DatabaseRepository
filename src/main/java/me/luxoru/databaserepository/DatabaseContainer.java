package me.luxoru.databaserepository;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class DatabaseContainer<T extends IDatabase<?>> {

    protected final T database;

}
