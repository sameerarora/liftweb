package com.xebia.snippet

import net.liftweb.http.S
import net.liftweb.http.SHtml.{ajaxSubmit, hidden, text}
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds.SetHtml
import net.liftweb.util.Helpers._

import scala.xml.Text

class EchoForm {

  def render = {
    //TODO change it to val
    var savedParam = ""

    //def process(): JsCmd = SetHtml("result", Text(savedParam))

    def foo(paramFname:String) = () => paramFname

    "@fname" #> text(savedParam, foo) &
      "type=submit" #> ajaxSubmit("Click Me", () => SetHtml("result", Text(savedParam)))
    /*
    The render method is binding the fname input field to a function that will assign whatever
    the user enters to the variable savedParam .

    When the button is pressed, the process function is called, which will return the value
    of the savedParam back to the element in the HTML with an ID of result
     */
  }

  def renderViaHidden = {

    var name = ""

    def process(): JsCmd = SetHtml("result", Text(name))

    "@name" #> text(name, s => name = s) &
      "button *+" #> S.formGroup(1000) {
        hidden(process)
      }
  }

}
