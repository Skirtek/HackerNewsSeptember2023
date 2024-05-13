package com.codecool.hackernews.dao;

import com.codecool.hackernews.dto.Endpoints;
import com.codecool.hackernews.dto.News;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;

import javax.net.ssl.HttpsURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class NewsDaoImpl implements NewsDao {
    private final Gson gson = new Gson();

    @SneakyThrows
    @Override
    public List<News> readNews(Endpoints endpoint, int page) {
        String urlString = String.format("https://api.hnpwa.com/v0/%s/%d.json", endpoint.getEndpoint(), page);
        URL url = URI.create(urlString).toURL();

        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
        httpsURLConnection.setRequestMethod("GET");

        String response = new String(httpsURLConnection.getInputStream().readAllBytes());

        News[] news = gson.fromJson(response, News[].class);

        return Arrays.asList(news);
    }

    public String getNewsAsJson(Endpoints endpoint, int page) {
        // setPrettyPrinting że printuje sformatowany
        return new GsonBuilder().setPrettyPrinting().create().toJson(readNews(endpoint, page));
    }
}
