import kotlin.random.Random

fun main() {
    val palabras = listOf("kotlin", "java", "programacion", "android")
    val palabraSecreta = palabras[Random.nextInt(palabras.size)]
    val letrasAdivinadas = mutableSetOf<Char>()
    var errores = 0
    val maxErrores = 7

    // 🎵 Reproducir música
    val reproductor = try {
        ReproductorMidi("src/AC-DC.mid")
    } catch (e: Exception) {
        println("No se pudo reproducir la música")
        null
    }

    println("🎮 JUEGO DEL AHORCADO 🎮")

    while (errores < maxErrores) {

        // 🎨 Dibujar ahorcado según errores
        if (errores > 0) {
            DibujoAhorcado.dibujar(errores)
        }

        // Mostrar palabra
        for (c in palabraSecreta) {
            if (c in letrasAdivinadas) print("$c ")
            else print("_ ")
        }
        println()

        print("Ingresa una letra: ")
        val entrada = readLine()?.lowercase()

        if (entrada.isNullOrEmpty() || entrada.length != 1) {
            println("❌ Ingresa solo una letra")
            continue
        }

        val letra = entrada[0]

        if (letra in letrasAdivinadas) {
            println("⚠️ Ya usaste esa letra")
            continue
        }

        letrasAdivinadas.add(letra)

        if (letra !in palabraSecreta) {
            errores++
            println("❌ Letra incorrecta")
        }

        if (palabraSecreta.all { it in letrasAdivinadas }) {
            println("\n🎉 ¡GANASTE!")
            println("La palabra era: $palabraSecreta")
            reproductor?.cerrar()
            return
        }
        println("$errores/$maxErrores fallos")
    }

    // ☠️ Perdió
    DibujoAhorcado.dibujar(maxErrores)
    println("\n💀 HAS PERDIDO")
    println("La palabra era: $palabraSecreta")
    reproductor?.cerrar()
}