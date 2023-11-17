import platformer.GameState
import processing.core.PApplet

class Main : PApplet() {
    private val gameState = GameState(this)


    private val player = gameState.player

    override fun settings() {
        size(gameState.width.toInt(), gameState.height.toInt())
    }

    /*override fun setup() {
        frameRate(5f)
    }*/

    override fun draw() {
        translate(-player.position.x + 200f, 0f)
        background(255)
        drawBlocks()
        drawPlayer()
        gameState.player.update()
    }

    private fun drawPlayer() {
        doKeyPresses()
        fill(255f, 0f, 0f)
        stroke(0f, 0f, 0f)
        rect(player.position.x, player.position.y, player.width, player.height)
    }

    override fun keyPressed() {
        if (gameState.keyPressedMap.containsKey(key))
            gameState.keyPressedMap[key]?.keyPressed = true
    }

    override fun keyReleased() {
        if (gameState.keyPressedMap.containsKey(key))
            gameState.keyPressedMap[key]?.keyPressed = false
    }

    //Runs keyFun for each pressed key.
    private fun doKeyPresses() {
        for ((_, keyPress) in gameState.keyPressedMap) {
            if (keyPress.keyPressed)

                keyPress.keyFun()
        }
    }

    private fun drawBlocks() {
        //stroke(0f, 0f, 0f)
        fill(0f, 0f, 0f)
        for (square in gameState.gameObjects) {
            val squarePos = square.position
            rect(squarePos.x, squarePos.y, square.width, square.height)
        }
    }
}


fun main() {
    PApplet.main("Main")
}
