package me.luxoru.databaserepository.impl.neo4j;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.luxoru.databaserepository.IDatabase;

import me.luxoru.databaserepository.configurations.URIConfigurations;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

@RequiredArgsConstructor
public class NeoDatabase implements IDatabase<URIConfigurations> {

    @Getter
    private Driver driver;
    @Getter
    private final String database;
    private boolean connected;

    @Override
    public IDatabase<URIConfigurations> connect(URIConfigurations configurations) {
        String uri = configurations.getURI();
        String user  = configurations.getAuthenticationConfigurations().getUsername();
        String pass = configurations.getAuthenticationConfigurations().getPassword();
        System.out.println("URI: "+uri);
        System.out.println("Username: "+user);
        System.out.println("Password: "+pass);
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, pass,""));
        driver.verifyConnectivity();
        connected = true;
        return this;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }
}
