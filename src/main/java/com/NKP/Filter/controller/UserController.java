package com.NKP.Filter.controller;

import com.NKP.Filter.dto.CreatePostDTO;

import com.NKP.Filter.dto.UpdatePersonalDataDTO;
import com.NKP.Filter.dto.UpdatePostDTO;
import com.NKP.Filter.dto.UserProfileDTO;
import com.NKP.Filter.model.Post;

import com.NKP.Filter.service.PostService;
import com.NKP.Filter.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;

    // returns profile of users by searching their username
    @GetMapping("/{username}")
    public ResponseEntity<UserProfileDTO> userProfile(@PathVariable String username){
        UserProfileDTO profile = userService.getUserProfile(username);
//        System.out.println("User profile");
        return new ResponseEntity<>(profile,HttpStatus.OK);
    }

    // delete user account permanently
    @DeleteMapping("/{username}")
    public String deleteAccount(@PathVariable String username,
                                Authentication authentication){

        if(!authentication.getName().equals(username)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot delete other accounts");
        }
        userService.deleteAccount(username);
        return "Account deleted successfully";
    }

    // get all the posts posted by the user
    @GetMapping("/posts")
    public List<Post> myPosts(Authentication authentication){

        String username = authentication.getName();
        return userService.getPostByUser(username);
    }


    // post the crime content
    @PostMapping(value = "/post", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createPost(Authentication authentication, @Valid @ModelAttribute CreatePostDTO postDTO){
        String username =  authentication.getName();

        postService.createPost(username,postDTO);

        return new ResponseEntity<>("Post uploaded", HttpStatus.CREATED);
    }

    @PutMapping("/posts/{postId}")
    public String updatePost(Authentication authentication,@PathVariable long postId,@RequestBody UpdatePostDTO updatePostDTO){

        String username = authentication.getName();

        postService.updatePost(username,postId,updatePostDTO);
        return "Succesfully updated your post";
    }

    @PutMapping("/me")
    public String updatePersonalDetails(
            Authentication authentication,
            @Valid @RequestBody UpdatePersonalDataDTO personalDataDTO
    ){
        String username = authentication.getName();

        userService.updateUserData(username,personalDataDTO);
        return "User details updated succesfully";
    }
}
