package task1.modifyme

// The only purpose of this class is to print a partial result from final entries
case class Entry[A, W](time: W, fullPath: List[A]) {
  // You MUST keep the following format: 
  //   [TIME] [source] [label1] ... [destination]
  // separated by spaces
  override def toString = time + " " + fullPath.mkString(" ")
}
