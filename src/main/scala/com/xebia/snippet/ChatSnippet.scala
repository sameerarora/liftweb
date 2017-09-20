package com.xebia.snippet

import com.xebia.comet.ChatServer
import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds.SetValById

object ChatSnippet {

  def render = SHtml.onSubmit(s => {
    ChatServer ! s
    SetValById("message", "")
  })
}
