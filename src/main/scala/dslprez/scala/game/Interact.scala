package dslprez.scala.game

import scala.collection.mutable.Map

import scala.util.continuations._

trait Interact {

  protected def myAsk:Ask
  protected def notifier:Notifier

  private val questions = Map[String, String]()

  protected var lastQuestion: Option[String] = None
  protected var lastAnswer: Option[String] = None

  def ask(question: String) = {
    lastQuestion = Some(question)
    myAsk.ask(question, notifier)
  }

  def answer(answer: String) = {
    if (lastQuestion.nonEmpty) questions.put(lastQuestion.get, answer)
    lastAnswer = Some(answer)
    myAsk.answer(answer, notifier)
  }

  def end() = {
    lastQuestion = None
    lastAnswer = None
    myAsk.end(notifier)
  }
  
  def startDsl(dsl: => Unit @cps[Unit]) = {
    myAsk.start(dsl)
  }

}
