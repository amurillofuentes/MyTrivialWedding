package com.familygames.trivialwedding

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.media.MediaPlayer
import android.widget.Toast
import com.familygames.trivialwedding.simon.SimonGameActivity

//TODO: Videos nuestros cada x
//TODO: En preguntas, mostrar animacion de hucha ruta y un buuu cuando se equivocan
//TODO: más juegos con mismo sistema del sudoku
/*
[9:59, 25/5/2023] LaRisis: Mejor alineación de título
[9:59, 25/5/2023] LaRisis: Comodines se ven mal
[10:00, 25/5/2023] LaRisis: Simon descuadrado
[10:00, 25/5/2023] LaRisis: Música en simon
[10:01, 25/5/2023] LaRisis: Título de pausa tecnia en simon
[10:02, 25/5/2023] LaRisis: Definir donde va cada juego y que el comodín y que no
[10:02, 25/5/2023] LaRisis: En sudoku que no salgan 0. Simplemente que no haya nada
[10:04, 25/5/2023] LaRisis: Poner niveles en simon y sudoku
[10:04, 25/5/2023] LaRisis: A modo informativo
[10:05, 25/5/2023] LaRisis: Comodin 3 aparecerá piedra papel o tijera
[10:05, 25/5/2023] LaRisis: Solo esa vez
[10:06, 25/5/2023] LaRisis: Porque ahí influye azar
[10:06, 25/5/2023] LaRisis: Juego ahorcado
[10:06, 25/5/2023] LaRisis: Juego memoria
[10:06, 25/5/2023] LaRisis: Juego sudoku
[10:06, 25/5/2023] LaRisis: Juego simmon
[10:06, 25/5/2023] LaRisis: Juego respuestas
[10:07, 25/5/2023] LaRisis: Comodín 2 será un tres en raya. Si se pierde plof
Si se gana ok
Si se empata volver a probar
[10:09, 25/5/2023] LaRisis: 150 preguntas
4 juegos
Cada uno se va a jugar 10 veces
Son 50 juegos
Cada 5 saldrá uno entonces?

*/


class MainQuestions : AppCompatActivity() {
    private lateinit var questionTextView: TextView
    private lateinit var maxScoreTexView: TextView
    private lateinit var scoreTexView: TextView

    private lateinit var textInfoMoney: TextView

    private lateinit var option1Button: Button
    private lateinit var option2Button: Button
    private lateinit var option3Button: Button
    private lateinit var option4Button: Button

    private lateinit var comodin1Button: Button
    private lateinit var comodin2Button: Button
    private lateinit var comodin3Button: Button

    private var ultimaPuntuacion = 0

    private var mediaPlayerAbucheo: MediaPlayer? = null
    private var mediaPlayerCoin: MediaPlayer? = null

    private var orden = listOf(
        85, 46, 78, 28, 36, 64, 63, 92, 57, 89, 31, 20, 56, 68, 77, 93, 5, 54, 2, 35, 32, 51, 95, 80,
        39, 17, 18, 24, 94, 86, 91, 61, 65, 66, 73, 42, 10, 40, 25, 72, 33, 53, 96, 90, 27, 71, 11, 13,
        44, 19, 47, 21, 12, 0, 38, 9, 62, 70, 16, 81, 23, 87, 67, 14, 75, 48, 60, 79, 15, 45, 37, 4, 97,
        49, 26, 58, 59, 76, 29, 55, 1, 41, 8, 74, 7, 30, 34, 43, 82, 50, 22, 84, 88, 83, 69, 3, 6, 52, 98
    )

