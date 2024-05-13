package com.codecool.hackernews.dto;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class News {
    private String title;
    @SerializedName("time_ago")
    private String timeAgo;
    private String user;
    private String url;
}
