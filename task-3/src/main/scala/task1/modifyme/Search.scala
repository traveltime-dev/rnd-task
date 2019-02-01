package task1.modifyme

/*
 * A representation of the Dijkstra search space. A path.
 */
case class Search[A](
    // the last visited label 
    node: A, 
    // the reverse path that lead to `node` from the source
    reversePath: List[A]) {

  // extend the path the new node creating a new path
  def --> (node: A): Search[A] = Search(node, this.node :: reversePath)

  // 
  def toEntry[W](time: W): Entry[A, W] = Entry(time, (node :: reversePath).reverse)
}

object Search {
  def start(node: Int): Search[Int] = new Search(node, Nil)
}
