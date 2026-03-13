package com.NKP.Filter.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "post_details" )
public class PostDetails {

    @Id
    private long postId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Embedded
    private Criminal criminal;

}
