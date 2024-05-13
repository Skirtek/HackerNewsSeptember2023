package com.codecool.hackernews.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter @AllArgsConstructor
public enum Endpoints {
    HACKSON_NEWS("news"),
    TOP_NEWS("top"),
    NEWEST("newest"),
    JOBS("jobs");

    private final String endpoint;

    public static Endpoints getByEndpoint(String endpoint){
        return Arrays.stream(Endpoints.values()).filter(e -> e.getEndpoint().equals(endpoint))
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
