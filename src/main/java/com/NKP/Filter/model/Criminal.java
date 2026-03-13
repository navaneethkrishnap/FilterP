package com.NKP.Filter.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Criminal {

    private String criminalName; // optional
    private int criminalAge; // opt
    private String crimeArea; // opt
    private String crimeCity; // required

    @Enumerated(EnumType.STRING)
    private CrimeSceneType crimeScene;

}
