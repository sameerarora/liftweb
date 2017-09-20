package com.xebia.comet

import net.liftweb.actor.LiftActor
import net.liftweb.http.{CometActor, CometListener, ListenerManager}

class CommentRenderer extends CometActor with CometListener {

  private var comments = Vector[String]()

  def registerWith = CommentServer

  override def lowPriority = {
    case c: Vector[String] => {
      comments = c
      reRender()
    }
  }

  override def render = "li *" #> comments
}

object CommentServer extends LiftActor with ListenerManager {

  private var comments = Vector[String]()

  override def createUpdate = comments

  override def lowPriority = {
    case comment: String => {
      comments :+= comment
      updateListeners()
    }
  }
}