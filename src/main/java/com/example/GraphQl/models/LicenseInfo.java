package com.example.GraphQl.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class LicenseInfo {
    private String license;
    private String attribution;
    private boolean isOpenData;
}
