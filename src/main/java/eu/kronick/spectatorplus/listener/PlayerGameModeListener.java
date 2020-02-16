package eu.kronick.spectatorplus.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerGameModeListener implements Listener {
    private static final int MAX_EFFECT_DURATION = 1000000;
    private static final int MAX_EFFECT_AMPLIFIER = 100;

    private Plugin plugin;

    public PlayerGameModeListener(Plugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        final Player player = event.getPlayer();
        final GameMode newGameMode = event.getNewGameMode();

        // 0. If player's current game mode equals with new game mode, don't do anything.
        // Minecraft should be capable of handling this case internally.

        // 1. Apply effect for players that changes to SPECTATOR
        if (newGameMode.equals(GameMode.SPECTATOR)) {
            // better than forcing the potion effect, might be removed in the future.
            this.removeNightVisionFor(player);

            player.addPotionEffect(this.getNightVision());
        }

        // 2. Remove effect for players that changes from SPECTATOR
        if (player.getGameMode().equals(GameMode.SPECTATOR)) {
            this.removeNightVisionFor(player);
        }
    }

    /**
     * Creates a PotionEffect for night vision.
     *
     * @return PotionEffect
     */
    private PotionEffect getNightVision() {
        return new PotionEffect(
                PotionEffectType.NIGHT_VISION,
                MAX_EFFECT_DURATION,
                MAX_EFFECT_AMPLIFIER,
                false
        );
    }

    /**
     * Checks if a player has night vision applied and if such, it is removed.
     *
     * @param player The player instance.
     */
    private void removeNightVisionFor(Player player) {
        if (player.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }
}