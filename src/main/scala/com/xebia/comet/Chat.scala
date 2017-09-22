package com.xebia.comet

import net.liftweb.actor.LiftActor
import net.liftweb.http.{CometActor, CometListener, ListenerManager}

class ChatClient extends CometActor with CometListener {

  def registerWith = ChatServer

  private var msgs : Vector[String] = Vector()

  override def lowPriority = {
    case xs: Vector[String] =>
      msgs = xs
      //TODO which component calls the render function
      reRender()
  }

  def render = "li *" #> msgs

}

object ChatServer extends LiftActor with ListenerManager {

  private var msgs = Vector("Welcome")

  def createUpdate = msgs

  override def lowPriority = {
    case s: String =>
      msgs :+= s
      updateListeners()
  }
}

