package me.luxoru.databaserepository.configurations;

import lombok.Getter;

@Getter
public class AuthenticationConfigurations extends DatabaseConfigurations{

    private final String username;
    private final String password;

    public AuthenticationConfigurations(String host, int port, String username, String password) {
        super(host, port);
        this.username = username;
        this.password = password;
    }

    @Override
    public AuthenticationConfigurations withDebugging() {
        debugging = true;
        return this;
    }
}
