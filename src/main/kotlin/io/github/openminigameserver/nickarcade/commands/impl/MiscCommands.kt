package io.github.nickacpt.nickarcade.commands

import cloud.commandframework.annotations.CommandMethod
import io.github.openminigameserver.hypixelapi.models.HypixelPackageRank
import io.github.openminigameserver.nickarcade.core.data.sender.ArcadeSender
import io.github.openminigameserver.nickarcade.core.hypixelService
import io.github.openminigameserver.nickarcade.plugin.extensions.command
import io.github.openminigameserver.nickarcade.plugin.helper.commands.RequiredRank
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor.GOLD
import net.kyori.adventure.text.format.NamedTextColor.GREEN
import org.bukkit.Bukkit

object MiscCommands {
    @CommandMethod("apistats|apistatus")
    fun apiStatusCommand(sender: ArcadeSender) = command(sender) {
        val info = hypixelService.getKeyInformation()
        sender.audience.sendMessage(text {
            it.append(text("Made ", GOLD))
            it.append(text(info.record!!.queriesInPastMin + 1, GREEN))
            it.append(text(" out of ", GOLD))
            it.append(text(info.record!!.limit, GREEN))
            it.append(text(" queries to Hypixel in the last minute.", GOLD))
        })
    }

    @CommandMethod("stats|status")
    @RequiredRank(HypixelPackageRank.ADMIN)
    fun statusCommand(sender: ArcadeSender) = command(sender) {
        val instances = Bukkit.getWorlds()
        sender.audience.sendMessage(
            text("There are ", GOLD)
                .append(text(instances.size - 1, GREEN)) //Remove 1 for lobby instance
                .append(text(" game instances currently running.", GOLD))
        )
    }/*


    @CommandMethod("lobby|l")
    fun lobbyCommand(sender: ArcadePlayer) = command(sender) {

        val currentGame = sender.getCurrentGame()
        if (currentGame != null) {
            currentGame.let { MiniGameManager.removePlayer(it, sender) }
        } else {
            sender.audience.sendMessage(text("You are already on a lobby!", RED))
        }
    }*/
}