package donotmodifyme

object Scenario2 {
  abstract class Datum {
    def key: String
    def serializeContent: Array[Byte]
  }
  object Datum {
    def deserialize(bytes: Array[Byte]): Either[Throwable, Datum] = ???
  }

  /**
   * An abstraction of some database. The database implementation is not thread safe.
   */
  object database {
    /**
     * A representation of the credentials needed to connect to the database.
     */
    abstract class DatabaseCredentials

    class DatabaseException extends Throwable

    object DatabaseConnection {
      /**
       * Opens up a database connection or throws an DatabaseException. 
       *
       * An opened database needs to be closed, otherwise reopening it might not be possible.        
       */
      def open(credentials: DatabaseCredentials): DatabaseConnection = ???
    } 

    abstract class DatabaseConnection {
      /**
       * Fetch the data under `key`, null if none found or throw DatabaseException on reading error
       */
      def fetch(key: String): Array[Byte]

      /**
       * Writes the data under key. Throws a DatabaseException on writing error.
       */
      def put(key: String, data: Array[Byte]): Unit

      /**
       * Returns the keys to this database. Throws DatabaseException on reading error
       */
      def keys: Iterator[String]

      /**
       * Releases the resources.
       */
      def close(): Unit
    }
  }
}
