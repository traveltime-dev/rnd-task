package task1.modifyme

/*
 * A collection of visited labels `A`.
 */
abstract class Visited[A] {
  /*
   * Should return `true` if `this.updated` was never called with `a`.
   */
  def notVisited(a: A): Boolean
  /*
   * Remember that we visited label `a`
   */
  def updated(a: A): Unit
}

object Visited {
  def empty = new SetVisited(new scala.collection.mutable.HashSet[Int])
}

case class SetVisited(val underlying: scala.collection.mutable.Set[Int]) extends Visited[Int] {
  override def notVisited(a: Int) = !underlying.contains(a)
  override def updated(a: Int): Unit= {
    underlying += a
    ()
  }
}

