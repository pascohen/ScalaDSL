package dslprez.scala.game

protected class Notifier(private val groovyInstance:Object) {
  def emit(query:String) {
    val m = groovyInstance.getClass.getMethod("waitForAnswer",classOf[Object])
    m.invoke(groovyInstance,query)
  }
  
  def notify(answer:String) {
    val m = groovyInstance.getClass.getMethod("notifyResponse",classOf[String])
    m.invoke(groovyInstance,answer)
  }
    
  def end()  {
    val m = groovyInstance.getClass.getMethod("end")
    m.invoke(groovyInstance)
  }
}
