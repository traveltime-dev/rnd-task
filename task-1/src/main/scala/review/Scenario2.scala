package review

import donotmodifyme.Scenario2._
import database._

/*
 * We need to store `donotmodifyme.Scenario2.Datum` in an efficient manner. We found a very efficient database
 * implementation (donotmodifyme.Scenario2.database.*` now we need to provide a well behaved wrapper for a
 * java the libary `database`.
 */
object Scenario2 {
  class DatabaseUser(credentials: DatabaseCredentials) {
    def obtain: DatabaseConnection = {
      DatabaseConnection.open(credentials)
    }

    def put(connection: DatabaseConnection, datum: Datum) = {
      connection.put(datum.key, datum.serializeContent)
    }

    def getAll(connection: DatabaseConnection): Seq[Datum] = {
      val keys = connection.keys.toList

      val builder = Seq.newBuilder[Datum]
      keys.foreach { key =>
        val bytes = connection.fetch(key)
        Datum.deserialize(bytes) match {
          case Left(error) => 
          case Right(datum) => 
            builder += datum
        }
      }

      builder.result
    }

    def close(connection: DatabaseConnection): Unit = {
      connection.close
    }
  }
}
