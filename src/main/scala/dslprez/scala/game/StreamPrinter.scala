package dslprez.scala.game

// JSon part
import net.liftweb.json._
import net.liftweb.json.JsonDSL._

import scala.collection.JavaConverters._

// Avoid warning
import scala.language.implicitConversions

class StreamPrinter(val stream: Stream[Position],val t: Turtle) {

  implicit def toJsonValue(p: Position) = ("direction" -> p.direction.toString) ~ ("x" -> p.x) ~ ("y" -> p.y)
  
  def toJavaList(l:List[Position]) = l.map(p => p.toMapStructure.asJava).asJava
  
  def stepsToJson = compact(render(("name"->t.name)~("image"->t.image)~("steps" -> stream.reverse)~("asks"->List[String]())))
  def stepsToMap = Map("name"->t.name,
                       "image"->t.image,
                       "steps"->toJavaList(stream.reverse.toList),
                       "asks"->t.getNewAsks.asJava).asJava
  
  def to(p: PrintingMode) = p match {
    case _:JSon.type => stepsToJson
    case `JavaMap` => stepsToMap
    case _ => ()
  }
}
