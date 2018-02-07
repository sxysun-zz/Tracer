package main.scala.core

import main.scala.algebra._
import main.scala.config._
import main.scala.surfaces._
import main.scala.misc._
import main.scala.image.Pixel
import java.awt.Color

case class Shader (scnCfg: SceneConfig, norm: Vector, 
    hitPosition: Vector, obj: SphereSurface) {
  
  def shade(): Pixel = {
    val combined = scnCfg.light.map(x => 
      if(shadow(x)) ColorTool.colorToList(scnCfg.bg).map(_*2)
      else higher.higherCorrespondProc(lambertianShading(x))(blinnPhoneShading(x))(_+_)
    )
    
    val sigmaed = higher.listCombineHigher(combined)(0f)(_+_)
    
    val shaded = ColorTool.listClamp(
        higher.higherCorrespondProc(sigmaed)(ambientShading)(_+_).map(_/3 * 255))
    
    Pixel.apply(new Color(shaded.head.toInt, 
        shaded.drop(1).head.toInt, shaded.drop(2).head.toInt))
  }
  
  def shadow(l: Light): Boolean = {
    val epsilon = 0.01f
    val ray = ParametricRay.apply(epsilon, this.hitPosition, l.position)
    (false /: scnCfg.objs.map(x => x.hit(ray) && 
        x.getHitPoint(ray) > epsilon
    )) (_||_)
  }
  
  def lambertianShading(l: Light): List[Float] = {
      val product = Math.max(0, norm dot ((l.position - hitPosition).normalize))
      ColorTool.listClamp(List(
          obj.oriColor.getRed/255 * l.intensity.head * product, 
          obj.oriColor.getGreen/255 * l.intensity.drop(1).head * product,
          obj.oriColor.getBlue/255 * l.intensity.drop(2).head * product
      ))
  }
  
  def blinnPhoneShading(l: Light): List[Float] = {
    val hitToView = (scnCfg.viewPos - this.hitPosition).normalize
    val hitToLight = (l.position - this.hitPosition).normalize
    val product = Math.pow(
        Math.max(0, norm dot ((hitToView + hitToLight).normalize))
        , obj.phongExponent).toFloat
    ColorTool.listClamp(List(
          obj.specularColor.getRed/255 * l.intensity.head * product, 
          obj.specularColor.getGreen/255 * l.intensity.drop(1).head * product,
          obj.specularColor.getBlue/255 * l.intensity.drop(2).head * product
    ))
  }
  
  def ambientShading(): List[Float] = {
    ColorTool.listClamp(List(
          (obj.ambientColor.getRed/255 * scnCfg.ambientLightIntensity), 
          (obj.ambientColor.getGreen/255 * scnCfg.ambientLightIntensity),
          (obj.ambientColor.getBlue/255 * scnCfg.ambientLightIntensity)
    ))
  }
}