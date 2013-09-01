package dslprez.scala.game

// Avoid warning
import scala.language.implicitConversions

import scala.collection.JavaConverters._

import scala.util.continuations._

/**
 * Turtle
 * Steps are the history of moves built as a Stream
 * basic moves concepts: up/down/left/right
 * and while and until
 * plus a helper to generate JSon output
 */
class Turtle(val name: String, val image: String, position: OrientedPosition, val maze: Set[(Int, Int)], override val notifier: Notifier) extends Move with Interact {

  private def this(name: String, image: String, position: OrientedPosition, maze: Array[Array[Int]], groovyInstance: Object) = this(name, image, position, toSet(maze), new Notifier(groovyInstance))

  def getName() = name
  
  val myAsk = new Ask

  steps = position #:: Stream.empty

  private var meetPoint:Option[MeetPosition] = None

  def meet(x: Int, y: Int) = {
    if (!maze.contains(x, y)) {
      meetPoint = Some(new MeetPosition(x, y))
    }
  }

  def getNewStepsAsJavaList() = newSteps().reverse.map(p => p.toMapStructure.asJava).asJava
  
  def getNewAsksAsJavaList = if (!lastQuestion.isEmpty && !lastAnswer.isEmpty) List(Map("_question" -> lastQuestion.get, "answer" -> lastAnswer.get).asJava).asJava else List().asJava

  def getMeetPointAsJavaMap() = {
    val mp = (if(meetPoint.nonEmpty) Map("x"->meetPoint.get.x,"y"->meetPoint.get.y) else Map[String,Int]()).asJava
    meetPoint = None
    mp
  }

}

object Turtle {

  def meet(x: Int, y: Int)(implicit t: Turtle) = t.meet(x, y)

  def startDsl(dsl: => Unit @cps[Unit])(implicit t: Turtle) = t.startDsl(dsl)

  def end(implicit t: Turtle) = t.end()
  
  def getTurtle(name: String, image: String, position: OrientedPosition, maze: Array[Array[Int]], groovyInstance: Object) = 
    new Turtle(name,image,position,maze,groovyInstance) with LimitedMove with TimedMove with AtomicMove

  def getTurtleWithoutAtomic(name: String, image: String, position: OrientedPosition, maze: Array[Array[Int]], groovyInstance: Object) = 
    new Turtle(name,image,position,maze,groovyInstance) with LimitedMove with TimedMove

}