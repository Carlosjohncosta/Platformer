package platformer
import kotlin.math.abs

//Entity with a few common implementations+
abstract class BasicEntity(gameContext: GameContextProvider)
    : Entity, BasicGameObject(gameContext) {
    override var xVelocity: Float = 0f
    override var yVelocity: Float = 0f
    override var gravity: Float = 1.5f
    open var isOnSurface = false

    open val collisionMethodMap: Map<Direction, (GameObject) -> Unit> = mutableMapOf(
        Direction.U to {
            isOnSurface = true
            position.y = it.getFaceCentre[Direction.U]!!().y - height
            yVelocity = 0f
        },
        Direction.D to {
            position.y = it.getFaceCentre[Direction.D]!!().y
            yVelocity = 0f
        },
        Direction.L to {
            xVelocity = 0f
            position.x = it.position.x - width
        },
        Direction.R to {
            xVelocity = 0f
            position.x = it.position.x + it.width
        }
    )


    //protected abstract val texture: PImage

    //Checks distance between 2 points (returns distance^2 to avoid sqrt operation)
    private fun checkDistance(objectPoint: Point): Float {
        val xDistance = abs(centerPos.x - objectPoint.x)
        val yDistance = abs(centerPos.y - objectPoint.y)
        return xDistance * xDistance + yDistance * yDistance
    }

    //Checks collision between this GameObject and all other loaded objects.
    fun checkCollisions() : MutableList<Pair<GameObject, Direction>> {
        val collidedObjects = mutableListOf<Pair<GameObject, Direction>>()

        for (gameObject in gameContext.gameState.gameObjects) {
            if (gameObject === this)
                continue

            //Checks if this intersects x and y of object we are checking.
            val blockCenter = gameObject.centerPos
            val maxXDistance = (width / 2) + gameObject.width / 2
            val maxYDistance = (height / 2) + gameObject.height / 2
            val xIntersect = abs(centerPos.x - blockCenter.x)
            val yInterscet = abs(centerPos.y - blockCenter.y)
            val doesXIntersect = maxXDistance > xIntersect
            val doesYIntersect = maxYDistance > yInterscet

            if (doesYIntersect && doesXIntersect) {

                //Checks which center of side is closest to center of this object, and returns the closest side.
                val collisionDirection: Direction = gameObject.getFaceCentre.map { (key, value) ->
                    key to checkDistance(value())
                }.minByOrNull {
                    it.second
                }?.first ?: Direction.D

                collidedObjects.add(Pair(gameObject, collisionDirection))
            }
        }

        return collidedObjects
    }
}
