package com.NKP.Filter.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "criminal_media")
@Getter
@Setter
public class CriminalMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mediaId;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    private String mediaUrl;

    // for cloud storage deletion purposes
    private String cloudImageId;
}
