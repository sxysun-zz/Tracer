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
  def higherCorrespondProc[A, B, C](l1: List[A])
	    (l2: List[B])(f: (A, B) => C): List[C] = {
	  def higherTail(n: Int, r: List[C]): List[C] = n match {
	    case -1 => r
	    case _ => 
	      higherTail(n - 1, r :+ f(l1.reverse.drop(n).head, l2.reverse.drop(n).head))
	  }
	  higherTail(Math.min(l1.length, l2.length) - 1, List[C]())
	}
  def vecCorrespond(vec: Vector)(f: (Float, Float) => Float):Vector =
	  Vector(higherCorrespondProc(coord)(vec.coord)(f))
}