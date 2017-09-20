package com.xebia.snippet

import net.liftweb.http.js.JE
import net.liftweb.http.js.JsCmds.{Alert, RedirectTo}
import net.liftweb.util.Helpers._

object ClientSide {

  def render = "button [onclick]" #> "$(this).fadeOut()"

  /*
    rendering would be
    <button onclick="$(this).fadeOut()">Click Me</button>
   */

  def combined =
    "button [onclick]" #> (Alert("Here we go...") & RedirectTo("http://www.xebia.com"))
  /*
  Above CssSelector renders the following on the view
  <button onclick="alert(&quot;Here we go...&quot;);
    window.location = &quot;http://liftweb.net&quot;;">Click Me</button>
   */

  def greet =
    "button [onclick]" #> "greet(\"Hello\", 3)"//JE.Call("greet", "World!", 3)
  /*
  Note that JE.call accepts parameters "World" and 3 are the parameters used by client side function greet in the html
  On the client-side, we’d see:
  <button onclick="greet(&quot;World!&quot;,3)">Click Me For Greeting</button>
   */

}