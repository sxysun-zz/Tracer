package test.scala.tracer

object working {
  def main(args: Array[String]): Unit = {
//    SphereTest.test()
    1.to(50).map(x => {
      StarWarsTest.test(x.toString, x.toFloat/30)
    })
  }
}