package main.scala.misc

import scala.util.Random

case class rgba (red: Float, green: Float, blue: Float, alpha: Float) {
  //alpha channel is 1 when solid
  def *(s: Float): rgba = rgba(red * s, green * s, blue * s, alpha)
	def +(rgb: rgba): rgba = rgba(red + rgb.red, green + rgb.green, 
	    blue + rgb.blue, alpha)
	def -(rgb: rgba): rgba = rgba(red - rgb.red, green - rgb.green, 
	    blue - rgb.blue, alpha)
	def random(): rgba = rgba(Random.nextFloat(), Random.nextFloat(), Random.nextFloat()
	    , 1)
}