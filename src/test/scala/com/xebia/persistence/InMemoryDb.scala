package com.xebia.persistence

import java.sql.DriverManager

import net.liftweb.common._
import net.liftweb.squerylrecord.RecordTypeMode._
import net.liftweb.squerylrecord.SquerylRecord
import org.specs2.execute.{AsResult, Result}
import org.specs2.mutable.Around
import org.squeryl.Session
import org.squeryl.adapters.H2Adapter

import scala.util.{Success, Try}

trait DBTestKit extends Loggable {

  Class.forName("org.h2.Driver")

  def configureH2() = {
    SquerylRecord.initWithSquerylSession(
      Session.create(
        DriverManager.getConnection("jdbc:h2:mem:dbname;DB_CLOSE_DELAY=-1", "sa", ""),
        new H2Adapter)
    )
  }

  def createDb() {
    inTransaction {
      Try {
        MySchema.drop
        MySchema.create
      } match {
        case Success(_) =>
        case scala.util.Failure(ex) => throw ex
      }
    }
  }

}

case class InMemoryDB() extends Around with DBTestKit {

  override def around[T](testToRun: => T)(implicit ev: AsResult[T]): Result = {
    configureH2
    createDb
    inTransaction {
      Session.currentSession.setLogger(s => logger.info(s))
      AsResult(testToRun)
    }
  }
}




