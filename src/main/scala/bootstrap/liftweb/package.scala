package bootstrap

import java.sql.DriverManager

import com.xebia.rest.{ImageUploadResource, IssuesResource, ItemResource, QuotesResource}
import net.liftmodules.JQueryModule
import net.liftweb.common.Full
import net.liftweb.http.js.jquery.JQueryArtifacts
import net.liftweb.http.{ContentSecurityPolicy, ContentSourceRestriction, LiftRules, NotFoundAsTemplate, ParsePath, Req, SecurityRules}
import net.liftweb.util.{NamedPF, Props}

package object liftweb {

  def securityRules: SecurityRules = {
    SecurityRules(content = Some(ContentSecurityPolicy(
      defaultSources = List(
        ContentSourceRestriction.All,
        ContentSourceRestriction.UnsafeEval,
        ContentSourceRestriction.UnsafeInline
      ),
      styleSources = List(
        ContentSourceRestriction.Self,
        ContentSourceRestriction.UnsafeInline,
        ContentSourceRestriction.Host("https://maxcdn.bootstrapcdn.com/bootstrap/"),
        ContentSourceRestriction.Host("https://fonts.googleapis.com/css")
      ),
      fontSources = List(
        ContentSourceRestriction.Self,
        ContentSourceRestriction.Host("https://maxcdn.bootstrapcdn.com/bootstrap/"),
        ContentSourceRestriction.Host("https://fonts.gstatic.com/")
      ),
      scriptSources = List(
        ContentSourceRestriction.Self,
        ContentSourceRestriction.Host("http://code.jquery.com/"),
        ContentSourceRestriction.UnsafeInline,
        ContentSourceRestriction.UnsafeEval
      )
    )))
  }

  def initResources = {
    IssuesResource.init()
    QuotesResource.init()
    ImageUploadResource.init()
    ItemResource.init()
  }

  def addAjaxRules = {
    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart = Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd = Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)
  }

  def initJqueryModule = {
    LiftRules.jsArtifacts = JQueryArtifacts
    JQueryModule.InitParam.JQuery = JQueryModule.JQuery191
    JQueryModule.init()
  }

  def add404Handler = {
    LiftRules.uriNotFound.prepend(NamedPF("404handler") {
      case (_, _) =>
        NotFoundAsTemplate(ParsePath(List("pagenotfound"), "html", true, false))
    })
  }

  def addRequestDebugging: Unit = LiftRules.onBeginServicing.append {
    case r@Req("upload" :: _, _, _) => println("Received: " + r)
  }

  def initH2() {

    Class.forName("org.h2.Driver")

    import org.squeryl.adapters.H2Adapter
    import net.liftweb.squerylrecord.SquerylRecord
    import org.squeryl.Session

    SquerylRecord.initWithSquerylSession(Session.create(
      DriverManager.getConnection("jdbc:h2:mem:lift_proto;DB_CLOSE_DELAY=-1", "sa", ""),
      new H2Adapter))
  }

}
