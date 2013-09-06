package dslprez.scala.slides

// Avoid warning
import scala.language.implicitConversions

import scala.util.continuations._

// Sealed Directions - could be an Enum
sealed trait Direction
case object left extends Direction
case object right extends Direction
case object up extends Direction
case object down extends Direction

// Steps class mainly for readability
case class Step(i: Int) {
  def steps = this
}

/**
 * Position contains position but also turtle
 * orientation
 */
case class Position(x: Int, y: Int) {
  def left = Position(x - 1, y)
  def right = Position(x + 1, y)
  def up = Position(x, y + 1)
  def down = Position(x, y - 1)
}

/**
 * Turtle
 * Steps are the history of moves built as a Stream
 * basic moves concepts: up/down/left/right
 * and while and until
 * plus a helper to generate JSon output
 */
class Turtle(position: Position) {

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
      case dslprez.scala.slides.left => steps = Stream(steps.head.left) ++ steps
      case dslprez.scala.slides.right => steps = Stream(steps.head.right) ++ steps
      case dslprez.scala.slides.up => steps = Stream(steps.head.up) ++ steps
      case dslprez.scala.slides.down => steps = Stream(steps.head.down) ++ steps
    }
    currentDirection = Some(d)
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

  def print = println("My current Position is "+lastPosition)
  
  def ask(question: String) = myAsk.ask(question)
  
}

object Turtle {
  var currentTurtle:Turtle = _
  
  def print(implicit t: Turtle = currentTurtle) = t.print
  def answer(answer: String)(implicit t: Turtle = currentTurtle) = t.myAsk.answer(answer)
  
  def startDsl(dsl: => Unit @cps[Unit])(implicit t: Turtle) = {
    currentTurtle = t
    t.myAsk.start(dsl)
  }
  def move(d:Direction)(implicit t: Turtle = currentTurtle) = t.move(d)
  
  def end(implicit t: Turtle = currentTurtle) = t.myAsk.end

}
