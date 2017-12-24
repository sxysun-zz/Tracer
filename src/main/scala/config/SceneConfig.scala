package main.scala.config

import main.scala.algebra.Vector
import main.scala.surfaces._
import java.awt.Color

case class SceneConfig (imageCenter: Vector, viewPos: Vector, bg: Color, 
    objs: List[SphereSurface], light: List[Light], ambientLightIntensity: Float){
  
}

//intensity is a list of 3 floats between 0 and 1 
//signifying the corresponding r, g, b intensities
case class Light (position: Vector, intensity: List[Float]) {}