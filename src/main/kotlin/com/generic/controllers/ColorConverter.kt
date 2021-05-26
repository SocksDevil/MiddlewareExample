package com.generic.controllers

import com.generic.models.GenericColors
import com.proto.models.Colors

fun convertColor(request: GenericColors.ColorConversionRequest): GenericColors.ColorConversionResponse? {
    val originalColor = request.color
    val requestedType = request.colorMode

    val convertedColor = when {
        originalColor.colorDef.hasHexMode() -> convertHexColor(originalColor.colorDef.hexMode, requestedType)
        originalColor.colorDef.hasRgbMode() -> convertRGBColor(originalColor.colorDef.rgbMode, requestedType)
        originalColor.colorDef.hasCmykMode() -> convertCMYKColor(originalColor.colorDef.cmykMode, requestedType)
        originalColor.colorDef.hasHsvMode() -> convertHSVColor(originalColor.colorDef.hsvMode, requestedType)
        else -> null
    }

    return convertedColor?.let { GenericColors.ColorConversionResponse(requestedType, convertedColor) }
}

fun convertHexColor(color: GenericColors.HEX?, toModel: GenericColors.ColorMode): GenericColors.Color? = when (toModel) {
    GenericColors.ColorMode.HEX_MODE -> buildHEXColor(HEX_2_HEX(color))
    GenericColors.ColorMode.RGB_MODE -> buildRGBColor(HEX_2_RGB(color))
    GenericColors.ColorMode.CMYK_MODE -> buildCMYKColor(HEX_2_CMYK(color))
    GenericColors.ColorMode.HSV_MODE -> buildHSVColor(HEX_2_HSV(color))
}

fun convertRGBColor(color: GenericColors.RGB?, toModel: GenericColors.ColorMode): GenericColors.Color? = when (toModel) {
    GenericColors.ColorMode.HEX_MODE -> buildHEXColor(RGB_2_HEX(color))
    GenericColors.ColorMode.RGB_MODE -> buildRGBColor(RGB_2_RGB(color))
    GenericColors.ColorMode.CMYK_MODE -> buildCMYKColor(RGB_2_CMYK(color))
    GenericColors.ColorMode.HSV_MODE -> buildHSVColor(RGB_2_HSV(color))
}

fun convertCMYKColor(color: GenericColors.CMYK?, toModel: GenericColors.ColorMode): GenericColors.Color? = when (toModel) {
    GenericColors.ColorMode.HEX_MODE -> buildHEXColor(CMYK_2_HEX(color))
    GenericColors.ColorMode.RGB_MODE -> buildRGBColor(CMYK_2_RGB(color))
    GenericColors.ColorMode.CMYK_MODE -> buildCMYKColor(CMYK_2_CMYK(color))
    GenericColors.ColorMode.HSV_MODE -> buildHSVColor(CMYK_2_HSV(color))
}

fun convertHSVColor(color: GenericColors.HSV?, toModel: GenericColors.ColorMode): GenericColors.Color? = when (toModel) {
    GenericColors.ColorMode.HEX_MODE -> buildHEXColor(HSV_2_HEX(color))
    GenericColors.ColorMode.RGB_MODE -> buildRGBColor(HSV_2_RGB(color))
    GenericColors.ColorMode.CMYK_MODE -> buildCMYKColor(HSV_2_CMYK(color))
    GenericColors.ColorMode.HSV_MODE -> buildHSVColor(HSV_2_HSV(color))
}
