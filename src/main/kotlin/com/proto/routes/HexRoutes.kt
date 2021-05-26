package com.proto.routes

import com.generic.controllers.buildHEXColor
import com.generic.controllers.convertColor
import com.generic.controllers.generatePalette
import com.generic.controllers.getRandomHexColor
import com.proto.converters.*
import com.proto.models.Colors
import com.proto.printCall
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.hexRouting() {
    route("/hex") {
        get("/random") {
            printCall("/hex/random")

            call.respond(GenericColor_2_Color(buildHEXColor(getRandomHexColor())))
        }
        post("/convert") {
            val conversionRequest = call.receive<Colors.ColorConversionRequest>()

            printCall("/hex/convert")

            if (!conversionRequest.color.colorDef.hasHexMode())
                call.respondText("Color Mode must be HEX", status = HttpStatusCode.BadRequest)

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

            printCall("/hex/palette")

            if (!paletteRequest.color.colorDef.hasHexMode())
                call.respondText("Color Mode must be HEX", status = HttpStatusCode.BadRequest)

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

fun Application.registerHexRoutes() {
    routing {
        hexRouting()
    }
}
