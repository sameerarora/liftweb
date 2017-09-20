package com.xebia.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.{LiftRules, XmlResponse}


object IssuesResource extends RestHelper {

  def init(): Unit = {
    LiftRules.statelessDispatch.append(IssuesResource)
  }

  serve("issues" / "by-state" prefix {
    case "open" :: Nil XmlGet _ =>
      XmlResponse(IssuesDatabase.allIssues.filter(_.state == "open"))
    case "closed" :: Nil XmlGet _ =>
      XmlResponse(IssuesDatabase.allIssues.filter(_.state == "closed"))
    case "resolved" :: Nil XmlGet _ =>
      XmlResponse(IssuesDatabase.allIssues.filter(_.state == "resolved"))
  })

}

case class Issue(id: String, state: String, severity: String)

object IssuesDatabase {

  val allIssues = List(Issue("DEPL-1122", "open", "Major"),
    Issue("DEPL-1112", "resolved", "Major"),
    Issue("DEPL-1222", "closed", "Major"),
    Issue("DEPL-1242", "closed", "Minor"))

}


