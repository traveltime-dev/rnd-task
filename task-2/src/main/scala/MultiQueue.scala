package task2

class MultiQueue[A >: Null] private (
  // nQueues is guaranteed to be at least 2
  nQueues: Int
  // other arguments here if needed
  )(implicit ordering: Ordering[A]) {

  // XXX: You'll need ordering but it's not referenced anywhere and the compiler, rightly, complains about
  // that. You can safely delete this method when you're done with the class.
  def xxxRemoveMe = ordering

  def isEmpty: Boolean = ???
  def size: Int        = ???

  def insert(element: A): Unit = {
    ???
  }

  /*
   * Smallest elements (non-strictly) first.
   */
  def deleteMin(): A = {
    ???
  }
}

object MultiQueue {
  // You can ignore the scaling factor and the actuall amount of processors just use the given nQueues.
  def empty[A >: Null](nQueues: Int)(implicit ordering: Ordering[A]): MultiQueue[A] = {
    new MultiQueue(math.max(2, nQueues))
  }
}
