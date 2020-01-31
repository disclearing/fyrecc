package net.evilblock.stark.engine.command.defaults

import net.evilblock.stark.engine.command.Command
import net.evilblock.stark.engine.command.data.parameter.Param
import net.evilblock.stark.util.EntityUtils
import org.bukkit.ChatColor
import org.bukkit.block.CreatureSpawner
import org.bukkit.entity.Player

object SpawnerCommand {
    @Command(["spawner"], permission = "essentials.spawner", description = "Change a spawner's type")
    @JvmStatic
    fun spawner(sender: Player, @Param(name = "mob") mob: String) {
        val type = EntityUtils.parse(mob)
        if (type == null || !type.isAlive) {
            sender.sendMessage("${ChatColor.RED}No mob with the name " + mob + " found.")
            return
        }

        val block = sender.getTargetBlock(null, 5)
        if (block == null || block.state !is CreatureSpawner) {
            sender.sendMessage("${ChatColor.RED}You aren't looking at a mob spawner.")
            return
        }

        val spawner = block.state as CreatureSpawner
        spawner.spawnedType = type
        spawner.update()

        sender.sendMessage("${ChatColor.GOLD}This spawner now spawns ${ChatColor.WHITE}" + EntityUtils.getName(type) + "${ChatColor.GOLD}.")
    }
}