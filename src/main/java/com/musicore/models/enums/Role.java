package com.musicore.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum Role implements GrantedAuthority {
    USER("Покупатель"),
    ADMIN("Директор"),
    PUB("Менеджер");

    private final String name;

    @Override
    public String getAuthority() {
        return name();
    }
}
