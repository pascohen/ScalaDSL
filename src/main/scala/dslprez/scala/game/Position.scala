package dslprez.scala.game

/**
 * Position contains position but also turtle
 * orientation
 */
case class Position(x: Int, y: Int, rotation: Int, direction: Direction) {

  def left =  Position(x - 1, y, -90, dslprez.scala.game.left)
  def right = Position(x + 1, y, 90, dslprez.scala.game.right)
  def up =    Position(x, y + 1, 0, dslprez.scala.game.up)
  def down =  Position(x, y - 1, 180, dslprez.scala.game.down) 
  
  def toMapStructure = Map("x"->x,"y"->y,"rotation"->rotation,"direction"->direction.asString)    
}

object Position {
  private def randomPosition(gridSize:Int):(Int,Int) = {
    val random = new java.util.Random
    (1+random.nextInt(gridSize-2),1+random.nextInt(gridSize-2))
  }
    
  private def getValidPosition(gridSize:Int, walls:Set[(Int,Int)]):(Int,Int) = {
    val pos = randomPosition(gridSize)
    if (!walls.contains(pos)) pos
    else getValidPosition(gridSize,walls)
  }
  
  def generateRandomPosition(gridSize:Int, walls:Set[(Int,Int)]):Position = {
    val (x,y) = getValidPosition(gridSize,walls)
    Position(x,y,90,dslprez.scala.game.right)
  }
  
  def generateRandomPositionFromJava(gridSize:Int, walls:Array[Array[Int]]):Position =
    generateRandomPosition(gridSize,toSet(walls))
    
  def getDirection(s:String) = s match {
    case "+x" => dslprez.scala.game.right
    case "-x" => dslprez.scala.game.left
    case "+y" => dslprez.scala.game.up
    case "-y" => dslprez.scala.game.down
    case _ => dslprez.scala.game.up
  }
  
  def getPosition(x: Int, y: Int, rotation: Int, dir:String) = Position(x,y,rotation,getDirection(dir))

}

