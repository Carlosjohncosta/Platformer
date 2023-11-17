package platformer

//Simple game object, only has position and size.
interface GameObject {
    val width: Float
    val height: Float
    val position: Point
    val getFaceCentre: Map<Direction, () -> Point>
    fun onCollision(entity: Entity)
    fun update()
    //fun draw()
}