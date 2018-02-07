package main.scala.surfaces

import main.scala.misc.Material
import main.scala.misc.Texture
import main.scala.misc.ParametricRay
import main.scala.algebra.Vector
import java.awt.Color

case class SphereSurface (
    radius: Float, 
    center: Vector, 
    oriColor: Color,
    specularColor: Color,
    ambientColor: Color, 
    phongExponent: Float){
  
  def hit(ray:ParametricRay): Boolean = 
	  return discriminant(ray) >= 0
	
	def discriminant(ray:ParametricRay): Float = {
	  val temp = (ray.viewPosition - this.center)
		return ( (ray.endPosition dot temp) * (ray.endPosition dot temp) - 
		(ray.endPosition dot ray.endPosition) * ((temp dot temp)
		    - this.radius * this.radius) )
	}
	
	def getNormal(ray: ParametricRay, hitPoint: Float): Vector = {
	  val dis = this.discriminant(ray)
		if(dis >= 0) 
		  return (ray.position(hitPoint) - this.center) / this.radius
		else return Vector.apply(List(0f,0f,0f))
	}
	
	def getHitPoint(ray: ParametricRay): Float = {
	  val dis = this.discriminant(ray)
	  if(dis < 0) -1f
	  else {
	    val temp = (ray.viewPosition - this.center)	  
	    Math.min(((((ray.endPosition dot temp) * -1) + 
	    Math.sqrt(dis)) / (ray.endPosition dot ray.endPosition)), 
	    ((((ray.endPosition dot temp) * -1) - 
	    Math.sqrt(dis)) / (ray.endPosition dot ray.endPosition))).toFloat
	  }
	}
}