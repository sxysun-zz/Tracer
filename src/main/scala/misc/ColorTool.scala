package main.scala.misc

import java.awt.Color

object ColorTool {
  def colorMean(l: List[Color]): Color = 
    new Color(((0 /: l) {(x, xs) => x + xs.getRed}) / l.length, 
        ((0 /: l) {(x, xs) => x + xs.getGreen}) / l.length,
        ((0 /: l) {(x, xs) => x + xs.getBlue}) / l.length)
  
  def colorToList(color: Color): List[Float] = 
    List(color.getRed.toFloat/255, 
          color.getGreen.toFloat/255, color.getBlue.toFloat/255*2)
  
  def listToColor(l: List[Float]): Color = 
    new Color(254 * l.head, 254 * l.drop(1).head, 254 * l.drop(2).head)
  
  def listClamp(li: List[Float]) =
    List(
          Math.max(0, Math.min(254, li.head)), 
          Math.max(0, Math.min(254, li.drop(1).head)), 
          Math.max(0, Math.min(254, li.drop(2).head))
    )
}