    private val questions = listOf(
        // Cotilleos del corazón
        Question(
            "¿Qué famoso actor español se rumorea que está saliendo con una reconocida cantante internacional?",
            listOf(
                "Javier Bardem",
                "Miguel Ángel Silvestre",
                "Antonio Banderas",
                "Mario Casas"
            ),
            2
        ),
        Question(
            "¿Cuál de estas famosas presentadoras de televisión españolas está embarazada?",
            listOf(
                "Ana Rosa Quintana",
                "María Teresa Campos",
                "Cristina Pedroche",
                "Paz Padilla"
            ),
            0
        ),

        // Acertijos/adivinanzas
        Question(
            "¿Qué animal camina en la mañana a cuatro patas, al mediodía a dos patas y en la noche a tres patas?",
            listOf(
                "El perro",
                "El hombre",
                "La serpiente",
                "El gato"
            ),
            1
        ),

        // Problemas de lógica
        Question(
            "Si todos los gatos son animales y algunos animales son perros, ¿qué se puede concluir?",
            listOf(
                "Todos los perros son gatos",
                "Algunos perros son gatos",
                "Todos los gatos son perros",
                "Algunos gatos son perros"
            ),
            3
        ),

        // Problemas de matemáticas sencillos/medios
        Question(
            "¿Cuanto cuestan siete sardinas y media a euro y medio la sardina y media?",
            listOf(
                "10.25 euros",
                "10.75 euros",
                "11.50 euros",
                "11.25 euros"
            ),
            3
        ),

        // Política
        Question(
            "¿Quién fue el primer presidente de la democracia española?",
            listOf(
                "Adolfo Suárez",
                "Felipe González",
                "Juan Carlos I",
                "Francisco Franco"
            ),
            0
        ),

        // Economía
        Question(
            "¿Cuál es la moneda oficial de España?",
            listOf(
                "Libra",
                "Euro",
                "Dólar",
                "Peso"
            ),
            1
        ),


        // Fútbol
        Question(
            "¿Cuál es el equipo de fútbol más laureado de España?",
            listOf(
                "Real Madrid",
                "FC Barcelona",
                "Atlético de Madrid",
                "Sevilla FC"
            ),
            0
        ),


        Question(
            "Tiene un ojo y no puede ver, tiene un oído y no puede oír, tiene una boca pero no puede hablar. ¿Qué es?",
            listOf(
                "Una aguja",
                "Una cerradura",
                "Una caja fuerte",
                "Un libro"
            ),
            0
        ),

        // Problemas de lógica
        Question(
            "En una habitación hay tres interruptores. Cada uno de ellos enciende una bombilla en otra habitación. Si solo puedes ingresar a la habitación de las bombillas una vez, ¿cómo puedes saber qué interruptor enciende cada una?",
            listOf(
                "Enciendes uno a uno y esperas a que alguien te diga cuál es",
                "Enciendes uno, lo apagas y enciendes otro",
                "Enciendes los tres al mismo tiempo",
                "No es posible saberlo"
            ),
            1
        ),

        // Problemas de matemáticas sencillos/medios
        Question(
            "Supongamos que un equipo de fútbol gana el 40% de los partidos que juega, empata el 25% y el resto de los partidos se considera indefinido, es decir, no sabemos si los pierde o los empata. ¿Cual es el porcentaje de partidos indefinidos?",
            listOf(
                "20%",
                "30%",
                "0% (Indefinido)",
                "5%"
            ),
            2
        ),

        // Problemas de matemáticas sencillos/medios
        Question(
            "¿Con cuanta gente puede hablar Raquel desde el parque hasta las herrerias",
            listOf(
                "15.000 personas",
                "20.000 personas",
                "40.000 personas",
                "Con más que planetas se han encontrado"
            ),
            3
        ),

        // Política
        Question(
            "¿Cuál es el partido político actualmente en el poder en España?",
            listOf(
                "Partido Popular",
                "PSOE (Partido Socialista Obrero Español)",
                "Unidas Podemos",
                "Ciudadanos"
            ),
            1
        ),

        // Economía
        Question(
            "¿Qué ciudad española es conocida como el centro financiero y económico del país?",
            listOf(
                "Barcelona",
                "Valencia",
                "Madrid",
                "Sevilla"
            ),
            2
        ),


        // Fútbol
        Question(
            "¿Cuál es el máximo goleador de la selección española de fútbol?",
            listOf(
                "Raúl González",
                "David Villa",
                "Iker Casillas",
                "David Silva"
            ),
            0
        ),

        // Noticias de Ejea de los Caballeros
        Question(
            "¿Qué rios bañan las cinco villas?",
            listOf(
                "Arba de luesia y arba de biel",
                "Arba de luesia, arba de biel y ebro.",
                "Arba de luesia, arba de biel y riguel.",
                "Arba de luesia, arba de biel, ebro y riguel"
            ),
            2
        ),   // Gastronomía española
        Question(
            "¿Cuál es el plato típico de la gastronomía española compuesto por arroz, mariscos y azafrán?",
            listOf(
                "Paella",
                "Tortilla de patatas",
                "Gazpacho",
                "Fabada"
            ),
            0
        ),

        Question(
            "¿Qué bebida alcohólica de origen español se obtiene a partir de la destilación del vino?",
            listOf(
                "Cerveza",
                "Whisky",
                "Ron",
                "Brandy"
            ),
            3
        ),

        Question(
            "¿Cuál es el famoso embutido español hecho a base de carne de cerdo y especias, que se cura y se consume en rodajas finas?",
            listOf(
                "Chorizo",
                "Salchicha",
                "Mortadela",
                "Jamón"
            ),
            3
        ),

        // Definiciones complejas de palabras
        Question(
            "¿Qué es la filosofía?",
            listOf(
                "El estudio de las estrellas",
                "El arte de la pintura",
                "La reflexión sobre la naturaleza del conocimiento y la realidad",
                "La práctica de la medicina"
            ),
            2
        ),

        Question(
            "¿Qué es la epistemología?",
            listOf(
                "El estudio de la historia",
                "El estudio de la mente humana",
                "El estudio de los fenómenos naturales",
                "El estudio del conocimiento y la forma en que se adquiere"
            ),
            3
        ),

        Question(
            "¿Qué es la equidad?",
            listOf(
                "La capacidad de hablar varios idiomas",
                "La cualidad de ser justo y imparcial",
                "La habilidad para resolver problemas matemáticos",
                "La destreza en el uso de las herramientas"
            ),
            1
        ),

        // Sinónimos y antónimos de palabras
        Question(
            "Sinónimo de 'alegría'",
            listOf(
                "Tristeza",
                "Rabia",
                "Sorpresa",
                "Felicidad"
            ),
            3
        ),

        Question(
            "Antónimo de 'amplio'",
            listOf(
                "Pequeño",
                "Estrecho",
                "Redondo",
                "Alto"
            ),
            1
        ),

        Question(
            "Sinónimo de 'cansado'",
            listOf(
                "Enérgico",
                "Agotado",
                "Tranquilo",
                "Divertido"
            ),
            1
        ),

        // Deportes
        Question(
            "¿Quién es el futbolista español con más títulos de la Liga de Campeones?",
            listOf(
                "Sergio Ramos",
                "Xavi Hernández",
                "Andrés Iniesta",
                "Iker Casillas"
            ),
            3
        ),

        // Acertijos y adivinanzas
        Question(
            "Tengo ciudades, pero no casas. Tengo montañas, pero no árboles. Tengo ríos, pero no agua. ¿Qué soy?",
            listOf(
                "Un mapa",
                "Un libro",
                "Un jardín",
                "Un coche"
            ),
            0
        ),
        Question(
            "Si me nombras, ya no existo. ¿Quién soy?",
            listOf(
                "El silencio",
                "La sombra",
                "El amor",
                "El viento"
            ),
            0
        ),

        Question(
            "¿Cuál es el resultado de la siguiente operación matemática: 5 + 7 - 2 x 3?",
            listOf(
                "8",
                "10",
                "6",
                "14"
            ),
            2
        ),

        Question(
            "¿Cuál es el significado de la palabra 'espléndido'?",
            listOf(
                "Feo",
                "Triste",
                "Bonito",
                "Asqueroso"
            ),
            2
        ),
        Question(
            "Cuatro personas necesitan cruzar un puente oscuro de noche. Solo tienen una linterna y el puente es lo suficientemente ancho como para que solo dos personas crucen juntas. Cada persona tiene una velocidad diferente para cruzar el puente: una puede cruzarlo en 1 minuto, otra en 2 minutos, otra en 5 minutos y la última en 10 minutos. Cuando dos personas cruzan juntas, deben moverse al ritmo de la persona más lenta. ¿Cuál es el tiempo mínimo necesario para que todas las personas crucen el puente?",
            listOf(
                "12 minutos",
                "14 minutos",
                "17 minutos",
                "20 minutos"
            ),
            2
        ),
        Question(
            "En una isla, hay un sombrero negro y un sombrero blanco escondidos en un baúl. Hay dos personas en la isla y ambos están al tanto de los sombreros y de que uno de ellos es blanco y el otro es negro. Sin embargo, no pueden verse a sí mismos ni pueden comunicarse entre ellos. El dueño del sombrero blanco debe levantar la mano si está seguro de que su sombrero es blanco. Después de un tiempo, uno de ellos levanta la mano. ¿Cómo sabe la persona si su sombrero es blanco o negro?",
            listOf(
                "Si la otra persona tenía un sombrero blanco, no hubiera levantado la mano",
                "Si la otra persona tenía un sombrero negro, hubiera levantado la mano",
                "Si la otra persona tenía un sombrero negro, no hubiera levantado la mano",
                "No hay suficiente información para determinarlo"
            ),
            2
        ),
        Question(
            "En un pueblo, hay dos barberos. Uno tiene el cabello perfectamente arreglado y el otro tiene el cabello desaliñado y descuidado. Si vas al barbero que tiene el cabello perfectamente arreglado, ¿quién te arreglará el cabello?",
            listOf(
                "El barbero con el cabello arreglado",
                "El barbero con el cabello desaliñado",
                "Ambos barberos",
                "Ninguno de los barberos"
            ),
            3
        ),
        Question(
            "Tienes dos cuerdas y un encendedor. Cada una de las cuerdas tarda una hora en quemarse por completo, pero no se queman de manera uniforme, por lo que es difícil medir el tiempo exacto. ¿Cómo puedes medir exactamente 45 minutos?",
            listOf(
                "Enciende ambas cuerdas al mismo tiempo",
                "Enciende una cuerda y cuando esté a la mitad, enciende la otra cuerda",
                "Enciende una cuerda y cuando esté a punto de apagarse, enciende la otra cuerda",
                "No es posible medir exactamente 45 minutos"
            ),
            1
        ),

        Question(
            "¿Quién pintó la Mona Lisa?",
            listOf("Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Michelangelo"),
            0
        ),
        Question(
            "¿En qué período artístico se desarrolló el Renacimiento?",
            listOf("Barroco", "Romanticismo", "Renacimiento", "Realismo"),
            2
        ),
        Question(
            "¿Cuál de las siguientes obras es conocida por su técnica de puntillismo?",
            listOf(
                "La persistencia de la memoria",
                "La noche estrellada",
                "La gran ola de Kanagawa",
                "Un domingo en la tarde en la isla de la Grande Jatte"
            ),
            3
        ),
        Question(
            "¿Quién es el autor de la escultura 'El David'?",
            listOf("Donatello", "Auguste Rodin", "Miguel Ángel", "Bertel Thorvaldsen"),
            2
        ),
        Question(
            "¿Cuál de las siguientes corrientes artísticas se caracteriza por el uso de colores vivos y formas exageradas?",
            listOf("Expresionismo", "Impresionismo", "Cubismo", "Fauvismo"),
            3
        ),

        Question("¿Quién escribió la obra 'Cien años de soledad'?", listOf("Gabriel García Márquez", "Mario Vargas Llosa", "Julio Cortázar", "Pablo Neruda"), 0)
        ,Question("¿Cuál de las siguientes palabras no es un adjetivo?", listOf("Correr", "Feliz", "Triste", "Rápido"), 0)
        ,Question("¿Cuál es el plural de 'mamá'?", listOf("Mamás", "Mámas", "Mamas", "Mamass"), 0)
            ,Question("¿Cuál es el autor de la pintura 'La noche estrellada'?", listOf("Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh", "Salvador Dalí"), 2)
            ,Question("¿Cuál es el significado de la palabra 'efímero'?", listOf("Largo", "Permanente", "Duradero", "Breve"), 3)
            ,Question("¿Cuál de las siguientes novelas fue escrita por Miguel de Cervantes Saavedra?", listOf("Don Quijote de la Mancha", "La metamorfosis", "Cien años de soledad", "1984"), 0)
            ,Question("¿Cuál de los siguientes idiomas es una lengua romance?", listOf("Ruso", "Alemán", "Italiano", "Japonés"), 2)
            ,Question("¿Qué famoso dramaturgo escribió 'Romeo y Julieta'?", listOf("William Shakespeare", "Oscar Wilde", "Anton Chejov", "Tennessee Williams"), 0)
            ,Question("¿Cuál de las siguientes obras pertenece al género del teatro?", listOf("La Odisea", "El Principito", "1984", "Hamlet"), 3)
            ,Question("¿Cuál es el autor de la escultura 'El Pensador'?", listOf("Auguste Rodin", "Michelangelo", "Pablo Picasso", "Donatello"), 0)
        ,Question("¿Quién dirigió la película 'El Padrino'?", listOf("Martin Scorsese", "Steven Spielberg", "Francis Ford Coppola", "Alfred Hitchcock"), 2)
        ,Question("¿En qué año se estrenó la primera película de 'Star Wars'?", listOf("1977", "1983", "1999", "2005"), 0)
        ,Question("¿Cuál de los siguientes actores interpretó a James Bond en 'Casino Royale'?", listOf("Daniel Craig", "Pierce Brosnan", "Sean Connery", "Roger Moore"), 0)
        ,Question("¿Quién es el director de la serie de televisión 'Breaking Bad'?", listOf("Vince Gilligan", "David Chase", "Matthew Weiner", "Aaron Sorkin"), 0)
        ,Question("¿Cuál de las siguientes películas fue dirigida por Christopher Nolan?", listOf("Pulp Fiction", "The Matrix", "Inception", "The Avengers"), 2)

        ,Question("¿Cuántos continentes hay en el mundo?", listOf("4", "5", "8", "7"), 3),
        Question("¿Cuál es el metal más ligero?", listOf("Oro", "Plata", "Aluminio", "Cobre"), 2),
        Question(
            "¿Cuál es el planeta más grande del sistema solar?",
            listOf("Mercurio", "Júpiter", "Tierra", "Venus"),
            1
        ),
        Question(
            "¿Cuál es el río más largo del mundo?",
            listOf("Nilo", "Amazonas", "Yangtsé", "Mississippi"),
            0
        ),
        Question(
            "¿Cuál es la montaña más alta del mundo?",
            listOf("Monte Everest", "K2", "Monte McKinley", "Monte Kilimanjaro"),
            0
        ),
        Question(
            "¿Cuál es el nombre de nuestro sistema solar?",
            listOf("Vía Láctea", "Andrómeda", "Sistema Solar", "Centaurus"),
            2
        ),
        Question(
            "¿Cuál es el planeta más cercano al Sol?",
            listOf("Venus", "Mercurio", "Marte", "Júpiter"),
            1
        ),
        Question(
            "¿Cuál es la estrella más cercana a la Tierra?",
            listOf("Sirio", "Alfa Centauri", "Próxima Centauri", "Betelgeuse"),
            2
        ),
        Question(
            "¿Qué es un agujero negro?",
            listOf(
                "Una estrella muy brillante",
                "Un planeta sin atmósfera",
                "Un cuerpo celeste que emite luz propia",
                "Una región del espacio con una gravedad extrema"
            ),
            3
        ),
        Question(
            "¿Cuál es la galaxia más cercana a la nuestra?",
            listOf(
                "Galaxia de Andrómeda",
                "Galaxia del Sombrero",
                "Galaxia del Remolino",
                "Galaxia del Triángulo"
            ),
            0
        ),
        Question(
            "¿Quién compuso la sinfonía No. 9 en Re menor, Op. 125, también conocida como 'Coral'?",
            listOf(
                "Ludwig van Beethoven",
                "Wolfgang Amadeus Mozart",
                "Johann Sebastian Bach",
                "Franz Schubert"
            ),
            0
        ),
        Question(
            "¿Qué instrumento musical se toca soplando en su embocadura y pulsando sus teclas?",
            listOf("Violín", "Guitarra", "Flauta", "Piano"),
            2
        ),
        Question(
            "¿Cuál de los siguientes géneros musicales es originario de Jamaica?",
            listOf("Jazz", "Reggae", "Blues", "Salsa"),
            1
        ),
        Question(
            "¿Qué tipo de instrumento es una trompeta?",
            listOf("Percusión", "Cuerda", "Viento", "Teclado"),
            2
        ),
        Question(
            "¿Cuál de los siguientes compositores es conocido por su música de ballet 'El lago de los cisnes'?",
            listOf(
                "Sergei Prokofiev",
                "Igor Stravinsky",
                "Piotr Ilyich Tchaikovsky",
                "Claude Debussy"
            ),
            2
        ),
        Question(
            "¿Cuál es el océano más grande del mundo?",
            listOf("Océano Pacífico", "Océano Atlántico", "Océano Índico", "Océano Ártico"),
            0
        ),
        Question(
            "¿Cuál es el país más poblado del mundo?",
            listOf("India", "Estados Unidos", "China", "Rusia"),
            0
        ),
        Question(
            "¿En qué año ocurrió la Revolución Francesa?",
            listOf("1776", "1789", "1812", "1848"),
            1
        ),
        Question(
            "¿Cuál es la capital de Australia?",
            listOf("Sídney", "Melbourne", "Brisbane", "Canberra"),
            3
        ),
        Question(
            "¿Cuál es la moneda oficial de Japón?",
            listOf("Yuan", "Rupia", "Yen", "Dólar"),
            2
        ),
        Question(
            "¿Cuál es el río más largo de América del Sur?",
            listOf("Amazonas", "Mississippi", "Orinoco", "Paraná"),
            0
        ),
        Question(
            "¿Cuál es la cadena montañosa más larga del mundo?",
            listOf("Andes", "Himalayas", "Alpes", "Rocosas"),
            0
        ),
        Question(
            "¿Cuál de las siguientes ciudades es la capital de España?",
            listOf("Barcelona", "Valencia", "Madrid", "Sevilla"),
            2
        ),
        Question(
            "¿En qué país se encuentra la ciudad de Toronto?",
            listOf("Estados Unidos", "Canadá", "México", "Australia"),
            1
        ),
        Question(
            "¿Cuál de las siguientes islas es conocida como 'La isla de las especias'?",
            listOf("Borneo", "Madagascar", "Sumatra", "Zanzíbar"),
            2
        ),


//TODO: hasta aquí están comprobadas
        Question(
            "Tienes tres cajas. Una de las cajas contiene solo manzanas, otra contiene solo naranjas y la tercera contiene tanto manzanas como naranjas, pero la etiqueta en cada caja está equivocada. Sin abrir ninguna caja ni mirar dentro, ¿cómo puedes etiquetar correctamente cada caja?",
            listOf(
                "Abre la caja etiquetada como 'manzanas y naranjas'. Si contiene manzanas, esa debe ser la caja de las manzanas. Si contiene naranjas, esa debe ser la caja de las naranjas. Luego, cambia las etiquetas de las otras dos cajas",
                "Elige una fruta de la caja etiquetada como 'manzanas y naranjas'. Si es una manzana, esa debe ser la caja de las manzanas. Si es una naranja, esa debe ser la caja de las naranjas. Luego, cambia las etiquetas de las otras dos cajas",
                "No es posible etiquetar correctamente las cajas sin abrir ninguna",
                "Debes abrir las cajas y mirar dentro para etiquetarlas correctamente"
            ),
            0
        ),
        Question(
            "En un grupo de personas, hay dos hermanos gemelos. Uno siempre dice la verdad y el otro siempre miente. No sabes cuál es cuál. ¿Qué única pregunta puedes hacerle a uno de los hermanos para saber quién es el que siempre dice la verdad?",
            listOf(
                "¿Cuál es tu nombre?",
                "¿Tú eres el que siempre dice la verdad?",
                "¿Tu hermano es el que siempre miente?",
                "No hay ninguna pregunta que pueda revelar quién es el que siempre dice la verdad"
            ),
            1
        ),
        Question(
            "Hay tres amigos: Juan, Pedro y Luis. Uno de ellos siempre dice la verdad, otro siempre miente y el tercero a veces dice la verdad y a veces miente. Juan dice que Pedro siempre miente. Pedro dice que Luis siempre dice la verdad. ¿Quién siempre dice la verdad?",
            listOf(
                "Juan",
                "Pedro",
                "Luis",
                "No se puede determinar"
            ),
            2
        ),
        Question(
            "Tiene nombre de flor, pero no es una flor. Tiene hojas, pero no es un árbol. Tiene cara, pero no es una persona. ¿Qué es?",
            listOf(
                "La margarina",
                "La esponja",
                "El sol",
                "La mariposa"
            ),
            0
        ),
        Question(
            "¿Sabe usted qué es lo que quiero?",
            listOf(
                "Montarme en tu velero",
                "La tarjeta del hormiguero",
                "Mi polla de sombrero",
                "500 euros"
            ),
            1
        ),
        Question(
            "Cuanto más intentas agarrarme, más rápido me escapo. ¿Qué soy?",
            listOf(
                "El tiempo",
                "El viento",
                "Un pez",
                "El miedo"
            ),
            0
        ),
        Question(
            "Soy ligero como una pluma, pero incluso el hombre más fuerte no puede sostenerme durante mucho tiempo. ¿Qué soy?",
            listOf(
                "Un suspiro",
                "Un pensamiento",
                "Una ilusión",
                "Una nube"
            ),
            0
        ),
        Question(
            "Me rompes sin tocarme, sin moverme me mueves. ¿Qué soy?",
            listOf(
                "Un secreto",
                "El corazón",
                "Un espejo",
                "El silencio"
            ),
            3
        ),
        Question(
            "Sin mí, la vida es imposible. Pero si estoy en exceso, la muerte también es una posibilidad. ¿Qué soy?",
            listOf(
                "El agua",
                "El aire",
                "La luz",
                "El amor"
            ),
            0
        ),
        Question(
            "Siempre estoy delante de ti, pero nunca me puedes alcanzar. ¿Qué soy?",
            listOf(
                "El futuro",
                "El sol",
                "Un sueño",
                "La sombra"
            ),
            3
        ),
        Question(
            "Soy pequeño como una semilla, pero mi sombra puede cubrir todo un campo. ¿Qué soy?",
            listOf(
                "El amor",
                "La esperanza",
                "Un pensamiento",
                "Un árbol"
            ),
            3
        ),
        Question(
            "Tengo un mar sin agua, una jungla sin árboles y un desierto sin arena. ¿Qué soy?",
            listOf(
                "Un planeta",
                "Una ilusión",
                "Un espejismo",
                "Un sueño"
            ),
            2
        ),
        Question(
            "Siempre estoy en movimiento, pero nunca llego a ningún lado. ¿Qué soy?",
            listOf(
                "El tiempo",
                "El viento",
                "Un río",
                "La luz"
            ),
            1
        ),
        Question(
            "Si me nombras, dejo de existir. ¿Qué soy?",
            listOf(
                "El silencio",
                "La oscuridad",
                "El olvido",
                "Un sueño"
            ),
            0
        ),
        Question(
            "¿Cuál de las siguientes opciones describe correctamente la segunda ley de Newton?",
            listOf(
                "La aceleración de un objeto es directamente proporcional a la fuerza aplicada sobre él y e inversamente proporcional a su masa.",
                "La fuerza aplicada sobre un objeto es igual al producto de su masa por la aceleración que experimenta.",
                "La fuerza aplicada sobre un objeto es inversamente proporcional a su aceleración.",
                "La aceleración de un objeto es inversamente proporcional a la fuerza aplicada sobre él y directamente proporcional a su masa."
            ),
            0
        ),
        Question(
            "¿Cuál de las siguientes ecuaciones representa la ley de la conservación de la energía?",
            listOf(
                "E = mc^2",
                "F = ma",
                "P = mgh",
                "V = IR"
            ),
            2
        ),
        Question(
            "Un objeto se lanza verticalmente hacia arriba. ¿Cuál de las siguientes afirmaciones es verdadera sobre su aceleración en la parte más alta de su trayectoria?",
            listOf(
                "La aceleración es positiva.",
                "La aceleración es negativa.",
                "La aceleración es cero.",
                "La aceleración varía según el objeto."
            ),
            1
        ),
        Question(
            "¿Cuál de las siguientes afirmaciones describe correctamente la ley de la conservación del momento lineal?",
            listOf(
                "La suma de los momentos lineales de los objetos en un sistema se conserva si no hay fuerzas externas.",
                "La suma de los momentos lineales de los objetos en un sistema se conserva si no hay fuerzas internas.",
                "La suma de los momentos lineales de los objetos en un sistema se conserva siempre, independientemente de las fuerzas externas o internas.",
                "La suma de los momentos lineales de los objetos en un sistema se conserva solo si los objetos están en reposo."
            ),
            0
        ),
        Question(
            "Un objeto se mueve a lo largo de una trayectoria circular con velocidad constante. ¿Cuál de las siguientes afirmaciones es verdadera sobre la aceleración del objeto?",
            listOf(
                "La aceleración es nula.",
                "La aceleración es siempre dirigida hacia el centro de la trayectoria.",
                "La aceleración es siempre perpendicular a la velocidad.",
                "La aceleración varía según el objeto y la velocidad."
            ),
            0
        )


        // Agrega más preguntas según sea necesario
    )

