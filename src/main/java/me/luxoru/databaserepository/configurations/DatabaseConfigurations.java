package me.luxoru.databaserepository.configurations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DatabaseConfigurations {

    private final String host;
    private final int port;

    protected boolean debugging;

    public DatabaseConfigurations withDebugging(){
        debugging = true;
        return this;
    }


}
