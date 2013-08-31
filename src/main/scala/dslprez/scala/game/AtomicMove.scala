package dslprez.scala.game

import scala.util.continuations._

trait AtomicMove extends Move with Interact {
  
  override def startDsl(dsl: => Unit @cps[Unit]) {
    try {
      super.startDsl(dsl)
    } catch {
      case e:Exception => {
        steps = steps.splitAt(newStepsCount)._2
        throw e
      }
    }
  }  
}
