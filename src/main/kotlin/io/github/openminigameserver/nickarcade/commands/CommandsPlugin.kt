package io.github.openminigameserver.nickarcade.commands

import io.github.openminigameserver.nickarcade.commands.impl.MiscCommands
import io.github.openminigameserver.nickarcade.commands.impl.RankCommands
import io.github.openminigameserver.nickarcade.core.commandAnnotationParser
import io.github.openminigameserver.nickarcade.core.commandHelper
import org.bukkit.plugin.java.JavaPlugin

class CommandsPlugin : JavaPlugin() {
    override fun onEnable() {
        RankCommands.registerOverrideRanksCommands(commandHelper)
        commandAnnotationParser.parse(RankCommands)
        commandAnnotationParser.parse(MiscCommands)
    }
}