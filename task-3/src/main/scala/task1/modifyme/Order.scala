package task1.modifyme

abstract class Order[A] {
  // true if left < right
  def lessThan(left: A, right: A): Boolean
}

object Order {
  implicit val intHasOrder = new Order[Int] {
    override def lessThan(left: Int, right: Int) = left < right
  }
}
