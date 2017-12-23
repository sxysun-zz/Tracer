package main.scala.surfaces

import main.scala.misc.Material
import main.scala.misc.Texture
import main.scala.misc.ParametricRay
import main.scala.algebra.Vector
import java.awt.Color

case class SphereSurface (radius: Float, center: Vector, 
    oriColor: Color) extends Surface{
  
	def hit(sphere: SphereSurface, ray:ParametricRay): Boolean = 
	  return discriminant(sphere, ray) >= 0
	
	def discriminant(sphere: SphereSurface, ray:ParametricRay): Float = {
	  val temp = (ray.viewPosition - sphere.center)
		return ( (ray.endPosition dot temp) * (ray.endPosition dot temp) - 
		(ray.endPosition dot ray.endPosition) * ((temp dot temp)
		    - sphere.radius * sphere.radius) )
	}
	
	def getNormal(sphere: SphereSurface, ray: ParametricRay, 
	    dis: Float, hitPoint: Float): Vector = {
		if(dis >= 0) 
		  return (ray.position(hitPoint, 
		      ray.viewPosition, ray.endPosition) - this.center) / this.radius
		else return Vector.apply(List(0f,0f,0f))
	}
	
	def getHitPoint(sphere: SphereSurface, ray: ParametricRay, dis: Float): Float = {
	  if(dis < 0) -1f
	  else {
	    val temp = (ray.viewPosition - sphere.center)	  
	    Math.min(((((ray.endPosition dot temp) * -1) + 
	    Math.sqrt(dis)) / (ray.endPosition dot ray.endPosition)), 
	    ((((ray.endPosition dot temp) * -1) - 
	    Math.sqrt(dis)) / (ray.endPosition dot ray.endPosition))).toFloat
	  }
	}
}