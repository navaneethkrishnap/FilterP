package com.NKP.Filter.service;

import com.NKP.Filter.dto.PostDTO;
import com.NKP.Filter.dto.UpdatePersonalDataDTO;
import com.NKP.Filter.dto.UserProfileDTO;
import com.NKP.Filter.model.CriminalMedia;
import com.NKP.Filter.model.Post;
import com.NKP.Filter.model.User;
import com.NKP.Filter.repo.PostRepository;
import com.NKP.Filter.repo.UserRepository;
import com.NKP.Filter.service.cloudStorage.SaveImageToCloudService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final SaveImageToCloudService imageService;


    public void deleteAccount(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        // delete all the files of user first then go with account deletion
        for(Post post : user.getPosts()){
            for(CriminalMedia media : post.getMedia()){
                imageService.deleteImage(media.getCloudImageId());
            }
        }

        userRepository.delete(user);
    }

    public List<Post> getPostByUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return postRepository.findByUserUsername(user.getUsername());
    }

    public UserProfileDTO getUserProfile(String username){

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Post> posts = postRepository.findByUserUsername(username);

        List<PostDTO> postDTO = posts.stream()
                .map(post -> PostDTO.builder()
                        .postId(post.getPostId())
                        .description(post.getPostDetails().getDescription())
                        .imageUrl(post.getMedia().isEmpty() ?
                                null :
                                post.getMedia().get(0).getMediaUrl()
                        )
                        .build()
                ).toList();

        return UserProfileDTO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .postCount(posts.size())
                .posts(postDTO)
                .build();


    }

    @Transactional
    public void updateUserData(String username, @Valid UpdatePersonalDataDTO personalDataDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(personalDataDTO.getName() != null){
            user.setName(personalDataDTO.getName());
        }

        if(personalDataDTO.getUsername() != null){
            if(userRepository.existsByUsername(personalDataDTO.getUsername())){
                throw new RuntimeException("Username already exists");
            }
            user.setUsername(personalDataDTO.getUsername());
        }

        if(personalDataDTO.getEmail() != null){
            user.setEmail(personalDataDTO.getEmail());
        }

        if(personalDataDTO.getPassword() != null){
            user.setUserPassword(passwordEncoder.encode(personalDataDTO.getPassword()));
        }
    }
}
