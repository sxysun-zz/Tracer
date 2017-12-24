package main.scala.misc

import main.scala.algebra.Vector

case class ParametricRay (t: Float, viewPosition: Vector, endPosition: Vector) {
	def position(s: Float): Vector = {
		this.viewPosition + (this.endPosition - this.viewPosition) * s
	}
	//e -> viewPosition
	//d -> endPosition
	// if t1 is less than t2, it is closer to the viewPosition
	// if t is less than 0, then it is behind the eye
}