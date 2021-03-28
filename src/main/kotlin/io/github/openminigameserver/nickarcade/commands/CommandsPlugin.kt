package io.github.openminigameserver.nickarcade.commands

import io.github.nickacpt.nickarcade.commands.MiscCommands
import io.github.nickacpt.nickarcade.commands.RankCommands
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