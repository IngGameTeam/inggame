package io.github.inggameteam.inggame.plugin.test

import io.github.inggameteam.inggame.mongodb.Model
import net.sf.corn.cps.CPScanner
import net.sf.corn.cps.ClassFilter
import net.sf.corn.cps.PackageNameFilter

@Test
class ReflectionTest {

    init {
        CPScanner.scanClasses(
            PackageNameFilter("io.github.inggameteam.inggame.plugin"),
        ).apply {
            println(this)
        }
    }

}