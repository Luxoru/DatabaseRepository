package me.luxoru.databaserepository.impl.redis;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class RedisNode {

    private final String nodeName;
    private final String host;
    private final int port;
    private final RedisNodeType type;
    private String password;


}
