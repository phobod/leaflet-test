package com.phobod.leaflet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UiData {
    private int x;
    private int y;
    private String name;
    private String pinColor;
}
