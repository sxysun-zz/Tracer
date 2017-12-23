package test.scala.algebra

import main.scala.surfaces.SphereSurface
import main.scala.misc.ParametricRay
import main.scala.algebra.Vector
import main.scala.image.Pixel
import main.scala.misc.rgba
import main.scala.image.Image

import java.awt.image.RenderedImage
import java.io.File
import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.Color

object SphereTest {
  def main(args:Array[String]):Unit = {
    val center = Vector.apply(List(0f, 5f, 0f))
    val origin = Vector.apply(List(0f, 0f, 0f))
    val imageCenter = Vector.apply(List(-.5f, 1f, -.5f))
    val sphere1 = SphereSurface.apply(1f, center, Color.YELLOW)
    val sphere2 = SphereSurface.apply(0.5f, Vector.apply(List(1f, 5f, 0f)), Color.RED)
    
    val rendered = Image.apply("./output.png", 199, 199, "png", 
        imageCenter, origin, Color.BLUE)
    rendered.saveTo(rendered.paint(rendered.render(List(sphere1, sphere2))))
  }
}