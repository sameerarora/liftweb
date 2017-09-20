package com.xebia.snippet

import net.liftweb.common.{Empty, Full, ParamFailure}
import net.liftweb.http.S
import net.liftweb.util.PassThru
import net.liftweb.util.Helpers._

class ClassicForm {

  //TODO change fname to val
  var fname = ""

  def render = {
    if(fname.isEmpty) PassThru
    else {
      "#result" #> s"$fname"

    } //TODO check this example
    //TODO how can we control how S.notice works ?
  }

  def submit = {
    val value = S.param("firstName")
    value match {
      case Full(name) =>
        S.notice("Hello " + name)
        S.redirectTo("/form-example")//TODO how can parameters be passed
      case _ =>
        PassThru
    }
  }

}
