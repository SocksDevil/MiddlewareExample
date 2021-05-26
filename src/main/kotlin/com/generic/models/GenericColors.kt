package com.generic.models

import com.google.gson.annotations.SerializedName

class GenericColors {
    data class Color(
        val colorDef: Mode
    ) {
        var webSafe: Boolean = false

        constructor(
                    colorDef: Mode,
                    webSafe: Boolean
        ) : this(colorDef){
            this.webSafe = webSafe
        }

        class Mode {
            var hexMode: HEX? = null
            var rgbMode: RGB? = null
            var cmykMode: CMYK? = null
            var hsvMode: HSV? = null

            constructor(hexMode: HEX) {
                this.hexMode = hexMode
            }

            constructor(rgbMode: RGB) {
                this.rgbMode = rgbMode
            }

            constructor(cmykMode: CMYK) {
                this.cmykMode = cmykMode
            }

            constructor(hsvMode: HSV) {
                this.hsvMode = hsvMode
            }

            fun hasHexMode(): Boolean = hexMode != null
            fun hasRgbMode(): Boolean = rgbMode != null
            fun hasCmykMode(): Boolean = cmykMode != null
            fun hasHsvMode(): Boolean = hsvMode != null

            override fun toString(): String {
                return when {
                    hasHexMode() -> hexMode.toString()
                    hasRgbMode() -> rgbMode.toString()
                    hasCmykMode() -> cmykMode.toString()
                    hasHsvMode() -> hsvMode.toString()
                    else -> ""
                }
            }
        }

        override fun toString(): String = "$colorDef (${if (webSafe) "web-safe" else "not web-safe"})"
    }

    data class HEX(
        val code: String
    )

    data class RGB(
        val red: Int,
        val green: Int,
        val blue: Int
    )

    data class CMYK(
        val cyan: Float,
        val magenta: Float,
        val yellow: Float,
        val key: Float
    )

    data class HSV(
        val hue: Int,
        val saturation: Float,
        val value: Float
    )

    enum class ColorMode {
        @SerializedName("HEX_MODE")
        HEX_MODE,
        @SerializedName("RGB_MODE")
        RGB_MODE,
        @SerializedName("CMYK_MODE")
        CMYK_MODE,
        @SerializedName("HSV_MODE")
        HSV_MODE
    }

    data class ColorConversionRequest(
        val colorMode: ColorMode,
        val color: Color
    ){
        override fun toString(): String = "Request color conversion to $colorMode for color: $color."
    }

    data class ColorConversionResponse(
        val colorMode: ColorMode,
        val color: Color
    ){
        override fun toString(): String = "Color converted to $colorMode.\nResult: $color"
    }

    data class ColorPaletteRequest(
        val color: Color
    ){
        override fun toString(): String = "Color palette requested for color: $color"
    }

    data class ColorPaletteResponse(
        val palette: List<Color>
    ){
        override fun toString(): String {
            var representation = "Generated color palette:"

            palette.forEach { representation += ("\n\t$it") }

            return representation
        }
    }
}