package platformer


//Game object with changing state like movement.
interface Entity : GameObject {
    var xVelocity: Float
    var yVelocity: Float
    var gravity: Float
}