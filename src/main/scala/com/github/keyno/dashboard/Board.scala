package com.github.keyno.dashboard

import slinky.core.StatelessComponent
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._
import slinky.web.svg.to

@react class Board extends StatelessComponent {
  type Props = Unit

  private val css = AppCSS

  def render(): ReactElement = {
    List(
      head(),
      second(),
    )
  }

  def head(): ReactElement = {
    div(className := "App")(
      //      a(className := "App-header", href := "https://google.com")(
      //        "サンプルリンク"
      //      ),
      header(className := "App-header")(
        h1(className := "App-title")("サンプルリンク")
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
      div()(
        a(href := linkUrl)(text)
      )
    )
  }
}
