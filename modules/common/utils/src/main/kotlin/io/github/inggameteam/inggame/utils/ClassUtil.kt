package io.github.inggameteam.inggame.utils

import java.io.File
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.jar.JarFile
import kotlin.reflect.KType
import kotlin.reflect.jvm.javaType


object ClassUtil {

    fun getJarFile(clazz: Class<*>) = File(clazz.protectionDomain.codeSource.location.toURI())

    fun getDirectory(clazz: Class<*>): List<String> {
        val file = getJarFile(clazz)
        try {
            return JarFile(file).entries().toList()
                .filter { !it.isDirectory }.map { it.realName }.toList()
        } catch (_: Exception) { }
        println("MockBukkit detected")
        //MockBukkit testing
        val files = ArrayList<File>()
        fun dir(file: File) {
            file.listFiles()?.forEach {
                if (it.isFile) files.add(it)
                else dir(it)
            }
        }

        val base = File(file.parentFile.parentFile.parentFile, "resources/main")
        dir(base)
        return files.map { it.relativeTo(base).path }
    }

/*
        val files = ArrayList<File>()
        fun dir(file: File) {
            file.listFiles()?.forEach {
                if (it.isDirectory) dir(it)
                else files.add(it)
            }
        }
        dir(File(clazz.protectionDomain.codeSource.location.toURI()))
        println(files.map { it.name })
        return files.filter { it.isFile }.map { it.path }.toList()
*/

    fun matchClass(codec: List<String>, name: String) =
        listOf(name, *codec.map { "$it.$name" }.toTypedArray()).firstNotNullOfOrNull {
            try {
                Class.forName(it).kotlin
            } catch (_: Exception) {
                null
            }
        } ?: throw AssertionError("$name class not found")

}

val KType.singleClass: Class<*> get() = javaType.singleClass

val Type.singleClass: Class<*>
    get() {
        val javaType = this
        return if (javaType is Class<out Any>) {
            javaType
        } else if (javaType is ParameterizedType) {
            javaType.rawType as Class<out Any>
        } else throw AssertionError("cannot read class type ${javaType.typeName}")

    }