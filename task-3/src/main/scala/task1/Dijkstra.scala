package task1

import task1.modifyme._

final class Dijkstra[A, W](
    val newPriorityQueue: () => PriorityQueue[W, Search[A]],
    val newVisited: () => Visited[A],
    val newDistances: () => Distances[A, W],
    val initialSearch: A => Search[A],
    val newEntries: () => FoundEntries[A, W],
    val graph: Graph[A, W],
    val pointsOfInterest: Set[A])(implicit monoid: Monoid[W], order: Order[W]) {

  type Queue = PriorityQueue[W, Search[A]]

  def apply(start: A): FoundEntries[A, W] = {
    val queue = newPriorityQueue()

    queue.enqueue(monoid.zero, initialSearch(start))

    mainLoop(
      queue,
      newDistances(),
      newVisited(),
      newEntries())
  }

  def mainLoop(
      queue: Queue, 
      distances: Distances[A, W],
      visited: Visited[A],
      entries: FoundEntries[A, W]): FoundEntries[A, W] = {

    while (!queue.isEmpty) {
      val search = queue.min
      val value  = queue.priority

      queue.deleteMin

      val node = search.node

      if (visited.notVisited(node)) {
        distances.updated(node, value)

        if (pointsOfInterest contains node) {
          entries.add(search.toEntry(value))
        }

        graph.neighbours(node).foreach { (label, distance) =>
          val newDistance: W = monoid.append(value, distance)

          if (order.lessThan(newDistance, distances.distanceAt(label))) {
            queue.enqueue(newDistance, search --> label)
            distances.updated(label, newDistance)
          }
        }

        visited.updated(node)
      }
    }

    entries
  }
}
