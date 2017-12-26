package main.scala.core

import main.scala.image._
import main.scala.config._
import main.scala.misc._

case class AntiAliasing (img: Image, scn: SceneConfig){
  
  /*
   * SSAA may greatly increase the processing time
   */
  def superSamplingAntiAliasing(times: Int): List[Pixel] = {
    val tempW = img.imgCfg.width * times + 1
    val tempH = img.imgCfg.height * times + 1
    val pro = Renderer.render(Image.apply(
        ImageConfig.apply(img.imgCfg.path, tempW, tempH, img.imgCfg.form)), scn)
    def ssaaTail(t: Tuple2[Int, Int], o: List[Pixel]): List[Pixel] = t match {
      case t @ Tuple2(`tempW`, `tempH`) => {o}
      case _ => {
        if(t._1 % 2 == 0 && t._2 % 2 == 0) {
          if(t._2 == tempH) ssaaTail((t._1 + 1, 0), o :+ neutralizePixel(
              pro, times, t))
          else {println(t);ssaaTail((t._1, t._2 + 1), o :+ neutralizePixel(
              pro, times, t))}
        } else {
          if(t._2 == tempH) ssaaTail((t._1 + 1, 0), o)
          else ssaaTail((t._1, t._2 + 1), o)
        }
      }
    }
    val ret = ssaaTail((0,0), List())
    println(ret.length)
    ret
  }
  
  def neutralizePixel(l: List[Pixel], times: Int, position: Tuple2[Int, Int]): Pixel = {
    Pixel.apply(ColorTool.colorMean(List(
        l.drop(position._1 * (img.imgCfg.height * times + 1) + position._2).head.color,
        l.drop(position._1 * (img.imgCfg.height * times + 1) + position._2 + 1).head.color,
        l.drop(position._1 * (img.imgCfg.height * times + 1) + position._2 + (img.imgCfg.height * times + 1)).head.color,
        l.drop(position._1 * (img.imgCfg.height * times + 1) + position._2 + (img.imgCfg.height * times + 1) + 1).head.color
    )))
  }
}