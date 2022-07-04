package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;
  final Gson gson = new Gson();

  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var data = service.all();
    writeAnswer(response, gson.toJson(data));
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var data = service.getById(id);
    writeAnswer(response, gson.toJson(data));
  }

  public void save(Reader body, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var postForSave = gson.fromJson(body, Post.class);
    if (postForSave.getId() != 0) {
        var savedPost = service.getById(postForSave.getId());
    }
    savePost(response, postForSave);
  }

    private void savePost(HttpServletResponse response, Post postForSave) throws IOException {
        final var data = service.save(postForSave);
        writeAnswer(response, gson.toJson(data));
    }

    private void writeAnswer(HttpServletResponse response, String s) throws IOException {
    response.getWriter().print(s);
  }

  public void removeById(long id, HttpServletResponse response) {
    service.removeById(id);
    response.setStatus(HttpServletResponse.SC_OK);
  }
}
