package dslprez.scala.game

import scala.util.continuations._

trait LimitedMove extends Move with Interact {

  val maxActions = 5
  var count = 0

  private def reset() = count = 0
  
  private def checkAndIncrement() = {
   count += 1
   println("Count is now "+count)
   if (count > maxActions) throw new TooManyActionsException
  }
  
  override def startDsl(dsl: => Unit @cps[Unit]) {
    reset()
    super.startDsl(dsl)
  }

  override def move(d: Direction) = {
    checkAndIncrement()
    super.move(d)
  }

  override def ask(question: String) = {
    checkAndIncrement()
    super.ask(question)
  }
  
}
