package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryImpl implements PostRepository{
    private static final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private static final AtomicLong lastPostId = new AtomicLong(0);

    @Override
    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    @Override
    public Optional<Post> getById(long id) {
        Post post = posts.get(id);
        return post == null ? Optional.empty() : Optional.of(post);
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(lastPostId.incrementAndGet());
        }
        posts.put(post.getId(), post);
        return post;
    }

    @Override
    public void removeById(long id) {
            posts.remove(id);
    }
}
