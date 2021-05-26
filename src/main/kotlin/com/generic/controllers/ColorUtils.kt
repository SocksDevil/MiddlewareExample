package com.generic.controllers

import com.generic.models.GenericColors
import com.proto.models.Colors
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

fun isWebSafe(color: GenericColors.HEX?): Boolean {
    if(color == null) return false

    val webSafeCodes = arrayOf("00", "33", "66", "99", "CC", "FF")
    val rawCode = color.code.filterNot { c -> c == '#' }

    val byte1 = rawCode.substring(0, 2)
    val byte2 = rawCode.substring(2, 4)
    val byte3 = rawCode.substring(4, 6)

    return webSafeCodes.contains(byte1) and webSafeCodes.contains(byte2) and webSafeCodes.contains(byte3)
}

fun isWebSafe(color: GenericColors.RGB?): Boolean {
    return isWebSafe(RGB_2_HEX(color))
}

fun isWebSafe(color: GenericColors.CMYK?): Boolean {
    return isWebSafe(CMYK_2_HEX(color))
}

fun isWebSafe(color: GenericColors.HSV?): Boolean {
    return isWebSafe(HSV_2_HEX(color))
}

fun HEX_2_HEX(hex: GenericColors.HEX?): GenericColors.HEX {
    return hex!!
}

fun HEX_2_RGB(hex: GenericColors.HEX?): GenericColors.RGB {
    if(hex == null) return buildRGB(-1, -1, -1)

    val rawCode = hex.code.filterNot { c -> c == '#' }

    if (rawCode.length != 6)
        return buildRGB(-1, -1, -1)

    val red = rawCode.subSequence(0, 2).toString().toInt(16)
    val green = rawCode.subSequence(2, 4).toString().toInt(16)
    val blue = rawCode.subSequence(4, 6).toString().toInt(16)

    return buildRGB(red, green, blue)
}

fun HEX_2_CMYK(hex: GenericColors.HEX?): GenericColors.CMYK {
    return RGB_2_CMYK(HEX_2_RGB(hex))
}

fun HEX_2_HSV(hex: GenericColors.HEX?): GenericColors.HSV {
    return RGB_2_HSV(HEX_2_RGB(hex))
}

fun RGB_2_HEX(rgb: GenericColors.RGB?): GenericColors.HEX {
    if(rgb == null) return buildHEX("")

    val red = String.format("%02X", rgb.red)
    val green = String.format("%02X", rgb.green)
    val blue = String.format("%02X", rgb.blue)

    return buildHEX("#$red$green$blue")
}

fun RGB_2_RGB(rgb: GenericColors.RGB?): GenericColors.RGB {
    return rgb!!
}

fun RGB_2_CMYK(rgb: GenericColors.RGB?): GenericColors.CMYK {
    if(rgb == null) return buildCMYK(-1f, -1f, -1f, -1f)

    val red = rgb.red
    val green = rgb.green
    val blue = rgb.blue

    val Rc: Float = (red / 255.0).toFloat()
    val Gc: Float = (green / 255.0).toFloat()
    val Bc: Float = (blue / 255.0).toFloat()

    val K = 1 - max(max(Rc, Gc), Bc)

    if (1.0.compareTo(K) == 0)
        return buildCMYK(0.toFloat(), 0.toFloat(), 0.toFloat(), K)

    val cyan = (1 - Rc - K) / (1 - K)
    val magenta = (1 - Gc - K) / (1 - K)
    val yellow = (1 - Bc - K) / (1 - K)

    return buildCMYK(cyan, magenta, yellow, K)
}

fun RGB_2_HSV(rgb: GenericColors.RGB?): GenericColors.HSV {
    if(rgb == null) return buildHSV(-1, -1f, -1f)

    val red = rgb.red
    val green = rgb.green
    val blue = rgb.blue

    val Rc: Float = (red / 255.0).toFloat()
    val Gc: Float = (green / 255.0).toFloat()
    val Bc: Float = (blue / 255.0).toFloat()

    val minVal = min(min(Rc, Gc), Bc)
    val maxVal = max(max(Rc, Gc), Bc)
    val delta = maxVal - minVal

    val value = maxVal

    if (0.0.compareTo(delta) == 0)
        return buildHSV(0, 0.toFloat(), value)

    val saturation = delta / maxVal

    val deltaR = (((maxVal - Rc) / 6) + (delta / 2)) / delta
    val deltaG = (((maxVal - Gc) / 6) + (delta / 2)) / delta
    val deltaB = (((maxVal - Bc) / 6) + (delta / 2)) / delta

    var hue: Float = when (maxVal) {
        Rc -> deltaB - deltaG
        Gc -> (1.0 / 3.0) + deltaR - deltaB
        Bc -> (2.0 / 3.0) + deltaG - deltaR
        else -> 0
    }.toFloat()

    if (hue < 0) hue += 1
    if (hue > 1) hue -= 1

    hue *= 360

    return buildHSV(hue.roundToInt(), saturation, value)
}

