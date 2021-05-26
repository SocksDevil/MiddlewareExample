package com.grpc.shared

import com.generic.controllers.*
import com.grpc.converters.*
import com.grpc.models.*

class ColorsServiceImpl : ColorsServiceGrpcKt.ColorsServiceCoroutineImplBase() {
    private fun printOperation(op: String) {
        println()
        println("\t\t ---- GRPC ----")
        println(op)
        println()
    }

    override suspend fun getRandomHexColor(request: Void): Color {
        printOperation("getRandomHexColor()")
        return GenericColor_2_Color(buildHEXColor(getRandomHexColor()))
    }

    override suspend fun getRandomRGBColor(request: Void): Color {
        printOperation("getRandomRGBColor()")
        return GenericColor_2_Color(buildRGBColor(getRandomRGBColor()))
    }

    override suspend fun getRandomCMYKColor(request: Void): Color {
        printOperation("getRandomCMYKColor()")
        return GenericColor_2_Color(buildCMYKColor(getRandomCMYKColor()))
    }

    override suspend fun getRandomHSVColor(request: Void): Color {
        printOperation("getRandomHSVColor()")
        return GenericColor_2_Color(buildHSVColor(getRandomHSVColor()))
    }

    override suspend fun convertColor(request: ColorConversionRequest): ColorConversionResponse {
        printOperation("convertColor()")
        return GenericConversionResponse_2_ConversionResponse(
            convertColor(
                ConversionRequest_2_GenericConversionRequest(
                    request
                )!!
            )
        )!!
    }

    override suspend fun generateColorPalette(request: ColorPaletteRequest): ColorPaletteResponse {
        printOperation("generateColorPalette()")
        return GenericPaletteResponse_2_PaletteResponse(generatePalette(PaletteRequest_2_GenericPaletteRequest(request)!!))!!
    }
}