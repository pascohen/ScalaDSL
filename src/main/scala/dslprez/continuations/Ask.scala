package dslprez.continuations

import scala.util.continuations._

class Ask {

var cont: Unit => Unit = _

def ask(query:String,answer:String):String @cps[Unit] = {
shift { 
   k: (Unit => Unit) => 
      cont = k; 
      println(query)
} 
      answer
}

def start(f: => Unit @cps[Unit])  = {
 reset {
    f
     }
}

def callCont = cont()
}
