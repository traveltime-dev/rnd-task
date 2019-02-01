package task1.modifyme

/**
 * Stores the distances (of type `W) to the labels `A`.
 */
abstract class Distances[A, W] {
  /*
   * Retrieves the stored distance `W` at label `W` or Infinity[W] if not reached.
   */
  def distanceAt(a: A): W
  /*
   * Updates the distance for label `A` with the value `W`
   */
  def updated(label: A, value: W): Unit
}

object Distances {
  def empty(implicit ev: Infinity[Int]): Distances[Int, Int] = new MapDistances
}

class MapDistances[A, W](implicit ev: Infinity[W]) extends Distances[A, W] {
  var underlying = Map.empty[A, W]

  def distanceAt(a: A): W = {
    underlying.getOrElse(a, Infinity[W])
  }

  def updated(label: A, value: W): Unit = {
    this.underlying = underlying.updated(label, value)
  }
}
