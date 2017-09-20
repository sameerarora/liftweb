package com.xebia.snippet

import net.liftweb.http.{InMemoryResponse, LiftResponse, ResponseShortcutException, SHtml}
import net.liftweb.util.Helpers._


import scala.xml.Text

class DownloadLink {

  val lines =
    "Some Random text" :: "to be added in a text file" :: "Another element" :: Nil


  def render =
    ".lines" #> lines.map(line => ".line" #> line) & "a" #> downloadLink

  /*
  <span class="lines">
                    <span class="line">line goes here</span> <br />
                </span>
   */
  def textFile : LiftResponse =
    InMemoryResponse(
      lines.mkString("\n").getBytes("UTF-8"),
      "Content-Type" -> "text/plain; charset=utf8" ::
        "Content-Disposition" -> "attachment; filename=\"lines.txt\"" :: Nil,
      cookies=Nil, 200)

  def downloadLink =
  //SHtml.link generates a link and executes a function you supply before following the link
  {
   SHtml.link("#hello", () => throw new ResponseShortcutException(textFile), Text("Attachment"))
  }

  // SHtml.link works by generating a URL that Lift associates with the function you give
  //it. On a page called downloadlink , the URL will look something like:
  //  downloadlink?F845451240716XSXE3G=_#notused

}
