package io.github.inggameteam.utils

import org.bukkit.util.NumberConversions
import org.bukkit.util.Vector
import kotlin.math.cos
import kotlin.math.sin

object VectorUtil {
        fun getDirection(yaw: Double, pitch: Double): Vector {
                val vector = Vector()
                vector.y = -sin(Math.toRadians(pitch))
                val xz = cos(Math.toRadians(pitch))
                vector.x = -xz * sin(Math.toRadians(yaw))
                vector.z = xz * cos(Math.toRadians(yaw))
                return vector
        }

        fun fromDirection(vector: Vector): Pair<Float, Float> {
                val pitch: Float
                var yaw = 0f

                val _2PI = 2 * Math.PI
                val x = vector.x
                val z = vector.z
                if (x == 0.0 && z == 0.0) {
                        pitch = if (vector.y > 0) -90f else 90f
                        return Pair(yaw, pitch)
                }
                val theta = Math.atan2(-x, z)
                yaw = Math.toDegrees((theta + _2PI) % _2PI).toFloat()
                val x2 = NumberConversions.square(x)
                val z2 = NumberConversions.square(z)
                val xz = Math.sqrt(x2 + z2)
                pitch = Math.toDegrees(Math.atan(-vector.y / xz)).toFloat()
                return Pair(yaw, pitch)
        }

}