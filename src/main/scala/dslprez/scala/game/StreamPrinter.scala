package dslprez.scala.game

// JSon part
import net.liftweb.json._
import net.liftweb.json.JsonDSL._

import scala.collection.JavaConverters._

// Avoid warning
import scala.language.implicitConversions

class StreamPrinter(val stream: Stream[Position],val t: Turtle) {

  def toJavaList(l:List[Position]) = l.map(p => p.toMapStructure.asJava).asJava
  
  def to(p: PrintingMode) = p match {
    case `JavaList` => toJavaList(stream.reverse.toList)
    case _ => ()
  }
}
