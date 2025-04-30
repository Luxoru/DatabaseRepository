package me.luxoru.databaserepository.impl.neo4j;

import lombok.Getter;
import me.luxoru.databaserepository.IDatabase;

import me.luxoru.databaserepository.configurations.URIConfigurations;
import org.neo4j.driver.Driver;


import java.io.File;

public class NeoDatabaseProvider implements IDatabase<URIConfigurations> {

    private static NeoDatabaseProvider INSTANCE;
    @Getter
    private Driver driver;

    public NeoDatabaseProvider(File configFile) {
        super(configFile);
        INSTANCE = this;
    }

    public NeoDatabaseProvider() {
        super(new File("neo.properties"));
        INSTANCE = this;
    }


}
