package me.luxoru.databaserepository.impl.neo4j;

import lombok.Getter;
import me.luxoru.databaserepository.configurations.AuthenticationConfigurations;

@Getter
public class NeoConfigurations extends AuthenticationConfigurations {

    private final String URI;

    public NeoConfigurations(String URI, String username, String password) {
        super(null, -1, username, password);
        this.URI = URI;
    }
}
