package com.xebia.snippet

import net.liftweb.http.js.JsCmds.FocusOnLoad
import net.liftweb.util.Helpers._

class FocusOnLoad {
  //Doobie

  def render = "name=username" #> FocusOnLoad(<input type="text"/>)

}
