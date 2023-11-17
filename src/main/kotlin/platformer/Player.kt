package platformer
import kotlin.math.sign
import platformer.Direction.*
enum class Direction { U, D, R, L }


//Entity the player controls.
class Player(gameContext: GameContextProvider) : BasicEntity(gameContext) {
    override val width = 25f
    override val height = 50f

    private val friction = 2f
    private val maxXVelocity = 4.5f
    private val maxYVelocity = 100f

    private var runAcceleration = 4.5f
    private var jumpAcceleration = 25f

    fun moveLeft() {
        xVelocity -= runAcceleration
    }

    fun moveRight() {
        xVelocity += runAcceleration
    }

    fun jump() {
        if (isOnSurface) {
            yVelocity += jumpAcceleration
        }
        isOnSurface = false
    }

    fun resetPlayer() {
        position.x = 0f
        position.y = 0f
        isOnSurface = false
        yVelocity = 0f
        xVelocity = 0f
    }

    private fun nextY() {
        if (!isOnSurface)
            yVelocity = if (yVelocity <= maxYVelocity) yVelocity - gravity else maxYVelocity

        position.y -= yVelocity
    }

    private fun nextX() {
        position.x += xVelocity
        if (isOnSurface) {
            xVelocity -= sign(xVelocity) * friction
            if (xVelocity < 3f && xVelocity > -3f)
                xVelocity = 0f
        }
        if (xVelocity !in -maxXVelocity..maxXVelocity)
            xVelocity = maxXVelocity * sign(xVelocity)
    }


    //Applies collision to current object
    private fun doCollisions() {
        val collisions = checkCollisions()
        for ((gameObject, direction) in collisions) {
            gameObject.onCollision(this)
            collisionMethodMap[direction]?.let {
                it(gameObject)
            } ?: run {
                throw Error("Collision direction \"${direction}\" not found in function map")
            }
        }
        if (collisions.size == 0) {
            isOnSurface = false
        }
    }

    override fun update() {
        nextY()
        nextX()
        doCollisions()
    }
}
