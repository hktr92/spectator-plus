package eu.kronick.spectatorplus;

import eu.kronick.spectatorplus.listener.PlayerGameModeListener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * Class SpectatorPlus
 *
 * @since 1.0.0
 */
public class SpectatorPlus extends JavaPlugin {
    private static final String PLUGIN_NAME = "SpectatorPlus";
    private static HashMap<String, String> pluginVersionInfo;

    public String getPluginName() {
        return PLUGIN_NAME;
    }

    public SpectatorPlus() {
    }

    @Override
    public void onEnable() {
        this.init();
        this.updateVersionInfo(this.getDescription());

        this.displayVersionInfo();

        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerGameModeListener(this), this);
    }

    private void displayVersionInfo() {
        this.getLogger().info(String.format("%s plugin enabled!", PLUGIN_NAME));
        this.getLogger().info(String.format("%s Version: %s", PLUGIN_NAME, pluginVersionInfo.get("version")));
        this.getLogger().info(String.format("%s Stability: %s", PLUGIN_NAME, pluginVersionInfo.get("stability")));
    }

    @Override
    public void onDisable() {
        this.getLogger().info(PLUGIN_NAME.concat(" plugin disabled!"));
    }

    private void init() {
        boolean created = this.getDataFolder().mkdir();
        if (!created) {
            this.getLogger().severe("Unable to create data directory!");
        }

        pluginVersionInfo = new HashMap<String, String>();
    }

    private void updateVersionInfo(PluginDescriptionFile pluginDescription) {
        String version = pluginDescription.getVersion();
        String pluginStability = "-???";

        if (version.contains("-")) {
            int stabilityIndex = version.lastIndexOf('-');

            pluginStability = version.substring(stabilityIndex + 1);
            version = version.substring(0, stabilityIndex);
        }

        pluginVersionInfo.put("version", version);
        pluginVersionInfo.put("stability", pluginStability);
    }
}
