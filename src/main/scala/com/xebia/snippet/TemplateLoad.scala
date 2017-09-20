package com.xebia.snippet

import net.liftweb.common.Loggable
import net.liftweb.http.js.JsCmds.{SetHtml, _Noop}
import net.liftweb.http.{SHtml, Templates}
import net.liftweb.http.js.JsCmd
import net.liftweb.util.Helpers._

object TemplateLoad extends Loggable {

  def content: JsCmd = Templates("index" :: Nil).map(ns => SetHtml("inject", ns)) openOr _Noop

  def render = {

    "button [onclick]" #> SHtml.ajaxInvoke(content _)
  }
}
