package dslprez.continuations

import scala.util.continuations._

// Should be better with a class
// Simpler for demo
object Ask {

   // The continuation
   var cont: Unit => Unit = _

   // last answer obtained - used as return value
   // of the conitnuation
   var lastAnswer: String = _

   def ask(query:String):String @cps[Unit] = {
      shift { 
         k: (Unit => Unit) => 
            // Make the continuation available for call
            cont = k 
            // Display the query and then quit the continuation
            println(query)
         } 
         // Continuation restarts and returns the last answer
         lastAnswer
   }

   // Starts the DSL - basically a set of DSL rules
   // with ask and waiting for answer to continue
   def start(f: => Unit @cps[Unit])  = {
      reset {
         f
      }
   }

   // get back into the DSL flow by calling the stored continuation
   def answer(answer:String) = {lastAnswer= answer; cont() }
}
