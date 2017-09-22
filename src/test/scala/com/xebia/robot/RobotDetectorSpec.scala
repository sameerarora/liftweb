package com.xebia.robot

import net.liftweb.mocks.MockHttpServletRequest
import net.liftweb.mockweb.MockWeb
import org.scalatest.{Matchers, WordSpec}


class RobotDetectorSpec extends WordSpec with Matchers {

  "Google Bot Detector" should {
    "spot a web crawler" in {
      val userAgent = "Mozilla/5.0 (compatible; Googlebot/2.1)"
      // Mock a request with the right header:
      val http = new MockHttpServletRequest()
      http.headers = Map("User-Agent" -> List(userAgent))
      // Test with a Lift Req:
      MockWeb.testReq(http) { r =>
        RobotDetector.googlebot_?(r) shouldBe true
      }
    }
  }

}
