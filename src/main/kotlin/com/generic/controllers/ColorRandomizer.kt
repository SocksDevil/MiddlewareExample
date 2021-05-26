package com.generic.controllers

import com.generic.models.GenericColors
import com.proto.models.Colors
import kotlin.random.Random

fun getRandomHexColor(): GenericColors.HEX {
    val hexCode = CharArray(6)

    for (i in hexCode.indices) {
        val randCode = (48..63).random()
        hexCode[i] = (if (randCode > 57) randCode + 7 else randCode).toChar()
    }

    return GenericColors.HEX("""#${String(hexCode)}""")
}

fun getRandomRGBColor(): GenericColors.RGB = GenericColors.RGB((0..255).random(), (0..255).random(), (0..255).random())

fun getRandomCMYKColor(): GenericColors.CMYK =
    GenericColors.CMYK(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), Random.nextFloat())

fun getRandomHSVColor(): GenericColors.HSV =
    GenericColors.HSV((0..360).random(), Random.nextFloat(), Random.nextFloat())
