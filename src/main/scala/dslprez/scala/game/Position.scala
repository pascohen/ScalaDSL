package dslprez.scala.game

abstract class Position(val x:Int, val y:Int) {
  def toMapStructure:Map[String,Any]
}

/**
 * Position contains position but also turtle
 * orientation
 */
case class OrientedPosition(override val x: Int, override val y: Int, direction: Direction) extends Position(x,y) {

  def left =  OrientedPosition(x - 1, y, dslprez.scala.game.left)
  def right = OrientedPosition(x + 1, y, dslprez.scala.game.right)
  def up =    OrientedPosition(x, y + 1, dslprez.scala.game.up)
  def down =  OrientedPosition(x, y - 1, dslprez.scala.game.down) 
  
  def getRotation = direction match {
    case dslprez.scala.game.left => -90
    case dslprez.scala.game.right => 90
    case dslprez.scala.game.up => 0
    case dslprez.scala.game.down => 180
  }
  
  def toMapStructure = Map("x"->x,"y"->y,"k"->0,"rotation"->getRotation,"direction"->direction.asString)    
}

class KissPosition(p:OrientedPosition) extends OrientedPosition(p.x,p.y,p.direction) {
  override def toMapStructure = Map("x"->x,"y"->y,"k"->1,"rotation"->getRotation,"direction"->direction.asString) 
}

object Position {

  def getDirection(s:String) = s match {
    case "+x" => dslprez.scala.game.right
    case "-x" => dslprez.scala.game.left
    case "+y" => dslprez.scala.game.up
    case "-y" => dslprez.scala.game.down
    case _ => dslprez.scala.game.up
  }
  
  def getPosition(x: Int, y: Int, dir:String) = OrientedPosition(x,y,getDirection(dir))
}

class MeetPosition(x:Int, y:Int) extends Position(x,y) {
    def toMapStructure = Map("x"->x,"y"->y)    
}
