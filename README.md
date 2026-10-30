BLACKJACK 

Aplicación de escritorio del juego de cartas Blackjack, desarrollada en Java 
utilizando JavaFX bajo el patrón de arquitectura Modelo-Vista-Controlador (MVC).

AUTORES

- Carlos Anibal Ilich Ceron del Castillo - Código: 202537097
- Nombre Completo Integrante 2 - Código: Código de estudiante


Institución: Universidad del Valle
Paquete principal: co.edu.univalle.poe.mvc

REQUISITOS Y TECNOLOGÍAS UTILIZADAS

- SDK de Java: Amazon Corretto versión 21.0.13.
- Interfaz gráfica y multimedia: JavaFX (javafx.fxml, javafx.scene, javafx.animation, javafx.scene.media).
- IDE recomendado: IntelliJ IDEA.

ESTRUCTURA DEL PROYECTO (ARQUITECTURA MVC)

El código está organizado separando la lógica de negocio de la interfaz de usuario en los siguientes paquetes:
1. CONTROLADOR (co.edu.univalle.poe.mvc.controller)
- BlackJackController: Gestiona los eventos de la interfaz gráfica, controla el cambio de estados durante la
  partida, reproduce la música de fondo y los efectos de sonido, despliega la ventana de ayuda y ejecuta
  las animaciones al repartir las cartas.


2. MODELO (co.edu.univalle.poe.mvc.model)
- Carta: Representa una carta de la baraja con su respectivo valor y palo, permitiendo identificar
  si es una figura (J, Q, K) o un 4.
- Mazo: Crea y mezcla aleatoriamente las 52 cartas de los 4 palos (P, C, D, T) y las reparte secuencialmente, sin repetir.
- GestorCartas: Administra el mazo y las manos de cartas del jugador y de la CPU, además de calcular el puntaje
  ajustando dinámicamente el valor del As (de 11 a 1) cuando la mano supera los 21 puntos.
- GestorApuesta: Controla el saldo del jugador (iniciando en 1000 fichas), valida que las apuestas sean múltiplos
  de 10 y entrega las ganancias al cierre de cada ronda.
- EvaluadorJugadas: Evalúa la mano en curso y determina el ganador de la ronda contemplando jugadas especiales como
  Blackjack y Veinte y Medio.
- Enumeraciones (EstadoJuego, Jugadas, ResultadoRonda): Definen los estados del turno, la clasificación de las manos y
  los posibles desenlaces de una ronda.


3. VISTA Y RECURSOS (co.edu.univalle.poe.mvc.view / images / audios)
- Archivos FXML para el tablero principal y la ventana emergente de ayuda (BlackJack-vista.fxml y help.fxml).
- Recursos gráficos para las 52 cartas, íconos de interfaz y pistas de audio para ambientación de casino, victoria y derrota.

REGLAS Y CARACTERÍSTICAS PRINCIPALES

1. SISTEMA DE APUESTAS
- El jugador inicia con 1000 fichas.
- Las apuestas deben ser múltiplos de 10 y no pueden exceder el saldo disponible ni ser menores de 0.
- El juego finaliza automáticamente si el jugador se queda con menos de 10 fichas.


2. JUGADAS ESPECIALES
- Blackjack: Obtener 21 puntos con las 2 primeras cartas. Esta jugada paga 2.5 veces la apuesta realizada.
- Veinte y Medio: Combinación especial de 2 cartas conformada por una figura (J, Q, K) y un 4, la cual vence
  a cualquier puntaje menor a 21.


3. LÓGICA DE LA BANCA (CPU)
Durante su turno, la CPU pide cartas de forma automática mientras su puntaje sea menor a 17 y se planta al
alcanzar 17 o más puntos.


5. INTERFAZ INTERACTIVA
- Animaciones de reparto mediante TranslateTransition, FadeTransition y ParallelTransition.
- Descripciones emergentes (Tooltip) con el valor en puntos al pasar el cursor sobre cada carta.
- Control de silencio para la música de fondo.


EJECUCIÓN DEL PROYECTO

1. Clonar o abrir el proyecto en IntelliJ IDEA.
2. Configurar en la estructura del proyecto (Project Structure > Project SDK) el SDK Amazon Corretto 21.0.13.
   (Este paso llegó a generar inconvenientes, para acceder a la version de SDK usado en el proyecto: Ctrl + Alt + Shift + S)
4. Ejecutar la clase principal de la aplicación JavaFX para iniciar la interfaz del juego.
