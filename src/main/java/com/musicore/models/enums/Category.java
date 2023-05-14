package com.musicore.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    BRASS("Духовые"),
    STRINGS("Струнные"),
    KEYBOARDS("Клавишные"),
    PERCUSSION("Ударные"),
    LINGUAL("Язычковые");

    private final String name;
}
