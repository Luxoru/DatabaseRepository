package me.luxoru.databaserepository;

import me.luxoru.databaserepository.configurations.AuthenticationConfigurations;
import me.luxoru.databaserepository.configurations.DatabaseConfigurations;

public interface IDatabase<E extends DatabaseConfigurations> {

    IDatabase<E> connect(E configurations);

}
