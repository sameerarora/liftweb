import java.net.URL

import scala.concurrent.Future
import scala.io.Source
import scala.util.Success

object EitherTest extends App {

  def getContent(url: URL): Either[String, Source] =
    if (url.getHost.contains("google"))
      Left("Requested URL is blocked!")
    else
      Right(Source.fromURL(url))

  getContent(new URL("http://google.com")) match {
    case Left(msg) => println(msg)
    case Right(source) => source.getLines.foreach(println)
  }

  val pf: PartialFunction[String, String] = {
    case a@"Sameer" => s"Hello $a"
  }
  pf.isDefinedAt("")

}
