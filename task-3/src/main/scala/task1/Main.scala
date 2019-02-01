package task1

import scalaz._, Scalaz._
import scalaz.effect._
import scalaz.effect.IO._

import task1.modifyme.{Graph, Search, FoundEntries}

object Main {
  object Settings {
    sealed abstract class GraphType
    object GraphType {
      case object Constant extends GraphType
      case object ReadSmall extends GraphType
      case object ReadLarge  extends GraphType
    }

    val UseGraph: GraphType   = GraphType.ReadLarge
    val UseLessPois: Boolean  = false
  }

  case class Problem(
      timeLimit: Int,
      pointsOfInterest: Array[Int])

  def getEntries(graph: Graph[Int, Int], problem: Problem): Seq[FoundEntries[Int, Int]] = {
    implicit val timeLimit = modifyme.Infinity.fromValue(problem.timeLimit + 1)

    val emptyVisited   = () => modifyme.Visited.empty
    val emptyDistances = () => modifyme.Distances.empty
    val emptyQueue     = () => modifyme.PriorityQueue.intPriority[Search[Int]]
    val emptyEntries   = () => modifyme.FoundEntries.empty

    val poisSet = problem.pointsOfInterest.toSet

    val dijkstra = new Dijkstra[Int, Int](
      emptyQueue,
      emptyVisited,
      emptyDistances,
      Search.start,
      emptyEntries,
      graph,
      poisSet)

    problem.pointsOfInterest.par.map { p =>
      dijkstra(p)
    }.seq
  }

  def getCount[A, B](xs: Seq[FoundEntries[A, B]]): Int = {
    xs.par.map { entries => entries.nEntries }.sum
  }

  def writeOutputFile[A, B](fileName: String, xs: Seq[FoundEntries[A, B]], count: Int): IO[Unit] = IO {
    import java.io._
    val writer = new FileWriter(fileName)

    writer.write(count + "\n\n")

    xs.par.foreach { foundEntries =>
      if (!foundEntries.isEmpty) {
        writer.synchronized {
          writer.write(foundEntries.toString + "\n\n")
        }
      }
    }

    writer.close()
  }

  def readProblemDescription: IO[Problem] = IO {
    val fileName = Settings.UseGraph match {
      case Settings.GraphType.ReadSmall => "pois-small.dat"
      case other                        => "pois-large.dat"
    }

    val lines = scala.io.Source.fromFile(fileName).getLines

    val timeLimit = lines.next.trim.toInt

    val pois = lines.next.split("\\s").par.map { string =>
      string.toInt 
    }.toArray

    if (Settings.UseLessPois) {
      Problem(timeLimit, pois.take(1000))
    } else {
      Problem(timeLimit, pois)
    }
  }

  def time: IO[Long] = IO { System.currentTimeMillis }

  def getGraph: IO[Graph[Int, Int]] = {

    Settings.UseGraph match {
      case Settings.GraphType.Constant =>
        IO(ConstantGraph())

      case other =>
        val fileName = other match {
          case Settings.GraphType.ReadLarge => "connections-large.dat"
          case _                            => "connections-small.dat"
        }

        for {
          _            <- putStrLn("Reading connections...")
          genericGraph <- GenericGraph.intIntFromFile(fileName)
        } yield modifyme.Graph.from(genericGraph)
    }
  }

  case class Measured[A](ms: Long, data: A)

  def measuredEntries: IO[Measured[Seq[FoundEntries[Int, Int]]]] = for {
    graph        <- getGraph
    problem      <- readProblemDescription
    t1           <- time
    _            <- putStrLn("Calculating paths...")
    entries      =  getEntries(graph, problem)
    t2           <- time
  } yield Measured(t2 - t1, entries)


  def program: IO[Unit] = {
    for {
      measured     <- measuredEntries
      count        =  getCount(measured.data)
      _            <- putStrLn(s"Found ${count} paths in ${measured.ms} [ms]")
      _            <- putStr("Printing to file... ")
      t1           <- time
      _            <- writeOutputFile("paths.dat", measured.data, count)
      t2           <- time
      _            <- putStrLn(s"done in ${t2 - t1} [ms]")
    } yield ()
  }
      
  def main(args: Array[String]): Unit = {
    program.unsafePerformIO
  }
}
