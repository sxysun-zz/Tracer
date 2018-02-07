package main.scala.core

import java.util.Timer
import java.util.Date

import main.scala.misc.ParametricRay
import main.scala.algebra.Vector
import main.scala.image._
import main.scala.config.SceneConfig
import main.scala.core._

object Renderer {
  var timeP: List[Long] = List()
  def render(img: Image, scnCfg: SceneConfig): List[Pixel] = {
    timeP = List()
    val start = new Date().getTime
    
    def renderTail(li: List[Pixel], t: Tuple2[Int, Int]): List[Pixel] = t match {
      
      case t @ Tuple2(img.imgCfg.width, img.imgCfg.height) => li
      
      case _ => {
        
        printCompletion(t._1.toFloat / img.imgCfg.width * 100, start, timeP)
        
        val pixelPosition = Vector.apply(List(t._1.toFloat / img.imgCfg.width, 
            0, t._2.toFloat / img.imgCfg.height))
            
        val ray = ParametricRay.apply(1f, scnCfg.viewPos, scnCfg.imageCenter + 
            pixelPosition)
            
        val processed = scnCfg.objs.zipWithIndex.map({
          x => (x._1.getHitPoint(ray), x._2)
        }).sortWith(_._1 < _._1).filter(_._1 != -1f)
        
        if(processed != Nil){
          
          val small = scnCfg.objs.drop(processed.head._2).head
          
          val norm = small.getNormal(ray, processed.head._1)
          
          val shader = Shader(scnCfg, norm, 
              ParametricRay(processed.head._1, scnCfg.viewPos, 
              scnCfg.imageCenter + pixelPosition).position(processed.head._1)
              , small)
          
          if(t._2 == img.imgCfg.height) 
            renderTail(li :+ shader.shade, (t._1 + 1, 0))
          else
            renderTail(li :+ shader.shade, (t._1, t._2 + 1))
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
  
  def printCompletion(percent: Float, startTime: Long, l: List[Long]): Unit = {
    if(Math.abs(percent - 100) < 0.001f && !timeP.contains(-1)) {
      timeP = timeP :+ -1.toLong
      return println("-----Completed-----")}
    val t = (new Date().getTime - startTime) / 1000
    if(!timeP.contains(t)){
    val time = (new Date().getTime - startTime).toFloat / 1000f
    println("now : " +  time.toInt + " - completed - " + percent + "%")
    println("estimated completion in " + (time/percent * 100 - time).toInt)
    timeP = timeP :+ t}
  }
}