package task2

/**
 * We need a fast peekMin() method for `MultiQueue`.
 */
class Queue[A >: Null] private (
    underlying: scala.collection.mutable.PriorityQueue[A])(implicit ord: Ordering[A]) {

  var entry: A = null

  @inline private[this] def maybeUpdateEntry(): Unit = {
    if (entry == null && !underlying.isEmpty) {
      entry = underlying.dequeue()
    }
  }

  def isEmpty = entry == null && underlying.isEmpty

  /*
   * returns the element with the lowest priority or null if this queue is empty
   */
  def deleteMin(): A = {
    val returned = entry

    if (underlying.isEmpty) {
      entry = null
    } else {
      entry = underlying.dequeue
    }

    returned 
  }

  def peekMin: A = {
    maybeUpdateEntry()
    entry
  }

  def enqueue(queued: A): Unit = {
    if (entry == null) {
      entry = queued
    } else if (ord.gt(entry, queued)) {
      underlying.enqueue(entry)
      entry = queued
    } else {
      underlying.enqueue(queued)
    }

    ()
  }
}

object Queue {
  def empty[A >: Null](implicit ord: Ordering[A]): Queue[A] = {
    new Queue[A](new scala.collection.mutable.PriorityQueue()(ord.reverse))
  }
}
