package dslprez.scala.slides

import org.scalatest._

class DslTest extends org.scalatest.FlatSpec {

   "A dummy test" should "succeed" in  {
      assert(true)
   }
   
   "A turtle" should "move up" in {
      val t = new Turtle(Position(0,0,up))
      t move up
      assert(t.lastPosition.x == 0)
      assert(t.lastPosition.y == 1)
   }
   
   "A turtle" should "move down" in {
      val t = new Turtle(Position(0,1,up))
      t move down
      assert(t.lastPosition.x == 0)
      assert(t.lastPosition.y == 0)
   }
   
   "A turtle" should "move left" in {
      val t = new Turtle(Position(1,0,up))
      t move left
      assert(t.lastPosition.x == 0)
      assert(t.lastPosition.y == 0)
   }
   
   "A turtle" should "move right" in {
      val t = new Turtle(Position(0,0,up))
      t move right
      assert(t.lastPosition.x == 1)
      assert(t.lastPosition.y == 0)
   }
   
   "A turtle" should "move by steps" in {
      val t = new Turtle(Position(0,0,up))
      t move up by 3.steps
      assert(t.steps.length == 4)
      assert(t.lastPosition.x == 0)
      assert(t.lastPosition.y == 3)
   }

   "A turtle" should "not move if by steps not preceded by move direction" in {
      val t = new Turtle(Position(0,0,up))
      t by 3.steps
      assert(t.steps.length == 1)
      assert(t.lastPosition.x == 0)
      assert(t.lastPosition.y == 0)
   }
   
   "A turtle" should "move until" in {
      val t = new Turtle(Position(0,0,up))
      t move up until { _.y > 2}
      assert(t.steps.length == 4)  
      assert(t.lastPosition.x == 0)
      assert(t.lastPosition.y == 3)
   }
   
   "A turtle" should "move while" in {
      val t = new Turtle(Position(0,0,up))
      t move up `while` { _.y < 3}
      assert(t.steps.length == 4)  
      assert(t.lastPosition.x == 0)
      assert(t.lastPosition.y == 3)   
   }
   
   "A turtle" should "move several times" in {
      val t = new Turtle(Position(0,0,up))
      t move up
      t move right by 2.steps
      2.times { t move up }
      assert(t.steps.length == 6)  
      assert(t.lastPosition.x == 2)
      assert(t.lastPosition.y == 3)   
   }
   
      
   "A turtle" should "display output in Json" in {
      val t = new Turtle(Position(0,0,up))
      t move up
      t move right
      val expectedJson = "{\"steps\":[{\"direction\":\"up\",\"x\":0,\"y\":0},{\"direction\":\"up\",\"x\":0,\"y\":1},{\"direction\":\"right\",\"x\":1,\"y\":1}]}"
      assert(t.print(t.steps).to(JSon)==expectedJson)  
    }
}
