package com.cjcrafter.gitbook.gson

import com.cjcrafter.gitbook.search.AskResponse.Answer.Source
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class SourceTypeAdapter : JsonDeserializer<Source> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Source {
        val jsonObject = json.asJsonObject

        return when (val type = jsonObject["type"].asString) {
            "page" -> context.deserialize<Source.PageSource>(json, Source.PageSource::class.java)
            "entity" -> context.deserialize<Source.EntitySource>(json, Source.EntitySource::class.java)
            "capture" -> context.deserialize<Source.CaptureSource>(json, Source.CaptureSource::class.java)
            else -> throw JsonParseException("Not a valid Source type: $type (for json: $json)")
        }
    }
}
