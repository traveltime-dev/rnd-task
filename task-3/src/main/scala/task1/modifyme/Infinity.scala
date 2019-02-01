package task1.modifyme

trait Infinity[A] {
  def infinity: A
}

object Infinity {
  def apply[A](implicit ev: Infinity[A]): A = ev.infinity

  def fromValue[A](a: A): Infinity[A] = new Infinity[A] {
    override def infinity: A = a
  }
}
