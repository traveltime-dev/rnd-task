package task1.modifyme

/**
 * Hint: you will need an efficient graph implementation 
 * - think about memory locality.
 * - change it sparingly as `task1.GenericGraph` and `task1.Dijkstra` which you cannot modify still needs to
 *   compile.
 * - your graph must correctly handle an unknown label
 */

// a graph with labels of type A and weights of type W
trait Graph[A, W] {
  def neighbours(label: A): Neighbours[A, W]
}

object Graph {
  // return your instance of the graph reading from the generic graph
  def from(genericGraph: task1.GenericGraph[Int, Int]): Graph[Int, Int] = 
    genericGraph
}

// neighbours of a label (of type A) of a graph with weights of type W
trait Neighbours[A, W] {
  // perform `f` on each neighbour (label, weight). The order of traversal doesn't matter as long as all
  // neighbours are reached.
  def foreach(f: (A, W) => Unit): Unit
}

