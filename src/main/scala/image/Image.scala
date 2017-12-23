package main.scala.image

import main.scala.surfaces.SphereSurface
import main.scala.misc.ParametricRay
import main.scala.algebra.Vector
import javax.imageio.ImageIO
import java.awt.image.RenderedImage
import java.io.File
import java.awt.image.BufferedImage
import java.awt.Color

case class Image(path: String, width: Int, height: Int, 
    form: String, imageCenter: Vector, viewPos: Vector, bg: Color) {

  def paint(lis: List[Pixel]): BufferedImage = {
      val img = new BufferedImage(this.width + 1, this.height + 1, BufferedImage.TYPE_INT_RGB)
      def paintTail(t: Tuple2[Int, Int], li: List[Pixel]):BufferedImage = t match {
        case Tuple2(this.width, this.height) => img
        case Tuple2(_, this.height) => {
          img.setRGB(t._1, t._2, li.head.color.getRGB)
          paintTail(Tuple2(t._1 + 1, 0), li.drop(1))
        }
        case _ => {
          img.setRGB(t._1, t._2, li.head.color.getRGB)
          paintTail(Tuple2(t._1, t._2 + 1), li.drop(1))
        }
      }
      paintTail(Tuple2(0, 0), lis)
  }
  
  def render(objs: List[SphereSurface]): List[Pixel] = {
    def renderTail(li: List[Pixel], t: Tuple2[Int, Int]): List[Pixel] = t match {
      case t @ Tuple2(this.width, this.height) => li
      case _ => {
        println(t._1.toFloat / this.height * 100 + "%")
        val ray = ParametricRay.apply(1f, viewPos, imageCenter + 
            Vector.apply(List(t._1.toFloat / this.width, 0, t._2.toFloat / this.height)))
        val processed = objs.zipWithIndex.map({
          x => Tuple2(x._1.getHitPoint(x._1, ray, x._1.discriminant(x._1, ray)), x._2)
        }).sortWith(_._1 < _._1).filter(_._1 != -1f)
        if(processed != Nil){
          val small = objs.drop(processed.head._2).head
          val norm = small.getNormal(small, ray, 
              small.discriminant(small, ray), processed.head._1)
          if(t._2 == this.height) 
            renderTail(li :+ Pixel.apply(small.oriColor), Tuple2(t._1 + 1, 0))
          else
            renderTail(li :+ Pixel.apply(small.oriColor), Tuple2(t._1, t._2 + 1))
        }
        else{
          if(t._2 == this.height) 
            renderTail(li :+ Pixel.apply(bg), Tuple2(t._1 + 1, 0))
          else
            renderTail(li :+ Pixel.apply(bg), Tuple2(t._1, t._2 + 1))
        }
      }
    }
    renderTail(List[Pixel](), Tuple2(0,0))
  }
  
  def saveTo(img: BufferedImage) = {
    val fpath: String = "./output.png"
    ImageIO.write(img, form, new File(path))
  }
}