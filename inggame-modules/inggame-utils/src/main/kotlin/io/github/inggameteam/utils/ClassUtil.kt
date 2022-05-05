package io.github.inggameteam.utils

import java.io.File
import java.util.jar.JarFile


object ClassUtil {

    fun getDirectory(clazz: Class<*>): List<String> {
        val file = File(clazz.protectionDomain.codeSource.location.toURI())
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

        val base = File(file.parentFile.parentFile.parentFile, "resources/test")
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

}