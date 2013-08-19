package dslprez.scala.game

// Avoid warning
import scala.language.implicitConversions

import scala.util.continuations._

/**
 * Turtle
 * Steps are the history of moves built as a Stream
 * basic moves concepts: up/down/left/right
 * and while and until
 * plus a helper to generate JSon output
 */
class Turtle(val name:String, val image: String, val maze:Set[(Int,Int)], position: Position) {

  def this(name:String, image: String, maze:Array[Array[Int]], position: Position) =
    this(name,image,toSet(maze),position)
    
    //def result = [:]
    //def i = 1;

  val myAsk = new dslprez.scala.continuations.Ask
  
  var steps = position #:: Stream.empty

  var newStepsCount = 0

  def newSteps = {
    val newStepsStream = steps.take(newStepsCount)
    newStepsCount = 0
    newStepsStream
  }

  def lastPosition = steps.head

  // Used to manage the by/while/until
  // currentDirection is set by move call
  // by/while/until only work if move previously called
  // at the end this value is reset
  var currentDirection: Option[Direction] = None

  def move(d: Direction) = {
    d match {
      case dslprez.scala.game.left => steps = Stream(steps.head.left) ++ steps
      case dslprez.scala.game.right => steps = Stream(steps.head.right) ++ steps
      case dslprez.scala.game.up => steps = Stream(steps.head.up) ++ steps
      case dslprez.scala.game.down => steps = Stream(steps.head.down) ++ steps
    }
    currentDirection = Some(d)
    newStepsCount += 1
    this
  }

  def changeOrientation(newOrientation: Direction) = {
    steps = Stream(steps.head.copy(direction = newOrientation)) ++ steps
    newStepsCount += 1
    this
  }

  def by(s: Step) = {
    for (d <- currentDirection; i <- 1 until s.i) move(d)
    currentDirection = None
    this
  }

  def `while`(condition: Position => Boolean) = {
    for (d <- currentDirection) while (condition(lastPosition)) move(d)
    currentDirection = None
    this
  }

  def until(condition: Position => Boolean) = {
    for (d <- currentDirection) do move(d) while (!condition(lastPosition))
    currentDirection = None
    this
  }

  def print(what: Stream[Position]=steps) = new StreamPrinter(what,this)
 
  def ask(question: String) = myAsk.ask(question)
  
}

object Turtle {
  def answer(answer: String)(implicit t: Turtle) = t.myAsk.answer(answer)
  
  def startDsl(dsl: => Unit @cps[Unit])(implicit t: Turtle) = t.myAsk.start(dsl)
  
  def end(implicit t: Turtle) = t.myAsk.end

}