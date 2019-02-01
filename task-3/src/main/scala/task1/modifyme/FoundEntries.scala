package task1.modifyme

/*
 * A collection of found Entries[A, W]. The only purpose of this class is to collect Entries and then print
 * them in the proper output format
 */
abstract class FoundEntries[A, W] {
  def add(entry: Entry[A, W]): Unit
  // how many entries were added
  def nEntries: Int
  def isEmpty: Boolean = nEntries == 0
  // See ListFoundEntries how to correctly implement the toString method
  def toString: String
}
object FoundEntries {
  def empty: FoundEntries[Int, Int] = new ListFoundEntries[Int, Int]
}

class ListFoundEntries[A, W] extends FoundEntries[A, W] {
  var entries = List.empty[Entry[A, W]]

  def add(entry: Entry[A, W]): Unit = 
    entries ::= entry

  // we don't count the initial entry because it's the origin
  def nEntries = entries.length - 1

  override def toString = {
    // the first entry added is always the origin which we need to skip
    entries.toList.dropRight(1).mkString("\n")
  }
}
