package com.advancedhpbar;

import com.advancedhpbar.config.HpBarVisibility;
import com.advancedhpbar.config.PrayerBarVisibility;
import net.runelite.client.config.*;
import java.awt.*;

@ConfigGroup("advancedhpbar")
public interface AdvancedHpBarConfig extends Config
{
    // -------------------------------------------------------------------------
    // Sections
    // -------------------------------------------------------------------------

    @ConfigSection(
            name = "General",
            description = "General bar settings",
            position = 0
    )
    String generalSection = "general";

    @ConfigSection(
            name = "HP Bar",
            description = "HP bar appearance settings",
            position = 1
    )
    String hpSection = "hp";

    @ConfigSection(
            name = "Prayer Bar",
            description = "Prayer bar appearance settings",
            position = 2
    )
    String prayerSection = "prayer";

    @ConfigSection(
            name = "NPC HP Bar",
            description = "Redrawn NPC health bar settings",
            position = 3
    )
    String npcSection = "npc";

    @ConfigSection(
            name = "Other Player HP Bar",
            description = "Health bar settings for other players",
            position = 4
    )
    String otherPlayerSection = "otherPlayer";

    @ConfigSection(
            name= "Debug Mode",
            description = "Tools to help development",
            position = 5
    )
    String debugModeSection = "debugMode";

    // -------------------------------------------------------------------------
    // General
    // -------------------------------------------------------------------------
    @ConfigItem(
            keyName = "barWidth",
            name = "Bar Width",
            description = "Total fixed width of the bar in pixels",
            section = generalSection,
            position = 1
    )
    @Range(min = 10, max = 500)
    default int barWidth()
    {
        return 60;
    }

    @ConfigItem(
            keyName = "zOffset",
            name = "Z Offset",
            description = "Z offset of the bar for vertical world-space alignment",
            section = generalSection,
            position = 2
    )
    @Range(min = -5000, max = 5000)
    default int zOffset()
    {
        return 0;
    }

    @ConfigItem(
            keyName = "barHeightOffset",
            name = "Vertical Offset",
            description = "How high above the character to render the bar",
            section = generalSection,
            position = 3
    )
    @Range(min = -500, max = 500)
    default int barHeightOffset()
    {
        return 0;
    }

    @ConfigItem(
            keyName = "barXOffset",
            name = "Horizontal Offset",
            description = "Horizontal offset of the bar relative to the player's centre",
            section = generalSection,
            position = 4
    )
    @Range(min = -500, max = 500)
    default int barXOffset()
    {
        return 0;
    }

    // -------------------------------------------------------------------------
    // HP Bar
    // -------------------------------------------------------------------------
    @ConfigItem(
            keyName = "hpBarVisibility",
            name = "Visibility",
            description = "When to display the HP bar",
            section = hpSection,
            position = 0
    )
    default HpBarVisibility hpBarVisibility()
    {
        return HpBarVisibility.IN_COMBAT;
    }

    @ConfigItem(
            keyName = "displaySegments",
            name = "Display Segments",
            description = "Divide the HP bar into discrete box segments",
            section = hpSection,
            position = 1
    )
    default boolean displaySegments()
    {
        return true;
    }

    @ConfigItem(
            keyName = "hpPerBox",
            name = "HP Per Box",
            description = "How many HP points each box segment represents",
            section = hpSection,
            position = 2
    )
    default int hpPerBox()
    {
        return 10;
    }

    @Alpha
    @ConfigItem(
            keyName = "hpColor",
            name = "HP Color",
            description = "Color of the HP fill",
            section = hpSection,
            position = 3
    )
    default Color hpColor()
    {
        return Color.GREEN;
    }

    @Alpha
    @ConfigItem(
            keyName = "hpDamagedColor",
            name = "HP Damaged Color",
            description = "Color shown for the damaged (missing) portion of HP",
            section = hpSection,
            position = 4
    )
    default Color hpDamagedColor()
    {
        return Color.RED;
    }

    @Alpha
    @ConfigItem(
            keyName = "lowHpThreshold",
            name = "Low HP Threshold",
            description = "HP value at which the bar switches to the low HP color. Set to 0 to disable",
            section = hpSection,
            position = 5
    )
    default int lowHpThreshold()
    {
        return 5;
    }

    @Alpha
    @ConfigItem(
            keyName = "lowHpColor",
            name = "Low HP Color",
            description = "Color of the HP bar when HP drops below the low HP threshold",
            section = hpSection,
            position = 6
    )
    default Color lowHpColor()
    {
        return Color.YELLOW;
    }

