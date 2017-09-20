package com.xebia.rest

import net.liftweb.http.LiftRules
import net.liftweb.http.rest.RestHelper
import net.liftweb.json.JsonAST._
import net.liftweb.json.JsonDSL._

object QuotesResource extends RestHelper {

  def init() {
    LiftRules.statelessDispatch.append(QuotesResource)
  }
  //TODO add a Json Post example

  serve {
    case "quotes" :: Nil JsonGet req =>
      ("quote" -> "A beach house isn't just real estate. It's a state of mind.") ~
        ("by" -> "Douglas Adams"): JValue
  }
}
