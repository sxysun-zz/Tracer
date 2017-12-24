package test.scala.renderer

import main.scala.surfaces._
import main.scala.algebra._
import main.scala.image._
import main.scala.config._
import main.scala.core._

import java.awt.image.RenderedImage
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Color

object SphereTest {
  def main(args:Array[String]):Unit = {
    val origin = Vector.apply(List(0f, 0f, 0f))
    val imageCenter = Vector.apply(List(-.5f, 1f, -.5f))
    val sphere1 = SphereSurface.apply(
        1f, Vector.apply(List(0f, 5f, 0f)), Color.YELLOW, 
        Color.GRAY, Color.YELLOW, 100f)
    val sphere2 = SphereSurface.apply(
        0.5f, Vector.apply(List(1f, 5f, 0f)), Color.WHITE, 
        Color.GRAY, Color.WHITE, 100f)
    val sphere3 = SphereSurface.apply(
        3f, Vector.apply(List(-1f, 8f, -1f)), Color.CYAN, 
        Color.GRAY, Color.CYAN, 100f)
    
    val image = Image.apply(ImageConfig.apply("./output.png", 199, 199, "png"))
    val scene = SceneConfig.apply(imageCenter, origin, Color.BLACK, 
        List(sphere1, sphere2, sphere3), 
        List(Light.apply(Vector(List(-1f, 0.2f, 1f)), List(.9f, 1f, .9f)),
            Light.apply(Vector(List(-0.7f, 0f, 1f)), List(.8f, .8f, 1f))), 1f)
    image.saveTo(image.paint(Renderer.render(image, scene)))
  }
}