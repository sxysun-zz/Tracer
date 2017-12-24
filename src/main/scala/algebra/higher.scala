package main.scala.algebra

object higher {
  def higherCorrespondProc[A, B, C](l1: List[A])
	    (l2: List[B])(f: (A, B) => C): List[C] = {
	  def higherTail(n: Int, r: List[C]): List[C] = n match {
	    case -1 => r
	    case _ => 
	      higherTail(n - 1, r :+ f(l1.reverse.drop(n).head, l2.reverse.drop(n).head))
	  }
	  higherTail(Math.min(l1.length, l2.length) - 1, List[C]())
	}
}