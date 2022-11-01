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
public class Entry {
    private String entryId;
    private String name;
    private List<ExternalRatings> externalRatings;
    private Publication publication;
    private LicenseInfo licenseInfo;
    private WheelchairAccessibility wheelchairAccessibility;
}
