package dslprez.scala.game

sealed trait PrintingMode
case object JSon extends PrintingMode
case object JavaMap extends PrintingMode
