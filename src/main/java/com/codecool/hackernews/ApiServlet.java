package com.codecool.hackernews;

import com.codecool.hackernews.dao.NewsDao;
import com.codecool.hackernews.dao.NewsDaoImpl;
import com.codecool.hackernews.dto.Endpoints;
import com.codecool.hackernews.dto.News;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "apiServlet", value = "/api")
public class ApiServlet extends HttpServlet {
    private final NewsDao newsDao = new NewsDaoImpl();
    private final Gson gson = new Gson();

    @Override
    public void init() {
        String message = "Hello world";
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String category = req.getParameter("category");

        if (category == null || category.trim().isEmpty()) {
            resp.sendError(400, "No category provided");

            return;
        }

        int page;

        try {
            page = Integer.parseInt(req.getParameter("page"));

            if (page < 1) {
                resp.sendError(400, "Invalid page");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendError(400, "Invalid page");
            return;
        }

        Endpoints endpoint;

        try {
            endpoint = Endpoints.getByEndpoint(category);
        } catch (IllegalArgumentException e) {
            resp.sendError(404, "Category not found");
            return;
        }

        List<News> allNews = newsDao.readNews(endpoint, page);
        resp.setContentType("application/json");
        resp.getWriter().println(gson.toJson(allNews));
    }
}
