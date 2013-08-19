package dslprez.scala.game

// Sealed Directions - could be an Enum
sealed trait Direction
case object left extends Direction
case object right extends Direction
case object up extends Direction
case object down extends Direction