    @Alpha
    @ConfigItem(
            keyName = "overhealColor",
            name = "Overheal Color",
            description = "Color of the HP bar portion above your base max HP",
            section = hpSection,
            position = 7
    )
    default Color overhealColor()
    {
        return Color.WHITE;
    }

    @Alpha
    @ConfigItem(
            keyName = "foodHealColor",
            name = "Food heal preview color",
            description = "Color shown for potential HP restored by hovered food",
            section = hpSection,
            position = 8
    )
    default Color foodHealColor()
    {
        return new Color(228, 148, 148, 255);
    }

    @Alpha
    @ConfigItem(
            keyName = "hpBackgroundColor",
            name = "HP Background Color",
            description = "Background color behind the HP boxes",
            section = hpSection,
            position = 9
    )
    default Color hpBackgroundColor()
    {
        return Color.BLACK;
    }

    @Range(min = 1, max = 30)
    @ConfigItem(
            keyName = "hpBarHeight",
            name = "HP bar height",
            description = "Height of the HP bar in pixels",
            section = hpSection,
            position = 10
    )
    default int hpBarHeight()
    {
        return 5;
    }
    // -------------------------------------------------------------------------
    // Prayer Bar
    // -------------------------------------------------------------------------

    @ConfigItem(
            keyName = "prayerBarVisibility",
            name = "Visibility",
            description = "When to display the prayer bar below the HP bar",
            section = prayerSection,
            position = 0
    )
    default PrayerBarVisibility prayerBarVisibility()
    {
        return PrayerBarVisibility.ACTIVE_OR_COMBAT;
    }

    @Alpha
    @ConfigItem(
            keyName = "prayerColor",
            name = "Prayer Color",
            description = "Color of the prayer points fill",
            section = prayerSection,
            position = 2
    )
    default Color prayerColor()
    {
        return Color.CYAN;
    }

    @Alpha
    @ConfigItem(
            keyName = "prayerRestoreColor",
            name = "Prayer restore preview color",
            description = "Color shown for potential prayer restored by hovered item",
            section = prayerSection,
            position = 3
    )
    default Color prayerRestoreColor()
    {
        return new Color(187, 187, 255, 255); // soft blue, semi-transparent
    }

    @Alpha
    @ConfigItem(
            keyName = "lowPrayerThreshold",
            name = "Low Prayer Threshold",
            description = "Prayer value at which the bar switches to the low Prayer color. Set to 0 to disable",
            section = prayerSection,
            position = 4
    )
    default int lowPrayerThreshold()
    {
        return 0;
    }

    @Alpha
    @ConfigItem(
            keyName = "lowPrayerColor",
            name = "Low Prayer Color",
            description = "Color of the Prayer bar when Prayer drops below the low Prayer threshold",
            section = prayerSection,
            position = 5
    )
    default Color lowPrayerColor()
    {
        return new Color(244, 224, 102, 255);
    }

    @Alpha
    @ConfigItem(
            keyName = "prayerBackgroundColor",
            name = "Prayer Background Color",
            description = "Background color of the prayer bar",
            section = prayerSection,
            position = 6
    )
    default Color prayerBackgroundColor()
    {
        return Color.BLACK;
    }

    @Range(min = 1, max = 10)
    @ConfigItem(
            keyName = "prayerBarHeight",
            name = "Prayer Bar Height",
            description = "Height of the prayer bar",
            section = prayerSection,
            position = 7
    )
    default int prayerBarHeight()
    {
        return 3;
    }

    @ConfigItem(
            keyName = "prayerFlickOn",
            name = "Prayer Flick Helper",
            description = "Display a Prayer Flicker Helper",
            section = prayerSection,
            position = 8
    )
    default boolean prayerFlickOn()
    {
        return false;
    }

    @Alpha
    @ConfigItem(
            keyName = "prayerFlickColor",
            name = "Prayer Flick Color",
            description = "Color for Player Flick Helper",
            section = prayerSection,
            position = 9
    )
    default Color prayerFlickColor()
    {
        return new Color(255, 125, 0, 255);
    }

