package com.advancedhpbar.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PrayerBarVisibility
{
    ACTIVE_OR_COMBAT("Active or Combat"),
    ACTIVE_ONLY("Only Active"),
    COMBAT_ONLY("Only Combat"),
    ALWAYS("Always"),
    NEVER("Never");

    private final String name;

    @Override
    public String toString()
    {
        return name;
    }
}