package com.github.keyno.dashboard

import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._
import slinky.web.svg.to

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("resources/Board.css", JSImport.Default)
@js.native
object BoardCSS extends js.Object

@react class Board extends StatelessComponent {
  type Props = Unit

  private val css = BoardCSS

  def render(): ReactElement = {
    List(
      head(),
      body0(),
      body1(),
    )
  }

  def head(): ReactElement = {
    div(className := "Board")(
      //      a(className := "App-header", href := "https://google.com")(
      //        "サンプルリンク"
      //      ),
      header(className := "Board-header")(
        h1(className := "Board-title")("サンプルリンク")
      )
    )
  }
  def body0(): ReactElement = {
    div(className := "Board-box")(
      List(
        p()("sample link1"),
        second()
      )
    )
  }

  def body1(): ReactElement = {
    div(className := "Board-box")(
      List(
        p()("sample link2"),
        second()
      )
    )
  }

  def second(): ReactElement = {
    div(className := "Communication")(
//      a(className := "App-header", href := "https://google.com")(
//        "Google"
//      ),
//      a(href := "https://www.amazon.co.jp/")(
//        "Amazon "
//      ),
      sampleLinks()
    )
  }

  def sampleLinks(): List[ReactElement] = {
    List(
      "Google" -> "https://google.com",
      "Amazon" -> "https://www.amazon.co.jp/",
    ).map{
      values =>
        createLinkList(values._2, values._1)
    }
  }

  def createLinkList(linkUrl: String, text: String): ReactElement = {
    li()(
      a(href := linkUrl)(text)
    )
  }
}
