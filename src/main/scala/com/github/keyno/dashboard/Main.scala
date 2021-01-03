package com.github.keyno.dashboard

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportTopLevel, JSImport}
import scala.scalajs.LinkingInfo
import slinky.web.ReactDOM
import slinky.hot
import org.scalajs.dom
import org.scalajs.dom.document
import slinky.web.html.h1

@JSImport("resources/index.css", JSImport.Default)
@js.native
object IndexCSS extends js.Object

object Main {
  val css = IndexCSS

  @JSExportTopLevel("main")
  def main(): Unit = {
    if (LinkingInfo.developmentMode) {
      hot.initialize()
    }

    val container = Option(dom.document.getElementById("root")).getOrElse {
      val elem = dom.document.createElement("div")
      elem.id = "root"
      dom.document.body.appendChild(elem)
      elem
    }

    //ReactDOM.render(App(), container)
    //ReactDOM.render(Square(), container)
    ReactDOM.render(Board(), container)
  }
}
