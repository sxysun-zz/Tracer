package main.scala.surfaces

import main.scala.misc.Material
import main.scala.misc.Texture
import main.scala.misc.ParametricRay
import main.scala.algebra.Vector
import java.awt.Color

abstract class Surface {
	def hit(surface: Surface, ray: ParametricRay): Boolean = ???
	def boundingBox(): Unit = ???
	def getNormal(surface: Surface, ray: ParametricRay, 
	    dis: Float, hitPoint: Float): Vector = ???
	def getHitPoint(surface: Surface, ray: ParametricRay, dis: Float): Float = ???
	def discriminant(surface: Surface, ray:ParametricRay): Float = ???
}
