package net.evilblock.stark.engine.command.data.parameter.impl

import net.evilblock.stark.Stark
import net.evilblock.stark.engine.command.data.parameter.ParameterType
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.util.ArrayList
import java.util.UUID
import org.bukkit.command.CommandSender

class UUIDParameterType : ParameterType<UUID?> {

    override fun transform(sender: CommandSender, source: String): UUID? {
        if (sender is Player && (source.equals("self", ignoreCase = true) || source == "")) {
            return sender.uniqueId
        }

        val uuid = Stark.instance.core.uuidCache.uuid(source)
        if (uuid == null) {
            sender.sendMessage("${ChatColor.RED}$source has never joined the server.")
            return null
        }

        return uuid
    }

    override fun tabComplete(sender: Player, flags: Set<String>, source: String): List<String> {
        val completions = ArrayList<String>()

        for (player in Bukkit.getOnlinePlayers()) {
            // TODO: add support for visibility engine
            if (!sender.canSee(player)) {
                continue
            }

            completions.add(player.name)
        }

        return completions
    }

}