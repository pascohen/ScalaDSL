package dslprez.scala.game

// Steps class mainly for readability
case class Step(i: Int) {
  def steps = this
  def step  = this
}

case class StepAsString(s: String) {
  val stepAsInt = Step(s.toInt)
  def steps = stepAsInt
  def step  = stepAsInt
}
