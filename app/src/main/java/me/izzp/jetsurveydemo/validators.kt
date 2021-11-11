package me.izzp.jetsurveydemo


fun errEmail(text: String): String? {
    if (text.contains("@") && text.contains(".")) {
        return null
    } else {
        return "Invalid email"
    }
}

fun errPassword(text: String): String? {
    if (text.contains(" ") || text.length < 4) {
        return "密码长度必须大于4，且不含空格"
    } else {
        return null
    }
}