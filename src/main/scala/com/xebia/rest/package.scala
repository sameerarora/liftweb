package com.xebia

import scala.xml.Node

package object rest {

  implicit def toXml(issue: Issue): Node =
    <issue>
      <id>{issue.id}</id>
      <state>{issue.state}</state>
      <severity>{issue.severity}</severity>
    </issue>

  implicit def toXml(issues: Seq[Issue]): Node =
    <issues>{issues.map(toXml)}</issues>


}
