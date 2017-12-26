package main.scala.algebra

case class Vector(coord: List[Float]) {
  
  def vecCorrespond(vec: Vector)(f: (Float, Float) => Float) =
	  Vector(higher.higherCorrespondProc(coord)(vec.coord)(f))
	
	def unary_-() = Vector(coord.map(-_)) 
	
	def ==(vec: Vector) = 
	  !(false /: higher.higherCorrespondProc(this.coord)(vec.coord)(_==_)) (_||_)
	
  def *(f: Float) = Vector(coord.map(_ * f))
  
	def /(f: Float) = Vector(coord.map(_ / f))
	
	def dot(vec: Vector) = (0f /: vecCorrespond(vec)(_*_).coord) {_+_}
  
	def normalize = Vector(coord.map(_ / this.length))
	
	def length = Math.sqrt((coord :\ 0f) {(x, xs) => x * x + xs}).toFloat
	
	def +(vec: Vector) = vecCorrespond(vec)(_+_)
	
	def -(vec: Vector) = vecCorrespond(vec)(_-_)
	
	def cross(vec: Vector) = {
	  if(vec.length == 3 && this.length == 3)
	    Vector(List(
	        this.coord.drop(1).head * vec.coord.drop(2).head - 
	        this.coord.drop(2).head * vec.coord.drop(1).head, 
	        this.coord.drop(2).head * vec.coord.drop(0).head - 
	        this.coord.drop(0).head * vec.coord.drop(2).head, 
	        this.coord.drop(0).head * vec.coord.drop(1).head - 
	        this.coord.drop(1).head * vec.coord.drop(0).head))
	  else
	    throw new RuntimeException("only 3-d vectors can have meaningful cross product")
	}
	
}