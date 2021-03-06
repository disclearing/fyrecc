package net.evilblock.stark.profile.grant.conversation

import nig.cock.nigger.message.Message
import net.evilblock.stark.Stark
import net.evilblock.stark.profile.BukkitProfile
import net.evilblock.stark.core.profile.grant.ProfileGrant
import net.evilblock.stark.core.rank.Rank
import net.evilblock.stark.core.util.TimeUtils
import net.evilblock.stark.util.DateUtil
import org.bukkit.ChatColor
import org.bukkit.conversations.ConversationContext
import org.bukkit.conversations.Prompt
import org.bukkit.conversations.StringPrompt
import org.bukkit.entity.Player
import java.lang.Exception

class GrantCreationPeriodPrompt(val target: BukkitProfile) : StringPrompt() {

    override fun getPromptText(conversationContext: ConversationContext): String {
        return ChatColor.GREEN.toString() + "Please specify a valid time."
    }

    override fun acceptInput(conversationContext: ConversationContext, input: String): Prompt? {
        var perm = false
        var expiresAt = 0L

        if (input.toLowerCase() == "perm") {
            perm = true
        }

        if (!(perm)) {
            try {
                expiresAt = System.currentTimeMillis() - DateUtil.parseDateDiff(input, false)
            } catch (exception: Exception) {
                conversationContext.forWhom.sendRawMessage("${ChatColor.RED}Invalid duration.")
                return Prompt.END_OF_CONVERSATION
            }
        }

        val grant = ProfileGrant()
        grant.rank = conversationContext.getSessionData("rank") as Rank
        grant.reason = conversationContext.getSessionData("reason") as String
        grant.issuedBy = (conversationContext.forWhom as Player).uniqueId
        grant.issuedAt = System.currentTimeMillis()

        if (!perm) {
            grant.expiresAt = expiresAt + System.currentTimeMillis()
        }

        target.rankGrants.add(grant)

        Stark.instance.core.getProfileHandler().saveProfile(target)
        Stark.instance.core.globalMessageChannel.sendMessage(Message("GRANT_UPDATE", mapOf("uuid" to target.uuid.toString(), "grant" to grant.uuid.toString())))

        val period = if (grant.expiresAt == null) {
            "forever"
        } else {
            TimeUtils.formatIntoDetailedString(((grant.expiresAt!! - System.currentTimeMillis()) / 1000).toInt())
        }

        conversationContext.forWhom.sendRawMessage("${ChatColor.GREEN}You've granted " + target.getPlayerListName() + " ${ChatColor.GREEN}the ${grant.rank.getColoredName()} ${ChatColor.GREEN}rank for ${ChatColor.YELLOW}$period${ChatColor.GREEN}.")

        return Prompt.END_OF_CONVERSATION
    }

}