    // -------------------------------------------------------------------------
    // NPC HP Bar
    // -------------------------------------------------------------------------
    @ConfigItem(
            keyName = "npcBarEnabled",
            name = "Show NPCs' HP Bar",
            description = "Display health bars over NPCs",
            section = npcSection,
            position = 0
    )
    default boolean npcBarEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "npcBarWidth",
            name = "NPC Bar Width",
            description = "Width of the NPC health bar in pixels",
            section = npcSection,
            position = 1
    )
    @Range(min = 10, max = 300)
    default int npcBarWidth()
    {
        return 30;
    }

    @ConfigItem(
            keyName = "npcBarHeight",
            name = "NPC Bar Height",
            description = "Height of the NPC health bar in pixels",
            section = npcSection,
            position = 2
    )
    @Range(min = 1, max = 30)
    default int npcBarHeight()
    {
        return 5;
    }

    @ConfigItem(
            keyName = "npcBarZOffset",
            name = "NPC Z Offset",
            description = "Z offset added to the NPC's logical height for vertical alignment",
            section = npcSection,
            position = 3
    )
    @Range(min = -5000, max = 5000)
    default int npcBarZOffset()
    {
        return 0;
    }

    @ConfigItem(
            keyName = "npcBarXOffset",
            name = "NPC Horizontal Offset",
            description = "Horizontal pixel offset of the NPC bar after projection",
            section = npcSection,
            position = 4
    )
    @Range(min = -500, max = 500)
    default int npcBarXOffset()
    {
        return 0;
    }

    @ConfigItem(
            keyName = "npcBarYOffset",
            name = "NPC Vertical Offset",
            description = "Vertical pixel offset of the NPC bar after projection",
            section = npcSection,
            position = 5
    )
    @Range(min = -500, max = 500)
    default int npcBarYOffset()
    {
        return 0;
    }

    @Alpha
    @ConfigItem(
            keyName = "npcHpColor",
            name = "NPC HP Color",
            description = "Color of the NPC health fill",
            section = npcSection,
            position = 6
    )
    default Color npcHpColor()
    {
        return Color.GREEN;
    }

    @Alpha
    @ConfigItem(
            keyName = "npcHpDamagedColor",
            name = "NPC HP Damaged Color",
            description = "Color shown for the damaged (missing) portion of NPC HP",
            section = npcSection,
            position = 7
    )
    default Color npcHpDamagedColor()
    {
        return Color.RED;
    }

    // -------------------------------------------------------------------------
    // Other Player HP Bar
    // -------------------------------------------------------------------------
    @ConfigItem(
            keyName = "otherPlayerBarEnabled",
            name = "Show Other Players' HP Bar",
            description = "Display health bars over other players",
            section = otherPlayerSection,
            position = 0
    )
    default boolean otherPlayerBarEnabled()
    {
        return true;
    }

    @ConfigItem(
            keyName = "otherPlayerBarWidth",
            name = "Bar Width",
            description = "Width of the other player health bar in pixels",
            section = otherPlayerSection,
            position = 1
    )
    @Range(min = 10, max = 300)
    default int otherPlayerBarWidth()
    {
        return 30;
    }

    @ConfigItem(
            keyName = "otherPlayerBarHeight",
            name = "Bar Height",
            description = "Height of the other player health bar in pixels",
            section = otherPlayerSection,
            position = 2
    )
    @Range(min = 1, max = 30)
    default int otherPlayerBarHeight()
    {
        return 5;
    }

    @ConfigItem(
            keyName = "otherPlayerBarZOffset",
            name = "Z Offset",
            description = "Z offset added to the player's logical height for vertical alignment",
            section = otherPlayerSection,
            position = 3
    )
    @Range(min = -5000, max = 5000)
    default int otherPlayerBarZOffset()
    {
        return 0;
    }

    @ConfigItem(
            keyName = "otherPlayerBarXOffset",
            name = "Horizontal Offset",
            description = "Horizontal pixel offset of the bar after projection",
            section = otherPlayerSection,
            position = 4
    )
    @Range(min = -500, max = 500)
    default int otherPlayerBarXOffset()
    {
        return 0;
    }

    @ConfigItem(
            keyName = "otherPlayerBarYOffset",
            name = "Vertical Offset",
            description = "Vertical pixel offset of the bar after projection",
            section = otherPlayerSection,
            position = 5
    )
    @Range(min = -500, max = 500)
    default int otherPlayerBarYOffset()
    {
        return 0;
    }

    @Alpha
    @ConfigItem(
            keyName = "otherPlayerHpColor",
            name = "HP Color",
            description = "Color of the other player health fill",
            section = otherPlayerSection,
            position = 6
    )
    default Color otherPlayerHpColor()
    {
        return Color.GREEN;
    }

    @Alpha
    @ConfigItem(
            keyName = "otherPlayerHpDamagedColor",
            name = "HP Damaged Color",
            description = "Color shown for the damaged (missing) portion of other player HP",
            section = otherPlayerSection,
            position = 7
    )
    default Color otherPlayerHpDamagedColor()
    {
        return Color.RED;
    }

    // -------------------------------------------------------------------------
    // Debug Mode
    // -------------------------------------------------------------------------
    @ConfigItem(
            keyName = "debugModeEnabled",
            name = "Enable Debug Mode",
            description = "Display NPC IDs",
            section = debugModeSection,
            position = 0
    )
    default boolean debugModeEnabled()
    {
        return false;
    }
}