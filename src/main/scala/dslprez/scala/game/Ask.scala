package dslprez.scala.game

import scala.util.continuations._

class Ask {

   // The continuation
   var cont: Option[String => Unit] = None

   def ask(question:String, notifier: Notifier):String @cps[Unit] = {
      shift { 
         k: (String => Unit) => 
            // Make the continuation available for call
            cont = Some(k) 
            // Display the query and then quit the continuation
            //println(query)
            notifier.emit(question)
         } 
   }

   // Starts the DSL - basically a set of DSL rules
   // with ask and waiting for answer to continue
   def start(f: => Unit @cps[Unit])  = {
      reset {
         f
      }
   }

   // get back into the DSL flow by calling the stored continuation
   def answer(answer:String, notifier: Notifier) = {
     if (!cont.isEmpty) {
       cont.get(answer) 
       notifier.notify(answer)
       }
   }
   
   def end(notifier: Notifier) = {
     cont=None
     notifier.end()
   }
}
