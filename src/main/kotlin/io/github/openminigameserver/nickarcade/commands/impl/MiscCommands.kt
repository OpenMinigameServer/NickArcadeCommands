package io.github.openminigameserver.nickarcade.commands.impl

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandMethod
import io.github.openminigameserver.hypixelapi.models.HypixelPackageRank
import io.github.openminigameserver.nickarcade.core.data.sender.ArcadeSender
import io.github.openminigameserver.nickarcade.core.data.sender.misc.ArcadeConsole
import io.github.openminigameserver.nickarcade.core.hypixelService
import io.github.openminigameserver.nickarcade.core.manager.PlayerDataManager
import io.github.openminigameserver.nickarcade.plugin.extensions.command
import io.github.openminigameserver.nickarcade.plugin.helper.commands.RequiredRank
import io.github.openminigameserver.profile.ProfileApi
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor.*
import org.bukkit.Bukkit
import java.util.*

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
    }

    @CommandMethod("debugloadplayer <name>")
    @RequiredRank(HypixelPackageRank.ADMIN)
    fun debugLoadNamedPlayer(sender: ArcadeSender, @Argument("name") name: String) = command(sender) {
        val profile = ProfileApi.getProfileByName(name)
        val uuid = profile?.uuid
        if (uuid != null) {
            debugLoadPlayer(sender, uuid)
        } else {
            sender.audience.sendMessage(text("Unable to find a player named \"$name\".", RED))
        }
    }

    @CommandMethod("debugunloadplayer <name>")
    @RequiredRank(HypixelPackageRank.ADMIN)
    fun debugUnLoadNamedPlayer(sender: ArcadeSender, @Argument("name") name: String) = command(sender) {
        val profile = ProfileApi.getProfileByName(name)
        val uuid = profile?.uuid
        if (uuid != null) {
            debugUnLoadPlayer(sender, uuid)
        } else {
            sender.audience.sendMessage(text("Unable to find a player named \"$name\".", RED))
        }
    }

    @CommandMethod("debugload <player>")
    @RequiredRank(HypixelPackageRank.ADMIN)
    fun debugLoadPlayer(sender: ArcadeSender, @Argument("player") id: UUID)
    = command(sender) {
        val result = PlayerDataManager.getPlayerData(id, id.toString())
        sender.audience.sendMessage(text("Loaded player named \"${result.actualDisplayName}\"."))
    }

    @CommandMethod("debugunload <player>")
    @RequiredRank(HypixelPackageRank.ADMIN)
    fun debugUnLoadPlayer(sender: ArcadeSender, @Argument("player") id: UUID)
    = command(sender) {
        PlayerDataManager.saveAndRemovePlayerData(id)
        sender.audience.sendMessage(text("Unloaded player with ID \"${id}\"."))
    }

    @CommandMethod("dumpdepends")
    @RequiredRank(HypixelPackageRank.ADMIN)
    fun dumpDependencies(sender: ArcadeConsole)
    {
        Bukkit.getPluginManager().plugins.forEach { pl ->
            pl.description.depend.forEach {
                println("${pl.description.name.removePrefix("NickArcade")} --> ${it.removePrefix("NickArcade")}")
            }
        }
    }
}