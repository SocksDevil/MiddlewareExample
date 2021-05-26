package com.generic.controllers

import com.generic.models.GenericColors

fun buildHEXColor(hexDef: GenericColors.HEX): GenericColors.Color =
    GenericColors.Color(GenericColors.Color.Mode(hexDef), isWebSafe(hexDef))

fun buildHEXColor(code: String): GenericColors.Color = buildHEXColor(buildHEX(code))

fun buildHEX(code: String): GenericColors.HEX = GenericColors.HEX(code)

fun buildRGBColor(rgbDef: GenericColors.RGB): GenericColors.Color =
    GenericColors.Color(GenericColors.Color.Mode(rgbDef), isWebSafe(rgbDef))

fun buildRGBColor(red: Int, green: Int, blue: Int): GenericColors.Color = buildRGBColor(buildRGB(red, green, blue))

fun buildRGB(red: Int, green: Int, blue: Int): GenericColors.RGB = GenericColors.RGB(red, green, blue)

fun buildCMYKColor(cmykDef: GenericColors.CMYK): GenericColors.Color =
    GenericColors.Color(GenericColors.Color.Mode(cmykDef), isWebSafe(cmykDef))

fun buildCMYKColor(cyan: Float, magenta: Float, yellow: Float, key: Float): GenericColors.Color =
    buildCMYKColor(buildCMYK(cyan, magenta, yellow, key))

fun buildCMYK(cyan: Float, magenta: Float, yellow: Float, key: Float): GenericColors.CMYK =
    GenericColors.CMYK(cyan, magenta, yellow, key)

fun buildHSVColor(hsvDef: GenericColors.HSV): GenericColors.Color =
    GenericColors.Color(GenericColors.Color.Mode(hsvDef), isWebSafe(hsvDef))

fun buildHSVColor(hue: Int, saturation: Float, value: Float): GenericColors.Color =
    buildHSVColor(hue, saturation, value)

fun buildHSV(hue: Int, saturation: Float, value: Float): GenericColors.HSV = GenericColors.HSV(hue, saturation, value)
