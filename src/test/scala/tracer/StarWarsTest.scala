package test.scala.tracer

import main.scala.surfaces._
import main.scala.algebra._
import main.scala.image._
import main.scala.config._
import main.scala.core._
import main.scala.misc._
import main.scala.anim._

import java.awt.Color

object StarWarsTest {
  
  val deathStarPosition = Vector.apply(List(2f, 8f, 0f))
  val falconPosition = Vector.apply(List(-1f, 5f, 0f))
  val tieClusterPosition = Vector.apply(List(-1f, 5f, 0f))
  val shift = Vector.apply(List(.1f, .1f, .1f))
  val view = Vector.apply(List(0f, 0f, 0f))
  
  def test(name: String, time: Float) = {
    //Model
    val deathStar = SphereSurface.apply(3f, 
        deathStarPosition, Color.GREEN, 
        Color.WHITE, Color.GREEN, 200f)
    val falcon = SphereSurface.apply(.3f, 
        Motion.starWarsFalconMotion(time, falconPosition)
        , Color.WHITE, 
        Color.WHITE, Color.WHITE, 100f)
    val ties = 1.to(5).map(x => {
      SphereSurface.apply(.2f, 
        Motion.starWarsTIEMotion(time, tieClusterPosition), Color.GRAY, 
        Color.WHITE, Color.WHITE, 100f)
    }).toList
    
    //Image
    val imageCenter = Motion.closerImageMotion(time, 
        Vector.apply(List(-.5f, .1f + time, -.5f)))
        
    val executor = SphereSurface.apply(.5f, 
        imageCenter + Vector.apply(List(.5f, .05f, 1.1f)), Color.WHITE, 
        Color.WHITE, Color.WHITE, 1000f)
    
    val image = Image.apply(ImageConfig.apply("./sw/starWars" + name + ".png", 199, 199, "png"))
    val scene = SceneConfig.apply(imageCenter, view, Color.BLACK.brighter, 
        List(deathStar, falcon, executor) ::: ties, 
        List(Light.apply(Vector(List(-10f, 0.2f, 1f)), List(1f, 1f, 1f)),
            Light.apply(
                Motion.starWarsFalconMotion(time, falconPosition + shift), 
                List(1f, 1f, 1f))), .7f)
    image.saveTo(image.paint(Renderer.render(image, scene)))
  }
}