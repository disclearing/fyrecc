package net.evilblock.stark.engine.menu.pagination

import net.evilblock.stark.engine.menu.Button
import net.evilblock.stark.engine.menu.Menu
import net.evilblock.stark.engine.menu.buttons.BackButton
import net.evilblock.stark.util.Callback
import java.util.HashMap
import org.bukkit.entity.Player

class ViewAllPagesMenu(private val menu: PaginatedMenu) : Menu() {

    init {
        autoUpdate = true
    }

    override fun getTitle(player: Player): String {
        return "Jump to page"
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        val buttons = HashMap<Int, Button>()

        buttons[0] = BackButton(object : Callback<Player> {
            override fun callback(value: Player) {
                menu.openMenu(player)
            }
        })

        var index = 10
        for (i in 1..menu.getPages(player)) {
            buttons[index++] = JumpToPageButton(i, menu)
            if ((index - 8) % 9 == 0) {
                index += 2
            }
        }

        return buttons
    }

}