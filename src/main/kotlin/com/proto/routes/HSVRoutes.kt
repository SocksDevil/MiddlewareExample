package com.proto.routes

import com.generic.controllers.buildHSVColor
import com.generic.controllers.convertColor
import com.generic.controllers.generatePalette
import com.generic.controllers.getRandomHSVColor
import com.proto.converters.*
import com.proto.models.Colors
import com.proto.printCall
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.hsvRouting() {
    route("/hsv") {
        get("/random") {
            printCall("/hsv/random")

            call.respond(GenericColor_2_Color(buildHSVColor(getRandomHSVColor())))
        }
        post("/convert") {
            val conversionRequest = call.receive<Colors.ColorConversionRequest>()

            printCall("/hsv/convert")

            if (!conversionRequest.color.colorDef.hasHsvMode())
                call.respondText("Color Mode must be HSV", status = HttpStatusCode.BadRequest)

            val genericConversionRequest =
                ConversionRequest_2_GenericConversionRequest(conversionRequest)

            if (genericConversionRequest == null)
                call.respondText("Error converting color", status = HttpStatusCode.InternalServerError)
            else {
                val conversionResponse = GenericConversionResponse_2_ConversionResponse(
                    convertColor(
                        genericConversionRequest
                    )
                ) ?: call.respondText("Error converting color", status = HttpStatusCode.InternalServerError)

                call.respond(conversionResponse)
            }
        }
        post("/palette") {
            val paletteRequest = call.receive<Colors.ColorPaletteRequest>()

            printCall("/hsv/palette")

            if (!paletteRequest.color.colorDef.hasHsvMode())
                call.respondText("Color Mode must be HSV", status = HttpStatusCode.BadRequest)

            val genericPaletteRequest = PaletteRequest_2_GenericPaletteRequest(paletteRequest)

            if (genericPaletteRequest == null)
                call.respondText("Error converting color", status = HttpStatusCode.InternalServerError)
            else {
                val paletteResponse = GenericPaletteResponse_2_PaletteResponse(
                    generatePalette(
                        genericPaletteRequest
                    )
                ) ?: call.respondText("Error converting color", status = HttpStatusCode.InternalServerError)

                call.respond(paletteResponse)
            }
        }
    }
}

fun Application.registerHSVRoutes() {
    routing {
        hsvRouting()
    }
}
