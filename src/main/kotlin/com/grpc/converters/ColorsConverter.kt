package com.grpc.converters

import com.generic.models.GenericColors
import com.grpc.models.*

fun ColorMode_2_GenericColorMode(colorMode: ColorMode): GenericColors.ColorMode = when (colorMode) {
    ColorMode.HEX_MODE -> GenericColors.ColorMode.HEX_MODE
    ColorMode.RGB_MODE -> GenericColors.ColorMode.RGB_MODE
    ColorMode.CMYK_MODE -> GenericColors.ColorMode.CMYK_MODE
    ColorMode.HSV_MODE -> GenericColors.ColorMode.HSV_MODE
}

fun GenericColorMode_2_ColorMode(colorMode: GenericColors.ColorMode): ColorMode = when (colorMode) {
    GenericColors.ColorMode.HEX_MODE -> ColorMode.HEX_MODE
    GenericColors.ColorMode.RGB_MODE -> ColorMode.RGB_MODE
    GenericColors.ColorMode.CMYK_MODE -> ColorMode.CMYK_MODE
    GenericColors.ColorMode.HSV_MODE -> ColorMode.HSV_MODE
}

fun Color_2_GenericColor(color: Color): GenericColors.Color {
    when {
        color.colorDef.hasHexMode() -> {
            val hexColor = color.colorDef.hexMode

            return GenericColors.Color(GenericColors.Color.Mode(GenericColors.HEX(hexColor.code)), color.webSafe)
        }
        color.colorDef.hasRgbMode() -> {
            val rgbColor = color.colorDef.rgbMode

            return GenericColors.Color(
                GenericColors.Color.Mode(
                    GenericColors.RGB(
                        rgbColor.red,
                        rgbColor.green,
                        rgbColor.blue
                    )
                ), color.webSafe
            )
        }
        color.colorDef.hasCmykMode() -> {
            val cmykColor = color.colorDef.cmykMode

            return GenericColors.Color(
                GenericColors.Color.Mode(
                    GenericColors.CMYK(
                        cmykColor.cyan,
                        cmykColor.magenta,
                        cmykColor.yellow,
                        cmykColor.key
                    )
                ), color.webSafe
            )
        }
        color.colorDef.hasHsvMode() -> {
            val hsvColor = color.colorDef.hsvMode

            return GenericColors.Color(
                GenericColors.Color.Mode(
                    GenericColors.HSV(
                        hsvColor.hue,
                        hsvColor.saturation,
                        hsvColor.value
                    )
                ), color.webSafe
            )
        }
        else -> return GenericColors.Color(GenericColors.Color.Mode(GenericColors.HEX("")))
    }
}

fun GenericColor_2_Color(genericColor: GenericColors.Color): Color {
    when {
        genericColor.colorDef.hasHexMode() -> {
            val hexColor = genericColor.colorDef.hexMode ?: return buildHEXColor("")

            return buildHEXColor(hexColor.code, genericColor.webSafe)
        }
        genericColor.colorDef.hasRgbMode() -> {
            val rgbColor = genericColor.colorDef.rgbMode ?: return buildRGBColor(-1, -1, -1)

            return buildRGBColor(rgbColor.red, rgbColor.green, rgbColor.blue, genericColor.webSafe)
        }
        genericColor.colorDef.hasCmykMode() -> {
            val cmykColor = genericColor.colorDef.cmykMode ?: return buildCMYKColor(
                -1f,
                1f,
                -1f,
                -1f
            )

            return buildCMYKColor(
                cmykColor.cyan,
                cmykColor.magenta,
                cmykColor.yellow,
                cmykColor.key,
                genericColor.webSafe
            )
        }
        genericColor.colorDef.hasHsvMode() -> {
            val hsvColor = genericColor.colorDef.hsvMode ?: return buildHSVColor(-1, -1f, -1f)

            return buildHSVColor(
                hsvColor.hue,
                hsvColor.saturation,
                hsvColor.value,
                genericColor.webSafe
            )
        }
        else -> return buildHEXColor("")
    }
}

fun ConversionRequest_2_GenericConversionRequest(conversionRequest: ColorConversionRequest?): GenericColors.ColorConversionRequest? {
    if (conversionRequest == null) return null

    val genericColorMode: GenericColors.ColorMode = ColorMode_2_GenericColorMode(conversionRequest.colorMode)
    val genericColor: GenericColors.Color = Color_2_GenericColor(conversionRequest.color)

    return GenericColors.ColorConversionRequest(genericColorMode, genericColor)
}

fun GenericConversionRequest_2_ConversionRequest(genericConversionRequest: GenericColors.ColorConversionRequest?): ColorConversionRequest? {
    if (genericConversionRequest == null) return null

    val genericColorMode: ColorMode = GenericColorMode_2_ColorMode(genericConversionRequest.colorMode)
    val genericColor: Color = GenericColor_2_Color(genericConversionRequest.color)

    return ColorConversionRequest.newBuilder().setColorMode(genericColorMode).setColor(genericColor).build()
}

fun GenericConversionResponse_2_ConversionResponse(genericConversionResponse: GenericColors.ColorConversionResponse?): ColorConversionResponse? {
    if (genericConversionResponse == null) return null

    val colorMode: ColorMode = GenericColorMode_2_ColorMode(genericConversionResponse.colorMode)
    val color: Color = GenericColor_2_Color(genericConversionResponse.color)

    return ColorConversionResponse.newBuilder().setColorMode(colorMode).setColor(color).build()
}

fun PaletteRequest_2_GenericPaletteRequest(paletteRequest: ColorPaletteRequest?): GenericColors.ColorPaletteRequest? {
    if (paletteRequest == null) return null

    val color: GenericColors.Color = Color_2_GenericColor(paletteRequest.color)

    return GenericColors.ColorPaletteRequest(color)
}

fun GenericPaletteRequest_2_PaletteRequest(genericPaletteRequest: GenericColors.ColorPaletteRequest?): ColorPaletteRequest? {
    if (genericPaletteRequest == null) return null

    val color: Color = GenericColor_2_Color(genericPaletteRequest.color)

    return ColorPaletteRequest.newBuilder().setColor(color).build()
}

fun GenericPaletteResponse_2_PaletteResponse(genericPaletteResponse: GenericColors.ColorPaletteResponse?): ColorPaletteResponse? {
    if (genericPaletteResponse == null) return null

    val palette: List<Color> =
        genericPaletteResponse.palette.map { genericColor -> GenericColor_2_Color(genericColor) }

    return ColorPaletteResponse.newBuilder().addAllPalette(palette).build()
}