package com.NKP.Filter.controller;

import com.NKP.Filter.model.Post;
import com.NKP.Filter.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // delete post by post id
    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable long postId,
                             Authentication authentication){
        String username = authentication.getName();
        postService.deletePost(postId, username);
        return "Post deleted successfully";
    }

    // get post based on post id
    @GetMapping("/{postId}")
    public Post getPost(@PathVariable long postId){
        return postService.getPost(postId);
    }

    // filter using either city or name
    @GetMapping("/search")
    public ResponseEntity<Page<Post>> filter(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String name,
            Pageable pageable
    ){
        return new ResponseEntity<>(postService.filterPosts(city, name, pageable),HttpStatus.OK);

    }

    @GetMapping
    public ResponseEntity<Page<Post>> allPosts(Pageable pageable){
        return new ResponseEntity<>(postService.getAllPosts(pageable), HttpStatus.OK);
    }
}
