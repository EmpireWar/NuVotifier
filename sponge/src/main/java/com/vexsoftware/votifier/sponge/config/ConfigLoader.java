package com.vexsoftware.votifier.sponge.config;

import com.vexsoftware.votifier.sponge.NuVotifier;
import io.leangen.geantyref.TypeToken;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;

public class ConfigLoader {

    private static SpongeConfig spongeConfig;

    public static void loadConfig(NuVotifier pl) {
        if (!pl.getConfigDir().exists()) {
            if (!pl.getConfigDir().mkdirs()) {
                throw new RuntimeException("Unable to create the plugin data folder " + pl.getConfigDir());
            }
        }
        try {
            File config = new File(pl.getConfigDir(), "config.yml");
            if (!config.exists() && !config.createNewFile()) {
                throw new IOException("Unable to create the config file at " + config);
            }
            YamlConfigurationLoader loader = YamlConfigurationLoader.builder().file(config).build();
            ConfigurationNode configNode = loader.load(ConfigurationOptions.defaults().shouldCopyDefaults(true));
            spongeConfig = configNode.get(TypeToken.get(SpongeConfig.class), new SpongeConfig());
            loader.save(configNode);
        } catch (Exception e) {
            pl.getLogger().error("Could not load config.", e);
        }
    }

    public static SpongeConfig getSpongeConfig() {
        return spongeConfig;
    }
}
