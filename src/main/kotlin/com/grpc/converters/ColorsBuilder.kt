package com.grpc.converters

import com.grpc.models.*

fun buildHEXColor(hexDef: HEX, webSafe: Boolean = false): Color = Color
    .newBuilder()
    .setColorDef(
        Color.Mode
            .newBuilder()
            .setHexMode(
                hexDef
            )
    )
    .setWebSafe(webSafe)
    .build()

fun buildHEXColor(code: String, webSafe: Boolean = false): Color = buildHEXColor(buildHEX(code), webSafe)

fun buildHEX(code: String): HEX = HEX
    .newBuilder()
    .setCode(code)
    .build()

fun buildRGBColor(rgbDef: RGB, webSafe: Boolean = false): Color = Color
    .newBuilder()
    .setColorDef(
        Color.Mode
            .newBuilder()
            .setRgbMode(
                rgbDef
            )
    )
    .setWebSafe(webSafe)
    .build()

fun buildRGBColor(red: Int, green: Int, blue: Int, webSafe: Boolean = false): Color =
    buildRGBColor(buildRGB(red, green, blue), webSafe)

fun buildRGB(red: Int, green: Int, blue: Int): RGB = RGB
    .newBuilder()
    .setRed(red)
    .setGreen(green)
    .setBlue(blue)
    .build()

fun buildCMYKColor(cmykDef: CMYK, webSafe: Boolean = false): Color = Color
    .newBuilder()
    .setColorDef(
        Color.Mode
            .newBuilder()
            .setCmykMode(
                cmykDef
            )
    )
    .setWebSafe(webSafe)
    .build()

fun buildCMYKColor(cyan: Float, magenta: Float, yellow: Float, key: Float, webSafe: Boolean = false): Color =
    buildCMYKColor(buildCMYK(cyan, magenta, yellow, key), webSafe)

fun buildCMYK(cyan: Float, magenta: Float, yellow: Float, key: Float): CMYK = CMYK
    .newBuilder()
    .setCyan(cyan)
    .setMagenta(magenta)
    .setYellow(yellow)
    .setKey(key)
    .build()

fun buildHSVColor(hsvDef: HSV, webSafe: Boolean = false): Color = Color
    .newBuilder()
    .setColorDef(
        Color.Mode
            .newBuilder()
            .setHsvMode(
                hsvDef
            )
    )
    .setWebSafe(webSafe)
    .build()

fun buildHSVColor(hue: Int, saturation: Float, value: Float, webSafe: Boolean = false): Color = buildHSVColor(
    buildHSV(hue, saturation, value), webSafe
)

fun buildHSV(hue: Int, saturation: Float, value: Float): HSV = HSV
    .newBuilder()
    .setHue(hue)
    .setSaturation(saturation)
    .setValue(value)
    .build()
