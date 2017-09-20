package com.xebia.persistence

import java.sql.DriverManager

import org.squeryl.Session
import org.squeryl.adapters.H2Adapter
import net.liftweb.util.StringHelpers
import net.liftweb.common._
import net.liftweb.http.{LiftSession, Req, S}
import net.liftweb.squerylrecord.SquerylRecord
import net.liftweb.squerylrecord.RecordTypeMode._
import org.specs2.mutable.Around
import org.specs2.execute.{AsResult, Result}
import org.specs2.matcher.MatchResult

trait TestLiftSession {
  def session = new LiftSession("", StringHelpers.randomString(20), Empty)

  def inSession[T](a: => T): T = S.init(Full(Req.nil), session)(a)
}

trait DBTestKit extends Loggable {

  Class.forName("org.h2.Driver")

  Logger.setup = Full(net.liftweb.util.LoggingAutoConfigurer())
  Logger.setup.foreach {
    _.apply()
  }

  def configureH2() = {
    SquerylRecord.initWithSquerylSession(
      Session.create(
        DriverManager.getConnection("jdbc:h2:mem:dbname;DB_CLOSE_DELAY=-1", "sa", ""),
        new H2Adapter)
    )
  }

  def createDb() {
    inTransaction {
      try {
        MySchema.drop
        MySchema.create
      } catch {
        case e: Throwable =>
          logger.error("DB Schema error", e)
          throw e
      }
    }
  }

}

case class InMemoryDB() extends Around with DBTestKit with TestLiftSession {

  override def around[T](testToRun: => T)(implicit ev: AsResult[T]): Result = {
    configureH2
    createDb
    inSession {
      inTransaction {
        AsResult(testToRun)
      }
    }
  }
}