    private var currentQuestionIndex = 0
    private var threeDaysInMillis = 3 * 24 * 60 * 60 * 1000 // 3 días en milisegundos
    private var multiploSudoku=150
    private var multiploSimon =7


    //Que se active en la contraseña con algun codigo
    private var alwaysComodines = false;

    //Que se active en la contraseña con algun codigo
    private var alwaysSeeAnswer = false;

    var contadorPulsaciones = 0
    val umbralPulsaciones = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)

        mediaPlayerAbucheo = MediaPlayer.create(this, R.raw.abucheo)
        mediaPlayerCoin = MediaPlayer.create(this, R.raw.coin)

        val numero=questions.size

        maxScoreTexView = findViewById(R.id.maxScoreTexView)

        textInfoMoney = findViewById(R.id.maxDinerosTexView)

        scoreTexView = findViewById(R.id.scoreTexView)
        scoreTexView.setOnClickListener {
            contadorPulsaciones++
            if (contadorPulsaciones == umbralPulsaciones) {
                contadorPulsaciones = 0
                llamarMetodo()
            }
        }

        questionTextView = findViewById(R.id.questionTextView)

        option1Button = findViewById(R.id.option1Button)
        option2Button = findViewById(R.id.option2Button)
        option3Button = findViewById(R.id.option3Button)
        option4Button = findViewById(R.id.option4Button)

        comodin1Button = findViewById(R.id.comodin1Button)
        comodin2Button = findViewById(R.id.comodin2Button)
        comodin3Button = findViewById(R.id.comodin3Button)

        comodin1Button.setOnClickListener { showAlertDialogCardOne() }
        comodin2Button.setOnClickListener { showAlertDialogCardTwo() }
        comodin3Button.setOnClickListener { showAlertDialogCardThree() }

        option1Button.setOnClickListener { checkAnswer(0) }
        option2Button.setOnClickListener { checkAnswer(1) }
        option3Button.setOnClickListener { checkAnswer(2) }
        option4Button.setOnClickListener { checkAnswer(3) }

        showInitScore()
        setComodines()
        displayQuestion()
    }

    private fun llamarMetodo() {
        alwaysComodines = true;
        alwaysSeeAnswer = true;
        Toast.makeText(this, "Hacks activados. Respuestas correctas + comodines", Toast.LENGTH_SHORT).show()
        resaltarBotonRespuesta()
    }

    private fun setComodines() {
        activateComodin(1)
        activateComodin(2)
        activateComodin(3)

        if (!alwaysComodines) {

            //buscar en sharedPreferences que comodines se ha usado y cambiar las imagenes de los que correspondan
            val sharedPreferences = getSharedPreferences("TrivialWedding", Context.MODE_PRIVATE)
            val now=System.currentTimeMillis()

            if (sharedPreferences.getBoolean("CardOne", false)) {
                if (now - (sharedPreferences.getLong("CardOneLastDate", 0)) < threeDaysInMillis) {
                    // comodin usado y sigue activo. Se fija
                    deactivateComodin(1)
                    castigoOne()
                }
            }

            if (sharedPreferences.getBoolean("CardTwo", false)) {
                if (now - (sharedPreferences.getLong("CardTwoLastDate", 0)) < threeDaysInMillis) {
                    // comodin usado y sigue activo. Se fija
                    deactivateComodin(2)
                    castigoTwo()
                }
            }
            if (sharedPreferences.getBoolean("CardThree", false)) {
                if (now - (sharedPreferences.getLong("CardThreeLastDate", 0)) < threeDaysInMillis) {
                    // comodin usado y sigue activo. Se fija
                    deactivateComodin(3)
                    castigoThree()
                }
            }

        }
    }

    private fun castigoOne(){
        //Reordenar los primeros n números de la lista orden de manera aleatoria, manteniendo el resto de los números en su posición original.
        // n=máximaPutuacionObtenida
        val primerosNumeros = orden.subList(0, ultimaPuntuacion)
        val restantes = orden.subList(ultimaPuntuacion, orden.size)
        val numerosAleatorios = primerosNumeros.shuffled()
        orden = numerosAleatorios + restantes

        //TODO: Pensar como hacer para que tbn sean aleatorias las respuestas en los botones
    }

    private fun castigoTwo(){
         multiploSudoku=15
    }

    private fun castigoThree(){
        multiploSudoku=15
    }

    private fun displayQuestion() {

            val elementoQueToca = orden[currentQuestionIndex]
            val question = questions[elementoQueToca]
            questionTextView.text = question.question
            option1Button.text = question.options[0]
            option2Button.text = question.options[1]
            option3Button.text = question.options[2]
            option4Button.text = question.options[3]

            option1Button.setBackgroundResource(android.R.drawable.btn_default)
            option2Button.setBackgroundResource(android.R.drawable.btn_default)
            option3Button.setBackgroundResource(android.R.drawable.btn_default)
            option4Button.setBackgroundResource(android.R.drawable.btn_default)

        if (alwaysSeeAnswer){
            resaltarBotonRespuesta()
        }

        if(alwaysComodines){
            setComodines()
        }

        if (currentQuestionIndex==2){
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("Video", 1)
            startActivity(intent)
        }
        if (currentQuestionIndex==4){
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("Video", 1)
            startActivity(intent)
        }
        if (currentQuestionIndex==6){
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("Video", 1)
            startActivity(intent)
        }
        if (currentQuestionIndex==8){
            val intent = Intent(this, VideoActivity::class.java)
            intent.putExtra("Video", 1)
            startActivity(intent)
        }

        if (currentQuestionIndex!=0 && esMultiploDe(currentQuestionIndex)){
            val intent = Intent(this, SudokuActivity::class.java)
            intent.putExtra("Dificultad", currentQuestionIndex/multiploSudoku)
            startActivity(intent)
        }

        if (currentQuestionIndex!=0 && esMultiploDeSimon(currentQuestionIndex)){
            val intent = Intent(this, SimonGameActivity::class.java)
            intent.putExtra("Dificultad", currentQuestionIndex/multiploSimon)
            startActivity(intent)
        }

    }

    private fun esMultiploDe(numero: Int): Boolean {
        return numero % multiploSudoku == 0
    }

    private fun esMultiploDeSimon(numero: Int): Boolean {
        return numero % multiploSimon == 0
    }



    private fun showInitScore() {
        // Guardar la última puntuación
        val sharedPreferences = getSharedPreferences("TrivialWedding", Context.MODE_PRIVATE)
        ultimaPuntuacion = sharedPreferences.getInt("ultima_puntuacion", 0)
        maxScoreTexView.text = ultimaPuntuacion.toString()
    }

    private fun saveScore() {
        // Guardar la última puntuación
        val sharedPreferences = getSharedPreferences("TrivialWedding", Context.MODE_PRIVATE)
        maxScoreTexView.text = ultimaPuntuacion.toString()
        val editor = sharedPreferences.edit()
        editor.putInt("ultima_puntuacion", currentQuestionIndex)
        editor.apply()
    }

    private fun checkAnswer(selectedOptionIndex: Int) {
        val elementoQueToca=orden[currentQuestionIndex]
        val question = questions[elementoQueToca]
        if (selectedOptionIndex == question.correctOptionIndex) {
            // User answered correctly

            //Reproduce audio aqui
            mediaPlayerCoin?.start()

            //Corremos indice de preguntas
            currentQuestionIndex++

            //Actualiza dinero ganado
            updateCoins()

            //check si estamos en record
            if (currentQuestionIndex > ultimaPuntuacion) {
                saveScore()
            }

            //Actualiza preguntas acertadas
            scoreTexView.text=currentQuestionIndex.toString()

            //Chequeamos si ya ha llegado al final
            if (currentQuestionIndex < questions.size) {
                displayQuestion()
            } else {
                // User answered all questions correctly
                val intent = Intent(this, MainFinalScreen::class.java)
                startActivity(intent)
            }
        } else {
            // User answered incorrectly
            //Reproduce audio aqui
            mediaPlayerAbucheo?.start()

            showAlertDialog()
        }
    }

    private fun updateCoins() {
        val dineros=(currentQuestionIndex)*4
        if(dineros==0){
            textInfoMoney.text=""
        }else{
            textInfoMoney.text="¡Llevas "+dineros+" euracos ganados!"
        }
    }

    private fun showAlertDialog() {
        // Crear un AlertDialog
        val alertDialog = AlertDialog.Builder(this)

        // Establecer el título y el mensaje del diálogo
        alertDialog.setTitle("¡¡¡¡¡QUE PUTADA!!!!")
        alertDialog.setMessage("JAJAJA!!! Respuesta incorrecta. Intentalo otra vez xD ")

        // Establecer el botón de aceptar y su acción
        alertDialog.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, _: Int ->
            // Acción a realizar al hacer clic en el botón de aceptar
            dialogInterface.dismiss() // Cerrar el diálogo
            finishAffinity()
        }

        // Mostrar el diálogo
        alertDialog.show()
    }

    private fun showConsecuencesDialog(option : Int) {
        // Crear un AlertDialog
        val alertDialog = AlertDialog.Builder(this)

        // Establecer el título y el mensaje del diálogo
        alertDialog.setTitle("¡Ahora... a pagarlo!")
        if (option==1){
            alertDialog.setMessage("Vienen las consecuencias por utilizar el comodin Bañera: A partir de ahora, las preguntas que ya habeís respondido, cuando volváis a intentarlo, saldrán en distinto orden.")
        }else if (option==2){
            alertDialog.setMessage("Vienen las consecuencias por utilizar el comodin Las Eras: A partir de ahora, cada 15 preguntas, aparecerá un sudoku que tendréis que resolver para continuar.")
        }else if (option==3){
            alertDialog.setMessage("Vienen las consecuencias por utilizar el comodin Habanera: A partir de ahora ... sinceramente, no se nos ocurría que más podíamos hacer para complicar el juego, así que os lo perdonamos.")
        }

        // Establecer el botón de aceptar y su acción
        alertDialog.setPositiveButton("Aceptar") { dialogInterface: DialogInterface, _: Int ->
            // Acción a realizar al hacer clic en el botón de aceptar
            dialogInterface.dismiss() // Cerrar el diálogo
        }

        // Mostrar el diálogo
        alertDialog.show()
    }

    private fun activateComodin(comodin : Int) {
        if (comodin==1){
            //cambiar el icono de la carta
            comodin1Button.setBackgroundResource(R.drawable.comodinok)
            //desactivar el boton
            comodin1Button.isEnabled = true
            comodin1Button.isClickable = true
        }else
            if (comodin==2){
                //cambiar el icono de la carta
                comodin2Button.setBackgroundResource(R.drawable.comodinok)
                //desactivar el boton
                comodin2Button.isEnabled = true
                comodin2Button.isClickable = true
            }else
                if (comodin==3){
                    //cambiar el icono de la carta
                    comodin3Button.setBackgroundResource(R.drawable.comodinok)
                    //desactivar el boton
                    comodin3Button.isEnabled = true
                    comodin3Button.isClickable = true
                }
    }

    private fun deactivateComodin(comodin : Int) {
        if (comodin==1){
            //cambiar el icono de la carta
            comodin1Button.setBackgroundResource(R.drawable.comodinusado)
            //desactivar el boton
            comodin1Button.isEnabled = false
            comodin1Button.isClickable = false
        }else
        if (comodin==2){
        //cambiar el icono de la carta
            comodin2Button.setBackgroundResource(R.drawable.comodinusado)
            //desactivar el boton
            comodin2Button.isEnabled = false
            comodin2Button.isClickable = false
        }else
        if (comodin==3){
            //cambiar el icono de la carta
            comodin3Button.setBackgroundResource(R.drawable.comodinusado)
            //desactivar el boton
            comodin3Button.isEnabled = false
            comodin3Button.isClickable = false
        }
    }

    private fun showAlertDialogCardOne() {
        // Crear un AlertDialog
        val alertDialog = AlertDialog.Builder(this)

        // Establecer el título y el mensaje del diálogo
        alertDialog.setTitle("Comodín Bañera")
        alertDialog.setMessage("Estás a punto de utilizar el comodín de bañera. La fuente te va a ayudar señalando la respuesta correcta para que la selecciones. ¿Estás seguro?")

        // Establecer el botón de aceptar y su acción
        alertDialog.setPositiveButton("Usarlo") { dialogInterface: DialogInterface, _: Int ->
            // Acción a realizar al hacer clic en el botón de aceptar
            //CambioFormatoComodin
            deactivateComodin(1)
            //resaltar el boton de respuesta
            resaltarBotonRespuesta()
            //mostrar mensaje de que habrá consecuiencias
            showConsecuencesDialog(1)
            //poner en sharedPreferences que se ha usado ya
            val editor = getSharedPreferences("TrivialWedding", Context.MODE_PRIVATE).edit()
            editor.putBoolean("CardOne", true)
            editor.putLong("CardOneLastDate", System.currentTimeMillis())
            editor.apply()

            castigoOne()
        }

        // Establecer el botón de aceptar y su acción
        alertDialog.setNegativeButton("No usar") { dialogInterface: DialogInterface, _: Int ->
            // Acción a realizar al hacer clic en el botón de aceptar
            dialogInterface.dismiss() // Cerrar el diálogo
            //No hacer nada
        }

        // Mostrar el diálogo
        alertDialog.show()
    }

    private fun resaltarBotonRespuesta() {
        //pregunta actual
        val respuesta=questions[orden[currentQuestionIndex]].correctOptionIndex

        if (respuesta==0){
            option1Button.setBackgroundColor(Color.parseColor("#FFAE69"))
        }else if (respuesta==1){
            option2Button.setBackgroundColor(Color.parseColor("#FFAE69"))
        }else if (respuesta==2){
            option3Button.setBackgroundColor(Color.parseColor("#FFAE69"))
        }else if (respuesta==3) {
            option4Button.setBackgroundColor(Color.parseColor("#FFAE69"))
        }
    }
    private fun showAlertDialogCardTwo() {
        // Crear un AlertDialog
        val alertDialog = AlertDialog.Builder(this)

        // Establecer el título y el mensaje del diálogo
        alertDialog.setTitle("Comodín LasEras")
        alertDialog.setMessage("Estás a punto de utilizar el comodín de las eras. La gente del barrio te señalarán la respuesta correcta para que la selecciones. ¿Estás seguro?")

        // Establecer el botón de aceptar y su acción
        alertDialog.setPositiveButton("Usarlo") { dialogInterface: DialogInterface, _: Int ->
            // Acción a realizar al hacer clic en el botón de aceptar
            //CambioFormatoComodin
            deactivateComodin(2)
            //resaltar el boton de respuesta
            resaltarBotonRespuesta()
            //mostrar mensaje de que habrá consecuiencias
            showConsecuencesDialog(2)
            //poner en sharedPreferences que se ha usado ya
            val editor = getSharedPreferences("TrivialWedding", Context.MODE_PRIVATE).edit()
            editor.putBoolean("CardTwo", true)
            editor.putLong("CardTwoLastDate", System.currentTimeMillis())
            editor.apply()

            castigoTwo()
        }

        // Establecer el botón de aceptar y su acción
        alertDialog.setNegativeButton("No usar") { dialogInterface: DialogInterface, _: Int ->
            // Acción a realizar al hacer clic en el botón de aceptar
            dialogInterface.dismiss() // Cerrar el diálogo
            //No hacer nada
        }

        // Mostrar el diálogo
        alertDialog.show()
    }

    private fun showAlertDialogCardThree() {
        // Crear un AlertDialog
        val alertDialog = AlertDialog.Builder(this)

        // Establecer el título y el mensaje del diálogo
        alertDialog.setTitle("Comodín Habanera")
        alertDialog.setMessage("Estás a punto de utilizar el comodín de la habanera. Los ejeanos cantando señalarán la respuesta correcta para que la selecciones. ¿Estás seguro?")

        // Establecer el botón de aceptar y su acción
        alertDialog.setPositiveButton("Usarlo") { dialogInterface: DialogInterface, _: Int ->
            // Acción a realizar al hacer clic en el botón de aceptar
            //CambioFormatoComodin
            deactivateComodin(3)
            //resaltar el boton de respuesta
            resaltarBotonRespuesta()
            //mostrar mensaje de que habrá consecuiencias
            showConsecuencesDialog(3)
            //poner en sharedPreferences que se ha usado ya
            val editor = getSharedPreferences("TrivialWedding", Context.MODE_PRIVATE).edit()
            editor.putBoolean("CardThree", true)
            editor.putLong("CardThreeLastDate", System.currentTimeMillis())
            editor.apply()
        }

        // Establecer el botón de aceptar y su acción
        alertDialog.setNegativeButton("No usar") { dialogInterface: DialogInterface, _: Int ->
            // Acción a realizar al hacer clic en el botón de aceptar
            dialogInterface.dismiss() // Cerrar el diálogo
            //No hacer nada
        }
        // Mostrar el diálogo
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayerAbucheo?.release()
        mediaPlayerAbucheo = null
        mediaPlayerCoin?.release()
        mediaPlayerCoin = null
    }

    data class Question(
        val question: String,
        val options: List<String>,
        val correctOptionIndex: Int
    )
}


