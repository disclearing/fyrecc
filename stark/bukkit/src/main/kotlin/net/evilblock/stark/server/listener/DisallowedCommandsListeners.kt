package net.evilblock.stark.server.listener

import net.evilblock.stark.Stark
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class DisallowedCommandsListeners : Listener {
    @EventHandler
    fun onCommand(event: PlayerCommandPreprocessEvent) {
        val command = if (event.message.contains(" ")) event.message.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0] else event.message
        Stark.instance.serverHandler.disallowedCommands.stream().filter { blocked -> blocked.equals(this.strip(command), ignoreCase = true) }.forEach {
            event.isCancelled = true
            event.player.sendMessage("${ChatColor.RED}This action can only be performed by the console.")
        }
    }

    private fun strip(command: String): String {
        var command = command
        command = command.toLowerCase().replaceFirst("/".toRegex(), "")
        command = command.replace("minecraft:", "")
        command = command.replace("bukkit:", "")
        command = command.replace("worldedit:", "")
        return command
    }
}