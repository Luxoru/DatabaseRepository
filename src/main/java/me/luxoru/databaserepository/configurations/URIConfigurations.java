package me.luxoru.databaserepository.configurations;

import lombok.Getter;

@Getter
public class URIConfigurations extends DatabaseConfigurations{

    private final String URI;

    public URIConfigurations(String host, int port, String uri) {
        super(host, port);
        URI = uri;
    }


    @Override
    public URIConfigurations withDebugging() {
        debugging = true;
        return this;
    }
}
