package com.advancedhpbar.overlay;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;

import com.advancedhpbar.AdvancedHpBarConfig;
import com.advancedhpbar.sprite.NpcHpBarOverride;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.api.Perspective;
import net.runelite.api.WorldView;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

/**
 * The local player is intentionally skipped so the main overlay owns
 * the player's bar.
 */
public class NpcHpBarOverlay extends Overlay
{
    private final Client client;
    private final AdvancedHpBarConfig config;

    @Inject
    public NpcHpBarOverlay(Client client, AdvancedHpBarConfig config)
    {
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(Overlay.PRIORITY_HIGH);
        setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D g)
    {
        if (!config.npcBarEnabled())
        {
            return null;
        }
        final Player localPlayer = client.getLocalPlayer();
        final WorldView topLevelWv = client.getTopLevelWorldView();
        final WorldView playerWv = localPlayer.getWorldView();

        drawHealthBars(g, topLevelWv, localPlayer);

        if (playerWv != topLevelWv)
        {
            drawHealthBars(g, playerWv, localPlayer);
        }

        return null;
    }

    private void drawHealthBars(Graphics2D g, WorldView wv, Player localPlayer)
    {
        for (NPC npc : wv.npcs())
        {
            if (npc == null || isLocalPlayer(npc, localPlayer))
            {
                continue;
            }
            drawHealthBar(g, npc);
        }
    }

    private boolean isLocalPlayer(Actor actor, Player localPlayer)
    {
        return localPlayer != null && actor == localPlayer;
    }

    private void drawHealthBar(Graphics2D g, NPC npc)
    {
        final NpcHpBarOverride override = NpcHpBarOverride.forNpcId(npc.getId());

        // An override exists and has explicitly hidden this NPC's bar.
        if (override != null && !override.isVisible())
        {
            return;
        }

        final int healthScale = npc.getHealthScale();
        final int healthRatio = npc.getHealthRatio();

        if (healthScale <= 0 || healthRatio < 0)
        {
            return;
        }

        final LocalPoint lp = npc.getLocalLocation();
        if (lp == null)
        {
            return;
        }

        final int barWidth = override != null ? override.getBarWidth() : config.npcBarWidth();
        final int heightOffset = override != null ? override.getBarHeightOffset() : 0;

        final Point canvasPoint = Perspective.localToCanvas(
                client,
                lp,
                client.getTopLevelWorldView().getPlane(),
                npc.getLogicalHeight() + config.npcBarZOffset()
        );

        if (canvasPoint == null)
        {
            return;
        }

        final int barHeight = config.npcBarHeight();

        final int x = canvasPoint.getX() + config.npcBarXOffset() - (barWidth / 2);
        final int y = canvasPoint.getY() + config.npcBarYOffset() - 10 - heightOffset;

        final double fraction = Math.max(0.0, Math.min(1.0,
                (double) healthRatio / (double) healthScale));
        final int fillWidth = (int) Math.round(barWidth * fraction);

        g.setColor(config.npcHpDamagedColor());
        g.fillRect(x, y, barWidth, barHeight);

        if (fillWidth > 0)
        {
            g.setColor(config.npcHpColor());
            g.fillRect(x, y, fillWidth, barHeight);
        }
    }
}