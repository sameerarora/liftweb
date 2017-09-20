package com.xebia.snippet

import com.xebia.comet.{CommentServer}
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds.SetValById

object CommentSnippet {

  def render = SHtml.onSubmit(s => {
    CommentServer ! s
    SetValById("message", "")
  })
}