fun CMYK_2_HEX(cmyk: GenericColors.CMYK?): GenericColors.HEX {
    return RGB_2_HEX(CMYK_2_RGB(cmyk))
}

fun CMYK_2_RGB(cmyk: GenericColors.CMYK?): GenericColors.RGB {
    if(cmyk == null) return buildRGB(-1, -1, -1)

    val cyan = cmyk.cyan
    val magenta = cmyk.magenta
    val yellow = cmyk.yellow
    val key = cmyk.key

    val red = (1f - min(1.0f, cyan * (1f - key) + key)) * 255f
    val green = (1f - min(1.0f, magenta * (1f - key) + key)) * 255f
    val blue = (1f - min(1.0f, yellow * (1f - key) + key)) * 255f

    return buildRGB(red.roundToInt(), green.roundToInt(), blue.roundToInt())
}

fun CMYK_2_CMYK(cmyk: GenericColors.CMYK?): GenericColors.CMYK {
    return cmyk!!
}

fun CMYK_2_HSV(cmyk: GenericColors.CMYK?): GenericColors.HSV {
    return RGB_2_HSV(CMYK_2_RGB(cmyk))
}

fun HSV_2_HEX(hsv: GenericColors.HSV?): GenericColors.HEX {
    return RGB_2_HEX(HSV_2_RGB(hsv))
}

fun HSV_2_RGB(hsv: GenericColors.HSV?): GenericColors.RGB {
    if(hsv == null) return buildRGB(-1, -1, -1)

    val hue: Float = hsv.hue / 360.0f
    val saturation: Float = hsv.saturation
    val value: Float = hsv.value

    if (0.0.compareTo(saturation) == 0)
        return buildRGB((value * 255f).roundToInt(), (value * 255f).roundToInt(), (value * 255f).roundToInt())

    val varH = hue * 6f
    val varI = floor(varH).toInt()
    val var1 = value * (1.0f - saturation)
    val var2 = value * (1.0f - saturation * (varH - varI))
    val var3 = value * (1.0f - saturation * (1.0f - (varH - varI)))

    var red: Float
    var green: Float
    var blue: Float

    when (varI) {
        0 -> {
            red = value * 255f
            green = var3 * 255f
            blue = var1 * 255f
        }
        1 -> {
            red = var2 * 255f
            green = value * 255f
            blue = var1 * 255f
        }
        2 -> {
            red = var1 * 255f
            green = value * 255f
            blue = var3 * 255f
        }
        3 -> {
            red = var1 * 255f
            green = var2 * 255f
            blue = value * 255f
        }
        4 -> {
            red = var3 * 255f
            green = var1 * 255f
            blue = value * 255f
        }
        else -> {
            red = value * 255f
            green = var1 * 255f
            blue = var2 * 255f
        }
    }

    return buildRGB(red.roundToInt(), green.roundToInt(), blue.roundToInt())
}

fun HSV_2_CMYK(hsv: GenericColors.HSV?): GenericColors.CMYK {
    return RGB_2_CMYK(HSV_2_RGB(hsv))
}

fun HSV_2_HSV(hsv: GenericColors.HSV?): GenericColors.HSV {
    return hsv!!
}

fun generatePalette(color: GenericColors.HSV?): List<GenericColors.HSV> {
    if(color == null) return ArrayList<GenericColors.HSV>(0)

    var hue = color.hue
    val saturation = color.saturation
    val value = color.value

    val delta = (10..50).random()
    val palette = ArrayList<GenericColors.HSV>(6)
    palette.add(color)

    for (i in 1..5) {
        hue = (hue + delta) % 361

        palette.add(buildHSV(hue, saturation, value))
    }

    return palette
}
