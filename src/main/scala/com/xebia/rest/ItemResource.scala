package com.xebia.rest

import net.liftweb.http.LiftRules
import net.liftweb.http.rest.RestHelper
import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._

object ItemResource extends RestHelper {

  def init() = LiftRules.statelessDispatch.append(ItemResource)

  def greet(name: String): JValue =
    "greeting" -> ("HELLO " + name.toUpperCase)

  serve {
    case "shout" :: Nil JsonPost ((json, req)) => greet((json \ "name").extract[String])

    case "yippie" :: Nil JsonGet _ => JString("Yuhuuu")

  }

}

