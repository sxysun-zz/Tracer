package test.scala.tracer

import main.scala.surfaces._
import main.scala.algebra._
import main.scala.image._
import main.scala.config._
import main.scala.core._
import main.scala.misc._

import java.awt.Color

object SphereTest {
  
  def test():Unit = {
    val origin = Vector.apply(List(0f, 0f, 0f))
    val imageCenter = Vector.apply(List(-.5f, 1f, -.5f))
    
    
    val sphere1 = SphereSurface.apply(
        1f, Vector.apply(List(0f, 5f, 0f)), Color.YELLOW, 
        Color.WHITE, Color.YELLOW, 500f)
    val sphere2 = SphereSurface.apply(
        0.5f, Vector.apply(List(1f, 5f, 0f)), Color.RED, 
        Color.WHITE, Color.RED, 1000f)
    val sphere3 = SphereSurface.apply(
        3f, Vector.apply(List(-1f, 8f, -1f)), Color.CYAN, 
        Color.WHITE, Color.CYAN, 200f)
    
    val image = Image.apply(ImageConfig.apply("./spheres.png", 199, 199, "png"))
    val scene = SceneConfig.apply(imageCenter, origin, Color.BLACK.brighter, 
        List(sphere1, sphere2, sphere3), 
        List(Light.apply(Vector(List(-10f, 0.2f, 1f)), List(1f, 1f, 1f)),
            Light.apply(Vector(List(-1f, .2f, 1f)), List(1f, 1f, 1f))), .7f)
    image.saveTo(image.paint(Renderer.render(image, scene)))
//    image.saveTo(image.paint(
//        AntiAliasing.apply(image, scene).superSamplingAntiAliasing(2)))
  }
}