package bootstrap.liftweb

import com.xebia.persistence.MySchema
import net.liftmodules.widgets.autocomplete.AutoComplete
import net.liftweb.http.{Html5Properties, InternalServerErrorResponse, LiftRules, NotFoundAsTemplate, ParsePath, Req, S}
import net.liftweb.sitemap.{Menu, SiteMap}
import net.liftweb.util.{LiftFlowOfControlException, LoanWrapper}

import scala.util.{Failure, Success, Try}

class Boot {

  def boot {
    // path where it needs to look up the snippets
    LiftRules.addToPackages("com.xebia")

    LiftRules.securityRules = () =>
      securityRules.copy(enforceInDevMode = true, enforceInOtherModes = true)

    add404Handler

    initH2

    // Build SiteMap
    def sitemap(): SiteMap = SiteMap(
      Menu.i("Home") / "index",
      Menu.i("Pass Thru Example") / "passthru",
      Menu.i("Hello World") / "hello",
      Menu.i("Classic Form") / "form-example",
      Menu.i("Echo Form") / "echoform"
    )

    LiftRules.maxMimeFileSize = 1024 * 1024 * 1024

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    addAjaxRules

    initJqueryModule

    AutoComplete.init()

    initResources

    addRequestDebugging

    LiftRules.exceptionHandler.prepend {
      case (runMode, request, exception) =>
        InternalServerErrorResponse()
    }
    import net.liftweb.squerylrecord.RecordTypeMode._
    S.addAround(new LoanWrapper {
      override def apply[T](f: => T): T = {

        val result = inTransaction {
          // If you want to enable logging everywhere:
          // import org.squeryl.Session
          // Session.currentSession.setLogger( s => logger.info(s) )
          Try(f) match {
            case Success(r) => r
            case Failure(ex) => throw ex
          }
          try {
            Right(f)
          } catch {
            case e: LiftFlowOfControlException => Left(e)
          }
        }

        result match {
          case Right(r) => r
          case Left(exception) => throw exception
        }

      }
    })

    inTransaction {
      MySchema.printDdl
      MySchema.create
    }
  }

}
