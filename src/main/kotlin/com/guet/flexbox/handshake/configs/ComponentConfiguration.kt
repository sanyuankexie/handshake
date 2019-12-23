package com.guet.flexbox.handshake.configs

import com.google.gson.Gson

object ComponentConfiguration {

    private val components: List<ComponentInfo>

    init {
        val gson = Gson()
        val classLoader = javaClass.classLoader
        val group = classLoader.getResource("flexml-components/package.json")!!
            .openStream()
            .reader()
        val arr = gson.fromJson(
            group, ComponentsPackage::class.java
        ).components
        group.close()
        components = arr.map {
            val input = classLoader.getResourceAsStream(it)!!.reader()
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