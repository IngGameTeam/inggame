package io.github.inggameteam.utils

import java.io.File
import java.util.jar.JarFile

object ClassUtil {

    fun getDirectory(clazz: Class<*>) =
        JarFile(File(clazz.protectionDomain.codeSource.location.toURI())).entries().toList()
            .filter { !it.isDirectory }.map { it.name }.toList()
}