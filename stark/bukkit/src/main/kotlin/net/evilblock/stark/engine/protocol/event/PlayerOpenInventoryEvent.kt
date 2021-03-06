package net.evilblock.stark.engine.protocol.event

import org.bukkit.event.HandlerList
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerEvent

class PlayerOpenInventoryEvent(player: Player) : PlayerEvent(player) {

    private val instanceHandlers: HandlerList = ServerLaggedOutEvent.handlerList

    override fun getHandlers(): HandlerList {
        return instanceHandlers
    }

    companion object {
        var handlerList: HandlerList = HandlerList()
    }

}