package com.vexsoftware.votifier.sponge.cmd;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.net.VotifierSession;
import com.vexsoftware.votifier.sponge.NuVotifier;
import com.vexsoftware.votifier.util.ArgsToVote;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.spongepowered.api.command.CommandExecutor;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.command.parameter.Parameter;

public class TestVoteCmd implements CommandExecutor {

    private static final Parameter.Value<String> PARAMETER = Parameter.remainingJoinedStrings().key("args").build();

    private final NuVotifier plugin;

    public TestVoteCmd(NuVotifier plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandContext args) {
        Vote v;
        try {
            final String argumentsString = args.requireOne(PARAMETER);
            v = ArgsToVote.parse(argumentsString.split(" "));
        } catch (IllegalArgumentException e) {
            return CommandResult.error(Component.text("Error while parsing arguments to create test vote: " + e.getMessage(), NamedTextColor.DARK_RED)
                    .appendNewline()
                    .append(Component.text("Usage hint: /testvote [username] [serviceName=?] [username=?] [address=?] [localTimestamp=?] [timestamp=?]", NamedTextColor.GRAY)));
        }

        plugin.onVoteReceived(v, VotifierSession.ProtocolVersion.TEST, "localhost.test");
        return CommandResult.success();
    }
}
