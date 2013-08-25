package dslprez.scala.game

// Avoid warning
import scala.language.implicitConversions

import scala.collection.mutable.{Map => MutMap}

import scala.collection.JavaConverters._

import scala.util.continuations._


/**
 * Turtle
 * Steps are the history of moves built as a Stream
 * basic moves concepts: up/down/left/right
 * and while and until
 * plus a helper to generate JSon output
 */
class Turtle(val name:String, val image: String, position: Position,val maze:Set[(Int,Int)], notifier: Notifier) {

  def this(name:String, image: String, position: Position, maze:Array[Array[Int]], groovyInstance: Object)  =  this(name,image,position,toSet(maze), new Notifier(groovyInstance))
   
  val myAsk = new Ask(notifier)
  
  var steps = position #:: Stream.empty
  
  var newStepsCount = 0

  def newSteps = {
    val newStepsStream = steps.take(newStepsCount)
    newStepsCount = 0
    newStepsStream
  }
   
  def getNewStepsAsJavaList = print(newSteps).to(JavaList)
  
  def lastPosition = steps.head

  // Used to manage the by/while/until
  // currentDirection is set by move call
  // by/while/until only work if move previously called
  // at the end this value is reset
  var currentDirection: Option[Direction] = None

  private def canMove(p:Position,d:Direction) = d match {
      case dslprez.scala.game.left => !maze.contains((p.x-1,p.y)) 
      case dslprez.scala.game.right => !maze.contains((p.x+1,p.y))
      case dslprez.scala.game.up => !maze.contains((p.x,p.y+1))
      case dslprez.scala.game.down => !maze.contains((p.x,p.y-1))
     
  }
  
  def move(d: Direction) = {
    var hasMoved = true
    d match {
      case dslprez.scala.game.left if canMove(steps.head,dslprez.scala.game.left) => steps = Stream(steps.head.left) ++ steps
      case dslprez.scala.game.right if canMove(steps.head,dslprez.scala.game.right) => steps = Stream(steps.head.right) ++ steps
      case dslprez.scala.game.up if canMove(steps.head,dslprez.scala.game.up) => steps = Stream(steps.head.up) ++ steps
      case dslprez.scala.game.down if canMove(steps.head,dslprez.scala.game.down) => steps = Stream(steps.head.down) ++ steps
      case _ => hasMoved = false
    }
    if (hasMoved) {
      currentDirection = Some(d)
      newStepsCount += 1
    }
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

  /*def `while`(condition: Position => Boolean) = {
    for (d <- currentDirection) while (condition(lastPosition)) move(d)
    currentDirection = None
    this
  }*/

  def until(condition: Position => Boolean) = {
    for (d <- currentDirection) while (!condition(lastPosition)) move(d)
    currentDirection = None
    this
  }

  def print(what: Stream[Position]=steps) = new StreamPrinter(what,this)

  val questions = MutMap[String,String]()

  var lastQuestion:Option[String] = None
  var lastAnswer:Option[String] = None

  def getNewAsksAsJavaList = if (!lastQuestion.isEmpty && !lastAnswer.isEmpty) List(Map("_question"->lastQuestion.get,"answer"->lastAnswer.get).asJava).asJava else List().asJava
  
  /*val logWriter = {
    val logFile = new java.io.File("/tmp/scalaAsk_"+name+".out")
    new java.io.PrintWriter(logFile, "UTF-8")
  }*/
  
  def ask(question: String) = {
    lastQuestion = Some(question)
    
    //logWriter.println("Question for "+name+" => "+question)
    //logWriter.flush()
    
    myAsk.ask(question)
  }
  
  def answer(answer:String) = {
    if (lastQuestion.nonEmpty)  questions.put(lastQuestion.get,answer)
    lastAnswer = Some(answer)
    
    //logWriter.println("Answer to "+name+" for the question "+lastQuestion.getOrElse("noQuestion")+" => "+answer)
    //logWriter.flush()
    
    myAsk.answer(answer)
  }
}

object Turtle {
  
  def startDsl(dsl: => Unit @cps[Unit])(implicit t: Turtle) = t.myAsk.start(dsl)
  
  def end(implicit t: Turtle) = t.myAsk.end
   
}