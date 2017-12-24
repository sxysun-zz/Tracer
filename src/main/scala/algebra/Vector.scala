package main.scala.algebra

case class Vector(coord: List[Float]) {
  def *(f: Float) = Vector(coord.map(_ * f))
	def /(f: Float) = Vector(coord.map(_ / f))
	def dot(vec: Vector) = (0f /: vecCorrespond(vec)(_*_).coord) {_+_}
	def normalize = Vector(coord.map(_ / this.length))
	def length = Math.sqrt((coord :\ 0f) {(x, xs) => x * x + xs}).toFloat
	def +(vec: Vector) = vecCorrespond(vec)(_+_)
	def -(vec: Vector) = vecCorrespond(vec)(_-_)
	def cross(vec: Vector): Vector = ???
  def vecCorrespond(vec: Vector)(f: (Float, Float) => Float):Vector =
	  Vector(higher.higherCorrespondProc(coord)(vec.coord)(f))
}