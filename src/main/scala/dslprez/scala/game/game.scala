// Avoid warning
import scala.language.implicitConversions

package dslprez.scala {
  package object game {
  
    implicit def toDirection(s:String) = s match {
      case "up" => dslprez.scala.game.up
      case "down" => dslprez.scala.game.down
      case "left" => dslprez.scala.game.left
      case "right" => dslprez.scala.game.right
      case _ => throw new InvalidDirection()
    }
   
    def toSet(a: Array[Array[Int]]) = (for (i<-0 until a.length;j<-0 until a(i).length if (a(i)(j) == 1)) yield (i,j)).toSet
    
    implicit def toSteps(i: Int) = Step(i)
      
    implicit class Times(i: Int) {
      def times(c: => Any) = for (_ <- 1 to i) c
    }
  }
}
