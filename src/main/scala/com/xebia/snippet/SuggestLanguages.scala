package com.xebia.snippet

import net.liftmodules.widgets.autocomplete.AutoComplete
import net.liftweb.common.Loggable
import net.liftweb.http.S
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JsCmds.{Run, SetHtml}
import net.liftweb.util.Helpers._

import scala.xml.Text

class SuggestLanguages extends Loggable {

  val languages = List(
    "C", "C++", "Clojure", "CoffeeScript",
    "Java", "JavaScript",
    "POP-11", "Prolog", "Python", "Processing",
    "Scala", "Scheme", "Smalltalk", "SuperCollider"
  )

  def render = {

    val default = ""
    var chosen = ""

    def suggest(value: String, limit: Int = 20) ={
      println(s"Default limit is ..... $limit")
      languages.filter(_.toLowerCase.startsWith(value))
    }


    def submit(value: String): Unit =
      println("Value submitted: " + value)

    S.appendJs(Run(
      """
        |$('#autocomplete input[type=text]').bind('blur',function() {
        |  $(this).next().val($(this).val());
        |});
      """.stripMargin))

    /*
      With this in place, when the input field loses focus—for example, when the submit
      button is pressed—the value of the input field is stored as the value to be sent to the
      server.
     */
    def process(): JsCmd = SetHtml("result", Text(chosen))
    //TODO check the default limit
    "#autocomplete" #> AutoComplete(default, suggest, submit)
    /*
    The last line of this snippet shows the binding of the AutoComplete class, which takes:
      • A default value to show
      • A function that will produce suggestions from the text value entered—the result is
        a Seq[String] of suggestions
      • A function to call when the form is submitted
     */
  }


}
