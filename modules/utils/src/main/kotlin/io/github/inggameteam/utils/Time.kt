package io.github.inggameteam.utils

enum class Time(val unit: String, val sec: Int) {
    DAYS("일", 86400),
    HOURS("시간", 3600),
    MINUTES("분", 60),
    SECONDS("초", 1),
    ;

    companion object {
        fun recordString(seconds: Double): String {
            var recordSeconds = seconds
            val strings = ArrayList<String>()
            values().forEach {
                if (recordSeconds >= it.sec) {
                    strings.add("${(recordSeconds / it.sec.toDouble()).toInt()}${it.unit}")
                    recordSeconds %= it.sec
                }
            }
            return strings.joinToString(" ")
        }
    }

}