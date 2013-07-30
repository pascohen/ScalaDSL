// JSon part
import net.liftweb.json._
import net.liftweb.json.JsonDSL._

// Avoid warning
import scala.language.implicitConversions

// Implicits package scope
package object dslprez {
  implicit def toSteps(i:Int) = Step(i)

  implicit def toJsonValue(p:Position) = ("direction"->p.d.toString)~("x"->p.x)~("y"->p.y)

  implicit class Times(i:Int) {
     def times(c: => Any) = for (_ <- 1 to i) c
  }
}

package dslprez {

// Sealed Directions - could be an Enum
sealed trait Direction
case object left extends Direction
case object right extends Direction
case object up extends Direction
case object down extends Direction

// Steps class mainly for readability
case class Step(i:Int) {
  def steps = this
}

/**
 * Position contains position but also turtle 
 * orientation
 **/
case class Position(x:Int, y:Int, d:Direction) {
   def left  = Position(x-1,y,dslprez.left)
   def right = Position(x+1,y,dslprez.right)
   def up    = Position(x,y+1,dslprez.up)
   def down  = Position(x,y-1,dslprez.down)
}

/**
 * Turtle
 * Steps are the history of moves built as a Stream
 * basic moves concepts: up/down/left/right
 * and while and until
 * plus a helper to generate JSon output
 **/
class Turtle(position:Position) {

   var steps = position #:: Stream.empty

   def lastPosition = steps.head

   // Used to manage the by/while/until
   // currentDirection is set by move call
   // by/while/until only work if move previously called
   // at the end this value is reset
   var currentDirection:Option[Direction] = None

   def move(d: Direction) = {
      d match {
         case dslprez.left => steps = Stream(steps.head.left) ++ steps
         case dslprez.right => steps = Stream(steps.head.right) ++ steps
         case dslprez.up => steps = Stream(steps.head.up) ++ steps
         case dslprez.down => steps = Stream(steps.head.down) ++ steps
      }
      currentDirection = Some(d)
      this
   }
  
   def by(s:Step) = {
      for (d <- currentDirection; i <- 1 until s.i) move(d)
      currentDirection = None
   }

   def `while`(condition: Position => Boolean) = {
      for (d <- currentDirection) while(condition(lastPosition)) move(d)
      currentDirection = None
   }

   def until(condition: Position => Boolean) = {
      for (d <- currentDirection) do move(d) while(!condition(lastPosition)) 
      currentDirection = None
   }

   def stepsToJson = compact(render("steps"->steps.reverse))
}

} // end package
