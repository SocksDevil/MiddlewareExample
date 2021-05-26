package com.generic.controllers

import com.generic.models.GenericColors

fun generatePalette(request: GenericColors.ColorPaletteRequest): GenericColors.ColorPaletteResponse? {
    val baseColor = request.color

    val palette: List<GenericColors.Color> = when {
        baseColor.colorDef.hasHexMode() -> generateHexPalette(baseColor.colorDef.hexMode)
        baseColor.colorDef.hasRgbMode() -> generateRGBPalette(baseColor.colorDef.rgbMode)
        baseColor.colorDef.hasCmykMode() -> generateCMYKPalette(baseColor.colorDef.cmykMode)
        baseColor.colorDef.hasHsvMode() -> generateHSVPalette(baseColor.colorDef.hsvMode)
        else -> null
    }
        ?: return null

    return GenericColors.ColorPaletteResponse(palette)
}

fun generateHexPalette(color: GenericColors.HEX?): List<GenericColors.Color> {
    val hsvColor = HEX_2_HSV(color)
    val hsvPalette = generatePalette(hsvColor)

    return hsvPalette.map { c -> buildHEXColor(HSV_2_HEX(c)) }
}

fun generateRGBPalette(color: GenericColors.RGB?): List<GenericColors.Color> {
    val hsvColor = RGB_2_HSV(color)
    val hsvPalette = generatePalette(hsvColor)

    return hsvPalette.map { c -> buildRGBColor(HSV_2_RGB(c)) }
}

fun generateCMYKPalette(color: GenericColors.CMYK?): List<GenericColors.Color> {
    val hsvColor = CMYK_2_HSV(color)
    val hsvPalette = generatePalette(hsvColor)

    return hsvPalette.map { c -> buildCMYKColor(HSV_2_CMYK(c)) }
}

fun generateHSVPalette(color: GenericColors.HSV?): List<GenericColors.Color> {
    val hsvPalette = generatePalette(color)

    return hsvPalette.map { c -> buildHSVColor(c) }
}
