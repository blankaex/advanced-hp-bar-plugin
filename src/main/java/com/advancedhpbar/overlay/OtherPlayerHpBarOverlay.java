package com.advancedhpbar.overlay;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;

import com.advancedhpbar.AdvancedHpBarConfig;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.api.Perspective;
import net.runelite.api.WorldView;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

/**
 * Draws HP bars over other players. The local player is intentionally
 * skipped so the main overlay owns the local player's bar.
 */
public class OtherPlayerHpBarOverlay extends Overlay
{
    private final Client client;
    private final AdvancedHpBarConfig config;

    @Inject
    public OtherPlayerHpBarOverlay(Client client, AdvancedHpBarConfig config)
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
        if (!config.otherPlayerBarEnabled())
        {
            return null;
        }

        final Player localPlayer = client.getLocalPlayer();
        if (localPlayer == null)
        {
            return null;
        }

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
        for (Player player : wv.players())
        {
            if (player == null || player == localPlayer)
            {
                continue;
            }
            drawHealthBar(g, player);
        }
    }

    private void drawHealthBar(Graphics2D g, Player player)
    {
        final int healthScale = player.getHealthScale();
        final int healthRatio = player.getHealthRatio();

        if (healthScale <= 0 || healthRatio < 0)
        {
            return;
        }

        final LocalPoint lp = player.getLocalLocation();
        if (lp == null)
        {
            return;
        }

        final int barWidth = config.otherPlayerBarWidth();
        final int barHeight = config.otherPlayerBarHeight();

        final Point canvasPoint = Perspective.localToCanvas(
                client,
                lp,
                client.getTopLevelWorldView().getPlane(),
                player.getLogicalHeight() + config.otherPlayerBarZOffset()
        );

        if (canvasPoint == null)
        {
            return;
        }

        final int x = canvasPoint.getX() + config.otherPlayerBarXOffset() - (barWidth / 2);
        final int y = canvasPoint.getY() + config.otherPlayerBarYOffset() - 10;

        final double fraction = Math.max(0.0, Math.min(1.0,
                (double) healthRatio / (double) healthScale));
        final int fillWidth = (int) Math.round(barWidth * fraction);

        g.setColor(config.otherPlayerHpDamagedColor());
        g.fillRect(x, y, barWidth, barHeight);

        if (fillWidth > 0)
        {
            g.setColor(config.otherPlayerHpColor());
            g.fillRect(x, y, fillWidth, barHeight);
        }
    }
}