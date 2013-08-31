package dslprez.scala.game

import scala.util.continuations._

trait TimedMove extends Move with Interact {

  val maxDuration = 10
  var startTime = 0l

  private def reset() = startTime = System.currentTimeMillis()
  
  private def checkDuration(d:Long) = {
   val spentTime = (d-startTime)
   if (spentTime > maxDuration*1000) throw new TimeoutException
  }
  
  override def startDsl(dsl: => Unit @cps[Unit]) {
    reset()
    super.startDsl(dsl)
  }

  override def move(d: Direction) = {
    val result = super.move(d)
    val t = System.currentTimeMillis()
    checkDuration(t)
    result
  }

  override def ask(question: String) = {
    val result = super.ask(question)
    val t = System.currentTimeMillis()
    checkDuration(t)
    result
  }
  
}