package com.example.GraphQl.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Publication {
    private String iconUrl;
    private String iconText;
    private String linkUrl;
    private String linkText;
}
