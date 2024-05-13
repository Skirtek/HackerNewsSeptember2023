package com.codecool.hackernews.dao;

import com.codecool.hackernews.dto.Endpoints;
import com.codecool.hackernews.dto.News;

import java.util.List;

public interface NewsDao {
    List<News> readNews(Endpoints endpoint, int page);
}
