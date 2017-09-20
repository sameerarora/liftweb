package com.xebia.snippet

import net.liftweb.common.{Box, Empty, Full, Loggable}
import net.liftweb.http.FileParamHolder
import net.liftweb.http.SHtml._
import net.liftweb.util.Helpers._

class FileUpload extends Loggable {

  def render = {

    var upload: Box[FileParamHolder] = Empty

    def processForm() = upload match {
      case Full(FileParamHolder(_, mimeType, fileName, file)) =>
        println("%s of type %s is %d bytes long" format(fileName, mimeType, file.length))

      case _ => println("No file?")

    }

    def setFileParamToUpload(f: FileParamHolder) = {
      println(s"This function has been called with a $f")
      upload = Full(f)

    }

    "#file" #> fileUpload(f => setFileParamToUpload(f)) & "type=submit" #> onSubmitUnit(processForm)
    /*
    The fileUpload binding ensures that the file is assigned to the upload variable. This
    allows us to access the Array[Byte] of the file in the processForm method when the
    form is submitted.
     */
  }

}
