package com.github.keyno.dashboard

import slinky.core.{ FunctionalComponent, StatelessComponent, WithAttrs }
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html.{ div, hr, _ }
import typings.reactRouter.mod.{ `match`, RouteProps }
import typings.reactRouterDom.components.{ BrowserRouter, Link, Route, Switch }

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("resources/Board.css", JSImport.Default)
@js.native
object BoardCSS extends js.Object

@react class Board extends StatelessComponent {
  type Props = Unit

  private val css = BoardCSS

  def render(): ReactElement =
    List(
      head(),
      body0(),
      body1(),
      switch()
    )

  def head(): ReactElement =
    div(className := "Board")(
      //      a(className := "App-header", href := "https://google.com")(
      //        "サンプルリンク"
      //      ),
      header(className := "Board-header")(
        h1(className := "Board-title")("サンプルリンク")
      )
    )

  def body0(): ReactElement =
    div(className := "Board-box")(
      List(
        p()("sample link1"),
        second()
      )
    )

  def body1(): ReactElement =
    div(className := "Board-box")(
      List(
        p()("sample link2"),
        second()
      )
    )

  def second(): ReactElement =
    div(className := "Communication")(
//      a(className := "App-header", href := "https://google.com")(
//        "Google"
//      ),
//      a(href := "https://www.amazon.co.jp/")(
//        "Amazon "
//      ),
      sampleLinks()
    )

  def sampleLinks(): List[ReactElement] =
    List(
      "Google" -> "https://google.com",
      "Amazon" -> "https://www.amazon.co.jp/"
    ).map {
      case (key, value) =>
        createLinkList(value, key)
    }

  def createLinkList(linkUrl: String, text: String): ReactElement =
    li()(
      a(href := linkUrl)(text)
    )

  // 空ページ
  def home: WithAttrs[div.tag.type] = div(h2("Home"))

  def switch(): ReactElement =
    div()(
      BrowserRouter(
        div(
          ul(
            li(Link[js.Object](to = "/")("Home")),
            li(Link[js.Object](to = "/result")("Result"))
          ),
          hr(),
          Switch(
            Route(RouteProps().setExact(true).setPath("/").setRender(_ => home)),
            Route(RouteProps().setPath("/result").setRender(props => ResultPage(props.`match`)))
          )
        )
      )
    )
}

@react object ResultPage {
  type Props = `match`[_]

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { m =>
    div(
      h2("ResultPage"),
      ul(
        li(Link[js.Object](to = m.url + "/result")("Rendering with result"))
      ),
      hr(),
      Switch(
        Route(
          RouteProps()
            .setPath(m.path + "/:resultId")
            .setRender(props => Result(props.`match`.asInstanceOf[`match`[Result.Param]]))
        ),
        Route(
          RouteProps()
            .setExact(true)
            .setPath(m.path)
            .setRender(_ => h3())
        )
      )
    )
  }
}

@react object Result {

  @js.native
  trait Param extends js.Object {
    val resultId: String = js.native
  }

  case class Props(`match`: `match`[Result.Param])

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    div(
      h3("Result: " + props.`match`.params.resultId)
    )
  }
}
