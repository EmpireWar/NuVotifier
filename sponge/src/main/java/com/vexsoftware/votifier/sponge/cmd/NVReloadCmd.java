package com.vexsoftware.votifier.sponge.cmd;

import com.vexsoftware.votifier.sponge.NuVotifier;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;

public class NVReloadCmd implements CommandExecutor {

    private final NuVotifier plugin;

    public NVReloadCmd(NuVotifier plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandContext args) {
        args.sendMessage(Component.text("Reloading NuVotifier...", NamedTextColor.GRAY));
        if (plugin.reload())
            return CommandResult.success();
        else
            return CommandResult.error(Component.text("There was a problem reloading the plugin.", NamedTextColor.RED));
    }
}
