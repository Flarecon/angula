package com.example.angula.controller;

import com.example.angula.database.repository.PostRepo;
import com.example.angula.database.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostRepo postRepo;


    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepo.save(post);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable("id") Long id) {
        return postRepo.findById(id).orElseThrow();
    }
    @GetMapping
    public List<Post> getAllPost() {
        return postRepo.findAll();
    }

    @GetMapping("/title/{title}")
    public Page<Post> getPostByTitle(@PathVariable("title") String title, Pageable pageable) {
        return postRepo.findByTitleContaining(title, pageable);
    }

    @GetMapping("/content/{content}")
    public Page<Post> getPostByContent(@PathVariable("content") String content, Pageable pageable) {
        return postRepo.findByContentContaining(content, pageable);
    }

    @GetMapping("/search")
    public Page<Post> getPostBySearch(
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            Pageable pageable) {
        return postRepo.findByCreatedAtBetween(fromDate, toDate, pageable);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
        Post existingPost = getPost(id);
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());
        return postRepo.save(existingPost);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postRepo.deleteById(id);
    }


}
