package com.xebia.robot

import net.liftweb.http.Req

object RobotDetector {
  val botNames =
    "Googlebot" ::
      "Mediapartners-Google" ::
      "AdsBot-Google" :: Nil

  def known_?(userAgent: String) =
    botNames.exists(userAgent contains _)

  def googlebot_?(r: Req): Boolean =
    r.header("User-Agent").exists(known_?)
}