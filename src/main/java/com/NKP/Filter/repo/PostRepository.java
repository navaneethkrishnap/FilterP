package com.NKP.Filter.repo;

import com.NKP.Filter.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    Optional<Post> findById(long postId);

    Page<Post> findByPostDetailsCriminalCrimeCityIgnoreCaseAndPostDetailsCriminalCriminalNameContainingIgnoreCase
            (String city, String name, Pageable pageable);

    Page<Post> findByPostDetailsCriminalCrimeCityIgnoreCase(String city, Pageable pageable);

    Page<Post> findByPostDetailsCriminalCriminalNameContainingIgnoreCase(String name, Pageable pageable);

    List<Post> findByUserUsername(String username);

    Optional<Post> findByPostIdAndUserUsername(Long postId, String username);
}
