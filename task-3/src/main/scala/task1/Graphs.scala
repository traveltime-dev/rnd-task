package task1

import task1.modifyme.Graph

import scalaz._, Scalaz._
import scalaz.effect._
import scala.io.Source

/*
 * A graph where consequtive ids are neighbours with each other (of weight `ConstantGraph.Weight` with
 * shortcuts between ids.
 */
case class ConstantGraph() extends Graph[Int, Int] {
  def neighbours(id: Int) = ConstantGraph.Neighbours(id)
}
object ConstantGraph {
  val Weight = 100

  case class Neighbours(id: Int) extends task1.modifyme.Neighbours[Int, Int] {
    def foreach(f: (Int, Int) => Unit): Unit = {
      def <--->(delta: Int) = {
        val right = id + delta
        f(right, Weight)

        val left = id - delta
        if (left >= 0) {
          f(left, Weight)
        }
      }

      <--->(1)
    }
  } 
}

case class GenericGraph[A, W](points: Map[A, GenericGraph.Neighbours[A, W]]) extends Graph[A, W] {
  override def neighbours(id: A): task1.modifyme.Neighbours[A, W] = 
    points.get(id).getOrElse(GenericGraph.Neighbours.empty[A, W])
}

object GenericGraph {
  def intIntFromFile(file: String): IO[GenericGraph[Int, Int]] = IO {
    val t1 = System.currentTimeMillis

    val lines = Source.fromFile(file).getLines.drop(2).toVector.par

    val points: Map[Int, Neighbours[Int, Int]] = lines.aggregate(Map.empty[Int, Neighbours[Int, Int]])({ (points, line) =>
      val tokens = line.split("\\s")

      val n = Neighbours.single((tokens(1).toInt, tokens(2).toInt))
      Map(tokens(0).toInt -> n) |+| points  
    }, { (x1, x2) =>
      x1 |+| x2
    })

    val time = System.currentTimeMillis - t1
    println(s"Graph created in: ${time} [ms]")

    GenericGraph(points)
  }

  class Neighbours[A, W] private (val weights: List[(A, W)] @@ Neighbours.Distinct) 
      extends task1.modifyme.Neighbours[A, W] {

    def elements: List[(A, W)] = Tag.unwrap(weights)

    def foreach(f: (A, W) => Unit): Unit = {
      elements.foreach { case (a, w) =>
        f(a, w)
      }
    }

    override def toString = s"Neighbours($weights)"
  }

  object Neighbours {
    sealed trait Distinct

    def single[A, W](a: (A, W)): Neighbours[A, W] = new Neighbours(Tag(List(a)))

    def empty[A, W]: Neighbours[A, W] = instance.zero

    implicit def instance[A, W]: Monoid[Neighbours[A, W]] = new Monoid[Neighbours[A, W]] {
      def zero: Neighbours[A, W] = new Neighbours[A, W](Tag(List.empty[(A, W)]))
      def append(n1: Neighbours[A, W], n2: => Neighbours[A, W]): Neighbours[A, W] = {
        val xs: List[(A, W)] = {
          Tag.unwrap(n1.weights) ++ Tag.unwrap(n2.weights)
        }
        new Neighbours[A, W](Tag(xs))
      }
    }
  }
}
