package dslprez.continuations

import scala.util.continuations._

object Ask {

var cont: Unit => Unit = _
var lastAnswer: String = _

def ask(query:String):String @cps[Unit] = {
shift { 
   k: (Unit => Unit) => 
      cont = k; 
      println(query)
      } 
      lastAnswer
}

def start(f: => Unit @cps[Unit])  = {
 reset {
    f
  }
}

def answer(answer:String) = {lastAnswer= answer; cont() }
}
