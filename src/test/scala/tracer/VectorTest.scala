package test.scala.tracer

import main.scala.algebra.Vector

object VectorTest {
  def test():Unit = {
    val temp = Vector.apply(List(1f, 1f, 1f))
    println(Vector.apply(List(1f, 1f, 1f)) + temp)
    println(temp.normalize dot temp.normalize)
  }
}