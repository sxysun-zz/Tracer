package main.scala.core

import main.scala.algebra._
import main.scala.config._
import main.scala.surfaces._
import main.scala.image.Pixel
import java.awt.Color

case class Shader (scnCfg: SceneConfig, norm: Vector, 
    hitPosition: Vector, obj: SphereSurface) {
  
  def shade(): Pixel = {
    val ambient = ambientShading
    //List of sigmas
    val combined = scnCfg.light.map(x => 
      higher.higherCorrespondProc(lambertianShading(x))(blinnPhoneShading(x))(_+_)
    )
    
    Pixel.apply(new Color(
        
    ))
  }
  
  def lambertianShading(l: Light): List[Float] = {
      val product = Math.max(0, norm dot ((l.position - hitPosition).normalize))
      List(
          Math.min(254, (obj.oriColor.getRed * l.intensity.head * product)), 
          Math.min(254, (obj.oriColor.getGreen * l.intensity.drop(1).head * product)),
          Math.min(254, (obj.oriColor.getBlue * l.intensity.drop(2).head * product))
      )
  }
  
  def blinnPhoneShading(l: Light): List[Float] = {
    val hitToView = (scnCfg.viewPos - this.hitPosition).normalize
    val hitToLight = (scnCfg.light.head.position - this.hitPosition).normalize
    val product = Math.pow(
        Math.max(0, norm dot ((hitToView + hitToLight).normalize))
        , obj.phongExponent).toFloat
    List(
          Math.min(254, (obj.specularColor.getRed * l.intensity.head * product)), 
          Math.min(254, (obj.specularColor.getGreen * l.intensity.drop(1).head * product)),
          Math.min(254, (obj.specularColor.getBlue * l.intensity.drop(2).head * product))
    )
  }
  
  def ambientShading(): List[Float] = {
    List(
          Math.min(254, (obj.ambientColor.getRed * scnCfg.ambientLightIntensity)), 
          Math.min(254, (obj.ambientColor.getGreen * scnCfg.ambientLightIntensity)),
          Math.min(254, (obj.ambientColor.getBlue * scnCfg.ambientLightIntensity))
    )
  }
  
  def colorMean(l: List[Color]): Color = {
    new Color(((0 /: l) {(x, xs) => x + xs.getRed}) / l.length, 
        ((0 /: l) {(x, xs) => x + xs.getGreen}) / l.length,
        ((0 /: l) {(x, xs) => x + xs.getBlue}) / l.length)
  }
  
  def listColorCorrespondProc[T](l: List[List[T]]): List[Any] = {
    
  }
}