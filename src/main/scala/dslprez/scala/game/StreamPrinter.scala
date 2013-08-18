package dslprez.scala.game

// JSon part
import net.liftweb.json._
import net.liftweb.json.JsonDSL._

// Avoid warning
import scala.language.implicitConversions

class StreamPrinter(val stream: Stream[Position]) {

  implicit def toJsonValue(p: Position) = ("direction" -> p.d.toString) ~ ("x" -> p.x) ~ ("y" -> p.y)
  
  def stepsToJson(positions: Stream[Position]) = compact(render("steps" -> positions))

  def to(p: PrintingMode) = p match {
    case _:JSon.type => stepsToJson(stream.reverse)
    case _ => ()
  }
}
