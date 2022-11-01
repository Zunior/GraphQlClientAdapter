package com.example.GraphQl.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class EntryFilter {
    String id;
    String name;
    List<String> qualityLevels;
    List<String> postcodes;
}
