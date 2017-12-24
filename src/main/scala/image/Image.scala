package main.scala.image

import main.scala.surfaces.SphereSurface
import main.scala.misc.ParametricRay
import main.scala.algebra.Vector
import main.scala.config._
import javax.imageio.ImageIO
import java.awt.image.RenderedImage
import java.io.File
import java.awt.image.BufferedImage

case class Image(imgCfg: ImageConfig) {

  def paint(lis: List[Pixel]): BufferedImage = {
      val img = new BufferedImage(imgCfg.width + 1, imgCfg.height + 1, BufferedImage.TYPE_INT_RGB)
      def paintTail(t: Tuple2[Int, Int], li: List[Pixel]):BufferedImage = t match {
        case Tuple2(imgCfg.width, imgCfg.height) => img
        case Tuple2(_, imgCfg.height) => {
          img.setRGB(t._1, t._2, li.head.color.getRGB)
          paintTail((t._1 + 1, 0), li.drop(1))
        }
        case _ => {
          img.setRGB(t._1, t._2, li.head.color.getRGB)
          paintTail((t._1, t._2 + 1), li.drop(1))
        }
      }
      paintTail((0, 0), lis)
  }
  
  def saveTo(img: BufferedImage) = {
    val fpath: String = "./output.png"
    ImageIO.write(img, imgCfg.form, new File(imgCfg.path))
  }
}