package ru.nowandroid.youtube.rostselmash.models

import java.io.Serializable

data class State (var id: String = "", var title: String = "", var content: String = "") : Serializable {
    fun toMap(): Map<String, Any> {
        val map = HashMap<String, Any>()
        map.put("title", title)
        map.put("content", content)
        return map
    }
}