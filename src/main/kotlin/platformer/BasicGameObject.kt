package platformer

//Standard game object, with position and size
abstract class BasicGameObject(val gameContext: GameContextProvider) : GameObject {
    override val position: Point = Point(0f, 0f)
    val centerPos: Point
        get() {
            val xCenter = position.x + (width / 2)
            val yCenter = position.y + (height / 2)
            return Point(xCenter, yCenter)
        }

    override val getFaceCentre: Map<Direction, () -> Point> = mapOf(
        Direction.U to {  Point(centerPos.x, position.y) },
        Direction.D to { Point(centerPos.x, position.y + height ) },
        Direction.L to { Point(position.x, centerPos.y) },
        Direction.R to { Point(position.x + width, centerPos.y) }
    )

    //Empty methods as I want them callable, but don't need to do anything for some game objects.
    override fun onCollision(entity: Entity) {}
    override fun update() {}
}


open class Block(
    gameContext: GameContext,
    protected open val gridPosition: Point = Point(0f, 0f),
) : BasicGameObject(gameContext) {
    override val width = gameContext.gameState.blockSize
    override val height = gameContext.gameState.blockSize

    override val position
        get() = gridPosToBlockPos(gridPosition)

    private fun gridPosToBlockPos(point: Point): Point =
        Point(
            point.x * gameContext.gameState.blockSize,
            (gameContext.gameState.height - (point.y * gameContext.gameState.blockSize)) - gameContext.gameState.blockSize
        )
}