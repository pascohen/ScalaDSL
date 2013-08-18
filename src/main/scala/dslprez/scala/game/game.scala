// Avoid warning
import scala.language.implicitConversions

package dslprez.scala {
   package object game {
      implicit def toSteps(i: Int) = Step(i)
      
      implicit class Times(i: Int) {
         def times(c: => Any) = for (_ <- 1 to i) c
      }
   }
}
