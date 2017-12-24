package main.scala.core

import main.scala.misc.ParametricRay
import main.scala.algebra.Vector
import main.scala.image._
import main.scala.config.SceneConfig
import main.scala.core._

object Renderer {
  def render(img: Image, scnCfg: SceneConfig): List[Pixel] = {
    
    def renderTail(li: List[Pixel], t: Tuple2[Int, Int]): List[Pixel] = t match {
      
      case t @ Tuple2(img.imgCfg.width, img.imgCfg.height) => li
      
      case _ => {
        
        println(t._1.toFloat / img.imgCfg.height * 100 + "%")
        
        val pixelPosition = Vector.apply(List(t._1.toFloat / img.imgCfg.width, 
            0, t._2.toFloat / img.imgCfg.height))
            
        val ray = ParametricRay.apply(1f, scnCfg.viewPos, scnCfg.imageCenter + 
            pixelPosition)
            
        val processed = scnCfg.objs.zipWithIndex.map({
          x => (x._1.getHitPoint(x._1, ray, x._1.discriminant(x._1, ray)), x._2)
        }).sortWith(_._1 < _._1).filter(_._1 != -1f)
        
        if(processed != Nil){
          
          val small = scnCfg.objs.drop(processed.head._2).head
          
          val norm = small.getNormal(small, ray, 
              small.discriminant(small, ray), processed.head._1)
              
          val hitPosition = ParametricRay.apply(processed.head._1, scnCfg.viewPos, 
              scnCfg.imageCenter + pixelPosition).position(processed.head._1)
          
          val shader = Shader.apply(scnCfg, norm, hitPosition, small)
              
          if(t._2 == img.imgCfg.height) 
            renderTail(li :+ shader.shade(), (t._1 + 1, 0))
          else
            renderTail(li :+ shader.shade(), (t._1, t._2 + 1))
        } else {
          if(t._2 == img.imgCfg.height) 
            renderTail(li :+ Pixel.apply(scnCfg.bg), (t._1 + 1, 0))
          else
            renderTail(li :+ Pixel.apply(scnCfg.bg), (t._1, t._2 + 1))
        }
      }
    }
    
    renderTail(List[Pixel](), (0,0))
    
  }
}