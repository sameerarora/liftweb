package com.xebia.persistence

import org.specs2.mutable._
import net.liftweb.squerylrecord.RecordTypeMode._
import MySchema._

class PlanetsSpec extends Specification with DBTestKit {

  "Solar System" >> {

    "know that Mars has two moons" >> InMemoryDB() {

      val mars = planets.insert(Planet.createRecord.name("Mars"))
      Satellite.createRecord.name("Phobos").planetId(mars.id).save
      Satellite.createRecord.name("Deimos").planetId(mars.id).save

      mars.satellites.size must_== 2
    }

  }
}
