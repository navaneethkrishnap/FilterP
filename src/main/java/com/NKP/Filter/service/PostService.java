package com.NKP.Filter.service;

import com.NKP.Filter.dto.CreatePostDTO;
import com.NKP.Filter.dto.UpdatePostDTO;
import com.NKP.Filter.model.*;
import com.NKP.Filter.repo.PostRepository;
import com.NKP.Filter.repo.UserRepository;
import com.NKP.Filter.service.cloudStorage.SaveImageToCloudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SaveImageToCloudService imageService;


    public void createPost(String username, CreatePostDTO postDTO)
    {

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));

        Post post = new Post();
        post.setUser(user);

        PostDetails postDetails = new PostDetails();
        postDetails.setDescription(postDTO.getDescription());
        postDetails.setPost(post);


        Criminal criminal = Criminal.builder()
                .criminalName(postDTO.getCriminalName())
                .criminalAge(Optional.ofNullable(postDTO.getCriminalAge()).orElse(0))
                .crimeArea(postDTO.getArea())
                .crimeCity(postDTO.getCity())
                .crimeScene(postDTO.getCrimeSceneType())
                .build();

        postDetails.setCriminal(criminal);

        post.setPostDetails(postDetails);

        List<CriminalMedia> mediaList = new ArrayList<>();


        for(MultipartFile file : postDTO.getImages()){

            Map<String,String> imageData = imageService.uploadImage(file);

            CriminalMedia media = new CriminalMedia();
            media.setMediaUrl(imageData.get("url"));
            media.setCloudImageId(imageData.get("cloudImageId"));
            media.setPost(post);

            mediaList.add(media);
        }

        post.setMedia(mediaList);

        postRepository.save(post);
    }

    // delete post
    public void deletePost(long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if(!post.getUser().getUsername().equals(username)){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You cannot delete other user's post");
        }

        // deleting files from cloud
        for(CriminalMedia media : post.getMedia()){
            imageService.deleteImage(media.getCloudImageId());
        }

        postRepository.delete(post);
    }

    // filter post
    public Page<Post> filterPosts(String city, String name, Pageable pageable){

        if(city != null && name != null){
            return postRepository
                    .findByPostDetailsCriminalCrimeCityIgnoreCaseAndPostDetailsCriminalCriminalNameContainingIgnoreCase(
                            city,name,pageable
                    );
        }

        if(city != null){
            return postRepository
                    .findByPostDetailsCriminalCrimeCityIgnoreCase(city,pageable);
        }

        if(name != null){
            return postRepository
                    .findByPostDetailsCriminalCriminalNameContainingIgnoreCase(name,pageable);
        }

        return postRepository.findAll(pageable);
    }

    // pageable post
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post getPost(long postId) {
        return postRepository.findById(postId)
                .orElseThrow(()-> new RuntimeException("Post not found"));
    }

    public List<Post> getPostByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return postRepository.findByUserUsername(user.getUsername());
    }

    @Transactional
    public void updatePost(String username, long postId, UpdatePostDTO updatePostDTO){

        Post post = postRepository
                .findByPostIdAndUserUsername(postId, username)
                .orElseThrow(() -> new RuntimeException("Post not found or unauthorized"));


        PostDetails postDetails = post.getPostDetails();
        Criminal criminal = postDetails.getCriminal();

        if(updatePostDTO.getDescription() != null){
            postDetails.setDescription(updatePostDTO.getDescription());
        }

        if(updatePostDTO.getCity() != null){
            criminal.setCrimeCity(updatePostDTO.getCity());
        }

        if(updatePostDTO.getArea() != null){
            criminal.setCrimeArea(updatePostDTO.getArea());
        }

        if(updatePostDTO.getCriminalAge() != null){
            criminal.setCriminalAge(updatePostDTO.getCriminalAge());
        }

        if(updatePostDTO.getCriminalName() != null){
            criminal.setCriminalName(updatePostDTO.getCriminalName());
        }

    }
}
