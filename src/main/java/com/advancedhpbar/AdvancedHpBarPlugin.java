package com.advancedhpbar;

import com.advancedhpbar.overlay.AdvancedHpBarOverlay;
import com.advancedhpbar.overlay.DebugOverlay;
import com.advancedhpbar.overlay.NpcHpBarOverlay;
import com.advancedhpbar.overlay.OtherPlayerHpBarOverlay;
import com.advancedhpbar.sprite.BlankHpBarOverride;
import com.google.inject.Provides;
import java.io.InputStream;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.plugins.itemstats.ItemStatPlugin;

@Slf4j
@PluginDescriptor(
        name = "Advanced Hp Bar"
)
@PluginDependency(ItemStatPlugin.class)
public class AdvancedHpBarPlugin extends Plugin
{
    private static final int OVERHEAD_TEXT_HEIGHT = 12;

    @Inject
    private Client client;

    @Inject
    private AdvancedHpBarConfig config;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private AdvancedHpBarOverlay overlay;

    @Inject
    private SpriteManager spriteManager;

    @Inject
    private ClientThread clientThread;

    @Inject
    private NpcHpBarOverlay npcHpBarOverlay;

    @Inject
    private OtherPlayerHpBarOverlay otherPlayerHpBarOverlay;

    @Inject
    private DebugOverlay debugOverlay;

    private long lastTickTime;

    @Override
    protected void startUp()
    {
        lastTickTime = System.currentTimeMillis();
        overlayManager.add(overlay);
        clientThread.invoke(this::applyHealthBarOverrides);
        overlayManager.add(npcHpBarOverlay);
        overlayManager.add(otherPlayerHpBarOverlay);
        overlayManager.add(debugOverlay);
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
        overlayManager.remove(npcHpBarOverlay);
        overlayManager.remove(otherPlayerHpBarOverlay);
        overlayManager.remove(debugOverlay);
        clientThread.invoke(() ->
        {
            spriteManager.removeSpriteOverrides(BlankHpBarOverride.values());
            client.resetHealthBarCaches();
        });
        clientThread.invokeLater(client::resetHealthBarCaches);
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged gameStateChanged)
    {
        if (gameStateChanged.getGameState() == GameState.STARTING)
        {
            clientThread.invoke(this::applyHealthBarOverrides);
        }
    }

    private boolean applyHealthBarOverrides()
    {
        if (client.getGameState().getState() < GameState.LOGIN_SCREEN.getState())
        {
            return false;
        }

        String fileName = BlankHpBarOverride.values()[0].getFileName();
        try (InputStream in = BlankHpBarOverride.class.getResourceAsStream(fileName))
        {
            if (in != null)
            {
                log.info("[AdvancedHpBar] blank.png FOUND ('{}') relative to {}",
                        fileName, BlankHpBarOverride.class.getPackage().getName());
            }
            else
            {
                log.warn("[AdvancedHpBar] blank.png NOT FOUND - getResourceAsStream('{}') returned null. "
                                + "Expected at src/main/resources/{}/{}",
                        fileName,
                        BlankHpBarOverride.class.getPackage().getName().replace('.', '/'),
                        fileName);
            }
        }
        catch (Exception ex)
        {
            log.warn("[AdvancedHpBar] error while checking blank.png resource", ex);
        }

        spriteManager.addSpriteOverrides(BlankHpBarOverride.values());
        client.resetHealthBarCaches();
        log.info("[AdvancedHpBar] applied {} sprite overrides and reset health bar caches",
                BlankHpBarOverride.values().length);
        return true;
    }

    @Subscribe
    public void onGameTick(GameTick tick)
    {
        lastTickTime = System.currentTimeMillis();
    }

    public long millisSinceTick()
    {
        return System.currentTimeMillis() - lastTickTime;
    }

    /**
     * Gets the number of pixels the health bar should be raised for the
     * given actor's currently visible overhead text.
     */
    public static int getOverheadTextHealthBarShift(Actor actor)
    {
        final String overheadText = actor.getOverheadText();
        if (overheadText == null || overheadText.isEmpty() || actor.getOverheadCycle() <= 0)
        {
            return 0;
        }

        return OVERHEAD_TEXT_HEIGHT;
    }

    @Provides
    AdvancedHpBarConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(AdvancedHpBarConfig.class);
    }
}