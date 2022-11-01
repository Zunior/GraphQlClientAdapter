package com.example.GraphQl.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class WheelchairAccessibility {
    private String ratingProfileId;
    private String grade;
}
