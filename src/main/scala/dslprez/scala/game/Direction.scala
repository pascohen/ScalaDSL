package dslprez.scala.game

// Sealed Directions - could be an Enum
sealed trait Direction {
  def asString:String 
}

case object left extends Direction {
  val asString = "-x"
}

case object right extends Direction {
  val asString = "+x"
}

case object up extends Direction {
  val asString = "+y"
}

case object down extends Direction {
  val asString = "-y"
}
