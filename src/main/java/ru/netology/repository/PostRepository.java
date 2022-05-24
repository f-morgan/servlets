package ru.netology.repository;

import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
  private static final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
  private static final AtomicLong lastPostId = new AtomicLong(0);

  public List<Post> all() {
    return posts == null ? Collections.emptyList() : new ArrayList<>(posts.values());
  }

  public Optional<Post> getById(long id) {
    Post post = posts.get(id);
    return post == null ? Optional.empty() : Optional.of(post);
  }


  public Post save(Post post) {
    long postID = post.getId();
    if ((postID==0) || (getById(postID).equals(Optional.empty()))) {
      post.setId(lastPostId.incrementAndGet());
    }
    posts.put(post.getId(), post);
    return post;

  }

  public Boolean removeById(long id) {
    if (posts.containsKey(id)) {
      posts.remove(id);
      return true;
    } else {
      return false;
    }
  }

}
