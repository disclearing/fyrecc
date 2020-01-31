package nig.cock.nigger.morph.adapter.impl

import nig.cock.nigger.morph.adapter.JsonTypeAdapter
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import java.util.UUID

class UUIDTypeAdapter : JsonTypeAdapter<UUID> {

    override fun toJson(src: UUID): JsonElement {
        return JsonPrimitive(src.toString())
    }

    override fun toType(element: JsonElement): UUID {
        return UUID.fromString(element.asString)
    }

}
