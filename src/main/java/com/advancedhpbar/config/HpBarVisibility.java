package com.advancedhpbar.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HpBarVisibility
{
    IN_COMBAT("In Combat"),
    ALWAYS("Always"),
    NEVER("Never");

    private final String name;

    @Override
    public String toString()
    {
        return name;
    }
}