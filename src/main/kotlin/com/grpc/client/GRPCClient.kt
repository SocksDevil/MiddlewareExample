package com.grpc.client

import com.generic.models.GenericColors
import com.grpc.converters.Color_2_GenericColor
import com.grpc.converters.GenericConversionRequest_2_ConversionRequest
import com.grpc.converters.GenericPaletteRequest_2_PaletteRequest
import com.grpc.models.ColorsServiceGrpcKt
import com.grpc.models.Void
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.runBlocking
import java.io.Closeable
import java.util.*
import java.util.concurrent.ExecutionException

class GRPCClient(
    private val channel: ManagedChannel
) : Closeable {
    private val stub = ColorsServiceGrpcKt.ColorsServiceCoroutineStub(channel)
    private val scanner = Scanner(System.`in`)

    constructor(
        channelBuilder: ManagedChannelBuilder<*>,
        dispatcher: CoroutineDispatcher
    ) : this(
        channelBuilder.executor(dispatcher.asExecutor()).build()
    )

    private fun getOperationFromUser(): Int {
        println()
        println("Operations:")
        println("    [1] - Get random color")
        println("    [2] - Convert color")
        println("    [3] - Generate color palette")
        println()
        var choice = -1
        while (choice < 0 || 3 < choice) {
            println("Choose operation: ")
            while (!scanner.hasNextInt()) {
                scanner.nextLine()
            }
            choice = scanner.nextInt()
        }
        scanner.nextLine()
        println()
        return choice
    }

    @Throws(Exception::class)
    fun start() = runBlocking {
        var exit = false
        while (!exit) {
            when (getOperationFromUser()) {
                1 -> randomColorOperation(stub)
                2 -> convertColorOperation(stub)
                3 -> generatePaletteOperation(stub)
                0 -> exit = true
                else -> exit = true
            }
        }
    }

    private fun getColorModeFromUser(title: String): Int {
        println()
        System.out.printf("%s:%n", title)
        println("    [1] - Hexadecimal")
        println("    [2] - RGB")
        println("    [3] - CMYK")
        println("    [4] - HSV")
        println()
        var choice = -1
        while (choice < 1 || 4 < choice) {
            println("Choose mode: ")
            while (!scanner.hasNextInt()) {
                scanner.nextLine()
            }
            choice = scanner.nextInt()
        }
        scanner.nextLine()
        println()
        return choice
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    private suspend fun randomColorOperation(server: ColorsServiceGrpcKt.ColorsServiceCoroutineStub) {
        val result: GenericColors.Color
        when (getColorModeFromUser("Choose color mode for random color")) {
            1 -> {
                result = Color_2_GenericColor(server.getRandomHexColor(Void.getDefaultInstance()))
                println(result.toString())
                return
            }
            2 -> {
                result = Color_2_GenericColor(server.getRandomRGBColor(Void.getDefaultInstance()))
                println(result.toString())
                return
            }
            3 -> {
                result = Color_2_GenericColor(server.getRandomCMYKColor(Void.getDefaultInstance()))
                println(result.toString())
                return
            }
            4 -> {
                result = Color_2_GenericColor(server.getRandomHSVColor(Void.getDefaultInstance()))
                println(result.toString())
                return
            }
            else -> {
            }
        }
    }

    private fun getHexFromUser(): GenericColors.Color? {
        println("Insert an hexadecimal color code: ")
        val code = scanner.nextLine()
        return GenericColors.Color(GenericColors.Color.Mode(GenericColors.HEX(code)))
    }

    private fun getRGBFromUser(): GenericColors.Color? {
        println("Insert the red color value (0-255): ")
        val red = scanner.nextInt()
        println("Insert the green color value (0-255): ")
        val green = scanner.nextInt()
        println("Insert the blue color value (0-255): ")
        val blue = scanner.nextInt()
        scanner.nextLine()
        return GenericColors.Color(GenericColors.Color.Mode(GenericColors.RGB(red, green, blue)))
    }

    private fun getCMYKFromUser(): GenericColors.Color? {
        println("Insert the cyan color value (0-1): ")
        val cyan = scanner.nextFloat()
        println("Insert the magenta color value (0-1): ")
        val magenta = scanner.nextFloat()
        println("Insert the yellow color value (0-1): ")
        val yellow = scanner.nextFloat()
        println("Insert the key color value (0-1): ")
        val key = scanner.nextFloat()
        scanner.nextLine()
        return GenericColors.Color(GenericColors.Color.Mode(GenericColors.CMYK(cyan, magenta, yellow, key)))
    }

    private fun getHSVFromUser(): GenericColors.Color? {
        println("Insert the hue (0-360): ")
        val hue = scanner.nextInt()
        println("Insert the saturation (0-1): ")
        val saturation = scanner.nextFloat()
        println("Insert the value (0-1): ")
        val value = scanner.nextFloat()
        scanner.nextLine()
        return GenericColors.Color(GenericColors.Color.Mode(GenericColors.HSV(hue, saturation, value)))
    }

    private fun getColorFromUser(text: String): GenericColors.Color? {
        return when (getColorModeFromUser(text)) {
            1 -> getHexFromUser()
            2 -> getRGBFromUser()
            3 -> getCMYKFromUser()
            4 -> getHSVFromUser()
            else -> getHSVFromUser()
        }
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    private suspend fun convertColorOperation(server: ColorsServiceGrpcKt.ColorsServiceCoroutineStub) {
        val color = getColorFromUser("Color mode for original color")
        val colorMode: GenericColors.ColorMode
        colorMode = when (getColorModeFromUser("Color mode to convert to")) {
            1 -> GenericColors.ColorMode.HEX_MODE
            2 -> GenericColors.ColorMode.RGB_MODE
            3 -> GenericColors.ColorMode.CMYK_MODE
            4 -> GenericColors.ColorMode.HSV_MODE
            else -> GenericColors.ColorMode.HSV_MODE
        }
        val request = GenericColors.ColorConversionRequest(
            colorMode,
            color!!
        )
        val response = server.convertColor(GenericConversionRequest_2_ConversionRequest(request)!!)
        println(response.toString())
        println()
    }

    @Throws(ExecutionException::class, InterruptedException::class)
    private suspend fun generatePaletteOperation(server: ColorsServiceGrpcKt.ColorsServiceCoroutineStub) {
        val color = getColorFromUser("Color mode for base color")
        val request = GenericColors.ColorPaletteRequest(
            color!!
        )
        val response = server.generateColorPalette(GenericPaletteRequest_2_PaletteRequest(request)!!)
        println(response.toString())
        println()
    }

    override fun close() {
        channel.shutdown()
    }
}
