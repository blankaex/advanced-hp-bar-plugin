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
import java.awt.Color;
import java.awt.FontMetrics;
/**
 * The local player is intentionally skipped so the main overlay owns
 * the player's bar.
 */
public class DebugOverlay extends Overlay
{
    private final Client client;
    private final AdvancedHpBarConfig config;

    @Inject
    public DebugOverlay(Client client, AdvancedHpBarConfig config)
    {
        this.client = client;
        this.config = config;
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(Overlay.PRIORITY_HIGHEST);
        setLayer(OverlayLayer.UNDER_WIDGETS);
    }

    @Override
    public Dimension render(Graphics2D g)
    {
        if (!config.debugModeEnabled())
        {
            return null;
        }
        final Player localPlayer = client.getLocalPlayer();
        final WorldView topLevelWv = client.getTopLevelWorldView();
        final WorldView playerWv = localPlayer.getWorldView();

        drawNpcIds(g, topLevelWv, localPlayer);

        if (playerWv != topLevelWv)
        {
            drawNpcIds(g, playerWv, localPlayer);
        }

        return null;
    }

    private void drawNpcIds(Graphics2D g, WorldView wv, Player localPlayer)
    {
        for (NPC npc: wv.npcs()) {
            if (npc == null || isLocalPlayer(npc, localPlayer))
            {
                continue;
            }
            drawNpcId(g, npc);
        }
    }

    private void drawNpcId(Graphics2D g, NPC npc)
    {
        final LocalPoint lp = npc.getLocalLocation();
        if (lp == null)
        {
            return;
        }
        final Point canvasPoint = Perspective.localToCanvas(
                client,
                lp,
                client.getTopLevelWorldView().getPlane(),
                npc.getLogicalHeight()
        );
        if (canvasPoint == null)
        {
            return;
        }

        final String text = "NPC ID: " + npc.getId();
        final FontMetrics fm = g.getFontMetrics();
        final int textWidth = fm.stringWidth(text);
        final int x = canvasPoint.getX() - textWidth / 2;
        final int y = canvasPoint.getY();

        // Shadow for readability
        g.setColor(Color.BLACK);
        g.drawString(text, x + 1, y + 1);

        // Foreground
        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
    }

    private boolean isLocalPlayer(Actor actor, Player localPlayer)
    {
        return localPlayer != null && actor == localPlayer;
    }
}