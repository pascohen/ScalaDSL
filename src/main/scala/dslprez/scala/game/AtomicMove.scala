package dslprez.scala.game

import scala.util.continuations._

trait AtomicMove extends Move with Interact {
  
  override def startDsl(dsl: => Unit @cps[Unit]) {
    val initialCount = newStepsCount
    try {
      super.startDsl(dsl)
    } catch {
      case e:Exception => {
        steps = steps.drop(newStepsCount-initialCount) //savedSteps
        newStepsCount = 0
        throw e
      }
    }
  }  
}
