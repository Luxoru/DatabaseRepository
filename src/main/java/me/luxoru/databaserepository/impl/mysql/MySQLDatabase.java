package me.luxoru.databaserepository.impl.mysql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.luxoru.databaserepository.IDatabase;
import me.luxoru.databaserepository.configurations.AuthenticationConfigurations;


import java.sql.Connection;
import java.sql.SQLException;

@Getter
@RequiredArgsConstructor
@Slf4j(topic = "MySQLDatabase")
public class MySQLDatabase implements IDatabase<AuthenticationConfigurations> {

    private final String databaseName;
    private HikariDataSource dataSource;
    private Connection connection;

    @Override
    public MySQLDatabase connect(AuthenticationConfigurations configurations){

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://"+configurations.getHost()+":"+configurations.getPort()+"/"+databaseName);
        config.setUsername(configurations.getUsername());
        config.setPassword(configurations.getPassword());

        dataSource = new HikariDataSource(config);

        if(configurations.isDebugging()){
            log.debug("Connection established for "+dataSource+".");
        }

        try{
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return this;

    }



    public MySQLContainer createContainer(){

        return new MySQLContainer(this);

    }


}
