package platformer

import processing.core.PApplet

//Holds state of key press, and lambda that provides key press function.
data class KeyWithKeyFun(var keyPressed: Boolean = false, var keyFun: () -> Unit)

data class Point(var x: Float, var y: Float) {
    override fun toString(): String = "$x, $y"
}

interface GameContextProvider {
    val gameState: GameState
    val pApplet: PApplet
}

//Holds dependency for game state and render functions.
data class GameContext(override val gameState: GameState, override val pApplet: PApplet) : GameContextProvider

//Holds state of the game.
class GameState(pApplet: PApplet) {

    private val gameContext = GameContext(this, pApplet)
    val width = 1000f
    val height = 600f
    val blockSize = 64f
    val player = Player(gameContext)

    //Holds game objects (Will be changed at some point most likely.)
    val gameObjects = arrayListOf(
        Block(gameContext, Point(0f, 1f)),
        Block(gameContext, Point(6f, 1f)),
        Block(gameContext, Point(7f, 1f)),
        Block(gameContext, Point(10f, 1f)),
        Block(gameContext, Point(4f, 3f))
    )

    init {
        for (i in -20..50) {
            gameObjects.add(Block(gameContext, Point(i.toFloat(), 0f)))
        }
    }

    //Maps each key to current state of key press, and holds a function to execute if key is pressed.
    val keyPressedMap = mapOf(
        'w' to KeyWithKeyFun(keyFun = player::jump),
        'a' to KeyWithKeyFun(keyFun = player::moveLeft),
        'd' to KeyWithKeyFun(keyFun = player::moveRight),
        'r' to KeyWithKeyFun(keyFun = player::resetPlayer),
    )
}
