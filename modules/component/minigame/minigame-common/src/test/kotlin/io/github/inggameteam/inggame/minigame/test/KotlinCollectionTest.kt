//package io.github.inggameteam.inggame.minigame.test
//
//import com.vjh0107.barcode.framework.utils.print
//import io.kotest.core.spec.style.AnnotationSpec
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import java.util.*
//import kotlin.system.measureNanoTime
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class KotlinCollectionTest : AnnotationSpec() {
//    private inline fun <T> List<T>.fastForEach(callback: (T) -> Unit) {
//        var n = 0
//        while (n < size) callback(this[n++])
//    }
//
//    private val iterable: MutableList<String> = mutableListOf()
//
//    @BeforeEach
//    fun setup() {
//        repeat(10000) {
//            val randomString = UUID.randomUUID().toString()
//            iterable.add(randomString)
//        }
//    }
//
//    @Test
//    fun `kotlin inline forEach`() = runTest {
//        val measured = measureNanoTime {
//            iterable.forEach {
//                it.print()
//            }
//        }
//        measured.print()
//    }
//
//    @Test
//    fun `may faster foreach`() = runTest {
//        val measured = measureNanoTime {
//            iterable.fastForEach {
//                it.print()
//            }
//        }
//        measured.print()
//    }
//
//    @Test
//    fun `for`() = runTest {
//        val measured = measureNanoTime {
//            for (element in iterable) {
//                element.print()
//            }
//        }
//        measured.print()
//    }
//
//    @AfterEach
//    fun teardown() {
//        iterable.clear()
//    }
//}
