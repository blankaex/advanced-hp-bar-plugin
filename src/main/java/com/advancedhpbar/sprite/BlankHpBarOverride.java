package com.advancedhpbar.sprite;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.api.gameval.SpriteID;
import net.runelite.client.game.SpriteOverride;

/**
 * Replaces every vanilla healthbar (and related) sprite with a fully
 * transparent image, hiding the game's built-in bars so the overlay
 * can be drawn cleanly on top.
 */
@RequiredArgsConstructor
public enum BlankHpBarOverride implements SpriteOverride
{
    // Standard health (red)
    HEALTH_30_FRONT(SpriteID.StandardHealth30.FRONT),
    HEALTH_30_BACK(SpriteID.StandardHealth30.BACK),
    HEALTH_40_FRONT(SpriteID.StandardHealth40.FRONT),
    HEALTH_40_BACK(SpriteID.StandardHealth40.BACK),
    HEALTH_50_FRONT(SpriteID.StandardHealth50.FRONT),
    HEALTH_50_BACK(SpriteID.StandardHealth50.BACK),
    HEALTH_60_FRONT(SpriteID.StandardHealth60.FRONT),
    HEALTH_60_BACK(SpriteID.StandardHealth60.BACK),
    HEALTH_70_FRONT(SpriteID.StandardHealth70.FRONT),
    HEALTH_70_BACK(SpriteID.StandardHealth70.BACK),
    HEALTH_80_FRONT(SpriteID.StandardHealth80.FRONT),
    HEALTH_80_BACK(SpriteID.StandardHealth80.BACK),
    HEALTH_90_FRONT(SpriteID.StandardHealth90.FRONT),
    HEALTH_90_BACK(SpriteID.StandardHealth90.BACK),
    HEALTH_100_FRONT(SpriteID.StandardHealth100.FRONT),
    HEALTH_100_BACK(SpriteID.StandardHealth100.BACK),
    HEALTH_120_FRONT(SpriteID.StandardHealth120.FRONT),
    HEALTH_120_BACK(SpriteID.StandardHealth120.BACK),
    HEALTH_140_FRONT(SpriteID.StandardHealth140.FRONT),
    HEALTH_140_BACK(SpriteID.StandardHealth140.BACK),
    HEALTH_160_FRONT(SpriteID.StandardHealth160.FRONT),
    HEALTH_160_BACK(SpriteID.StandardHealth160.BACK),
    ;

    @Getter
    private final int spriteId;

    @Override
    public String getFileName()
    {
        return "osrs/hp/blank.png";
    }
}