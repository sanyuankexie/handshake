package com.guet.flexbox.handshake.configs

import com.google.gson.Gson
import java.util.*

object ComponentConfiguration {

    private val components: List<ComponentInfo>

    init {
        val gson = Gson()
        components = Collections.list(
            javaClass.classLoader.getResources("flexml-components")
        ).map {
            val input = it.openStream().reader()
            val r = gson.fromJson(input, ComponentInfo::class.java)
            input.close()
            return@map r
        }
    }

    val allComponentNames: List<String>
        get() = components.filter {
            !it.abstract
        }.map {
            it.name
        }

}