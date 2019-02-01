package task1.modifyme

abstract class Monoid[A] {
  // identiy element
  def zero: A
  // symetric append operation
  def append(a1: A, a2: => A): A
}

object Monoid {
  implicit val intHasMonoid = new Monoid[Int] {
    override val zero = 0
    override def append(a1: Int, a2: => Int) = a1 + a2
  }
}
