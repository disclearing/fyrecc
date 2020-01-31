package net.evilblock.stark.core.availability

import net.evilblock.stark.core.StarkCore
import java.util.*

class AvailabilityHandler {

    fun fetch(uuid: UUID): AvailabilityData {
        return StarkCore.instance.redis.runBackboneRedisCommand { redis ->
            if (!redis.exists("stark:availability:$uuid")) {
                AvailabilityData()
            }

            val data = redis.hgetAll("stark:availability:$uuid")

            AvailabilityData(data["online"]!!.toBoolean(), data["proxy"]!!, data["server"]!!, data["lastTime"]!!.toLong())
        }
    }

    fun update(uuid: UUID, online: Boolean, proxy: String?, server: String?) {
        val data = hashMapOf<String, String>()
        data["online"] = online.toString()

        if (proxy != null) {
            data["proxy"] = proxy
        }

        if (server != null) {
            data["server"] = server
        }

        data["lastTime"] = System.currentTimeMillis().toString()

        StarkCore.instance.redis.runBackboneRedisCommand { redis ->
            redis.hmset("stark:availability:$uuid", data)
        }
    }

}