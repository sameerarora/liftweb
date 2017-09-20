package com.xebia.rest

import net.liftweb.http.rest.RestHelper
import net.liftweb.http.{LiftRules, Req}


object ImageUploadResource extends RestHelper {

  def init(): Unit = {
    LiftRules.statelessDispatch.append(ImageUploadResource)
  }
//curl -X POST --data-binary "@Lift-logo.jpg" -H 'Content-Type: image/jpg' http://127.0.0.1:8081/upload
  serve {
    case "upload" :: Nil Post req =>
      for {
        bodyBytes <- req.body
      } yield <info>Received {bodyBytes.length} bytes</info>

    case "jpg" :: Nil Post JPeg(req) =>
      for {
        bodyBytes <- req.body
      } yield <info>Jpeg Received {bodyBytes.length} bytes</info>

  }

  object JPeg {
    def unapply(req: Req): Option[Req] =
      req.contentType.filter(_ == "image/jpg").map(_ => req)
  }

}
