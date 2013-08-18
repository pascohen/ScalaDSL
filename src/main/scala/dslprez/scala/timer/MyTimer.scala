package dslprez.scala.timer

object MyTimer {
  
  var startTime:Long = 0
  
  def reinit = startTime = 0
  
  def getStartTime = { 
    if (startTime == 0) startTime = System.currentTimeMillis()
    startTime
  }
}
