package main.scala.anim

import main.scala.algebra._
import test.scala.tracer.StarWarsTest

object Motion {
  def starWarsTIEMotion(time: Float, now: Vector): Vector = {
    now
  }
  
  def starWarsFalconMotion(time: Float, now: Vector): Vector = {
    now
  }
  
  def closerImageMotion(time: Float, now: Vector): Vector = {
    (StarWarsTest.falconPosition - Vector.apply(List(-.5f, .1f, -.5f))) * time + 
    Vector.apply(List(-.5f, .1f, -.5f))
  }
}