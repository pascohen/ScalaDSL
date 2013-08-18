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
case class Position(x: Int, y: Int, d: Direction) {
  def left = Position(x - 1, y, dslprez.scala.slides.left)
  def right = Position(x + 1, y, dslprez.scala.slides.right)
  def up = Position(x, y + 1, dslprez.scala.slides.up)
  def down = Position(x, y - 1, dslprez.scala.slides.down)
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

  def changeOrientation(newOrientation: Direction) = {
    steps = Stream(steps.head.copy(d = newOrientation)) ++ steps
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

  def print(what: Stream[Position]=steps) = new StreamPrinter(what)
  
  def ask(question: String) = myAsk.ask(question)
  
}

object Turtle {
  def answer(answer: String)(implicit t: Turtle) = t.myAsk.answer(answer)
  
  def startDsl(dsl: => Unit @cps[Unit])(implicit t: Turtle) = t.myAsk.start(dsl)
  
  def end(implicit t: Turtle) = t.myAsk.end

}

sealed trait PrintingMode
case object JSon extends PrintingMode
