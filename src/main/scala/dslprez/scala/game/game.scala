// Avoid warning
import scala.language.implicitConversions

package dslprez.scala {
  package object game {
   
    def toSet(a: Array[Array[Int]]) = (for (i<-0 until a.length;j<-0 until a(i).length if (a(i)(j) == 1)) yield (i,j)).toSet
    
    implicit def toSteps(i: Int) = Step(i)
      
    implicit class Times(i: Int) {
      def times(c: => Any) = for (_ <- 1 to i) c
    }
  }
}
