package com.xebia.snippet

import net.liftweb.util.Helpers._
import net.liftweb.util.PassThru

import scala.util.Random
import scala.xml.Text

class PassThruSnippet {

  private def fiftyFifty = Random.nextBoolean

  def render =
    if (fiftyFifty) "div *" #> "Congratulations! The content was changed"
    else PassThru


}