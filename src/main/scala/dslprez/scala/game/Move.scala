package dslprez.scala.game

/**
 * Turtle
 * Steps are the history of moves built as a Stream
 * basic moves concepts: up/down/left/right
 * and while and until
 * plus a helper to generate JSon output
 */
trait Move {
  def maze: Set[(Int,Int)]

  var steps: Stream[OrientedPosition] = _
   
  def lastPosition = steps.head

  // Used to manage the by/while/until
  // currentDirection is set by move call
  // by/while/until only work if move previously called
  // at the end this value is reset
  var currentDirection: Option[Direction] = None

  var newStepsCount = 0
  
  def newSteps() = {
    val newStepsStream = steps.take(newStepsCount)
    newStepsCount = 0
    newStepsStream
  }

  private def canMove(p: Position, d: Direction) = d match {
    case dslprez.scala.game.left => !maze.contains((p.x - 1, p.y))
    case dslprez.scala.game.right => !maze.contains((p.x + 1, p.y))
    case dslprez.scala.game.up => !maze.contains((p.x, p.y + 1))
    case dslprez.scala.game.down => !maze.contains((p.x, p.y - 1))
  }

  def move(d: Direction):this.type = moveInternal(d)

  private def moveInternal(d: Direction):this.type = {
    var hasMoved = true
    d match {
      case dslprez.scala.game.left if canMove(steps.head, dslprez.scala.game.left) => steps = Stream(steps.head.left) ++ steps
      case dslprez.scala.game.right if canMove(steps.head, dslprez.scala.game.right) => steps = Stream(steps.head.right) ++ steps
      case dslprez.scala.game.up if canMove(steps.head, dslprez.scala.game.up) => steps = Stream(steps.head.up) ++ steps
      case dslprez.scala.game.down if canMove(steps.head, dslprez.scala.game.down) => steps = Stream(steps.head.down) ++ steps
      case _ => hasMoved = false
    }
    if (hasMoved) {
      currentDirection = Some(d)
      newStepsCount += 1
    }
    this
  }

  def changeOrientation(newOrientation: Direction):this.type = {
    steps = Stream(steps.head.copy(direction = newOrientation)) ++ steps
    newStepsCount += 1
    this
  }

  def by(s: Step):this.type = {
    for (d <- currentDirection; i <- 1 until s.i) moveInternal(d)
    currentDirection = None
    this
  }

  def until(condition: Position => Boolean):this.type = {
    for (d <- currentDirection) while (!condition(lastPosition)) moveInternal(d)
    currentDirection = None
    this
  }
}
