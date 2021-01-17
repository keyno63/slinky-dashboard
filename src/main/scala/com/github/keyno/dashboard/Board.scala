package com.github.keyno.dashboard

import com.github.keyno.dashboard.domain.repository.MonsterListRepository
import com.github.keyno.dashboard.domain.service.MonsterListService
import org.scalajs.dom.{Event, html}
import slinky.core.{Component, FunctionalComponent, StatelessComponent, SyntheticEvent, WithAttrs}
import slinky.core.annotations.react
import slinky.core.facade.ReactElement
import slinky.web.html._
import slinky.history.History
import typings.reactRouter.mod.{RouteProps, `match`}
import typings.reactRouterDom.components.{BrowserRouter, Link, Route, Switch}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("resources/Board.css", JSImport.Default)
@js.native
object BoardCSS extends js.Object

@react class Board
  extends StatelessComponent
  //extends Component
{
  type Props = Unit

  private val css = BoardCSS

  def render(): ReactElement =
    List(
      head(),
      div(id := "top", className := "Board-virtical")(
        body0(),
        body1()
      ),
      div(className := "Board-virtical")(
        ButtonComponent()
      ),
      div(className := "Board-box2")(hr()),
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
    div(className := "Board-box2")(
      BrowserRouter(
        div(
          ul(
            li(Link[js.Object](to = "/")("Home")),
            li(Link[js.Object](to = "/result")("Result")),
            li(Link[js.Object](to = "/list")("List")),
          ),
          hr(),
          Switch(
            Route(RouteProps().setExact(true).setPath("/").setRender(_ => home)),
            Route(RouteProps().setPath("/result").setRender(props => ResultPage(props.`match`))),
            Route(RouteProps().setPath("/list").setRender(props => ResultPage(props.`match`)))
          )
        )
      )
    )
}

@react object ResultPage {
  type Props = `match`[_]

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { m =>
    //val list = List("a", "b", "c")
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
    //val resultId: List[String] = js.native
  }

  case class Props(`match`: `match`[Result.Param])

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    div(
      h3("Result: " + props.`match`.params.resultId)
//      h3("Result: "),
//      div()(
//        props.`match`.params.resultId.map(x => li()(x))
//      )
    )
  }
}

@react class ButtonComponent extends Component {
  //case class Props()
  type Props = Unit
  case class State(min: String, max: String)

  override def initialState: State = State("1", "100")

  def handleChange(event: SyntheticEvent[html.Input, Event]): Unit = {
    val eventValue = event.target.value
    setState(_.copy(min = eventValue))
  }

  def handleChange2(event: SyntheticEvent[html.Input, Event]): Unit = {
    val eventValue = event.target.value
    setState(_.copy(max = eventValue))
  }

  def handleSubmit(e: SyntheticEvent[html.Form, Event]): Unit = {
    e.preventDefault()

    if (state.min < state.max) {
      setState(precState => {
        State(state.min, state.max)
      }
      )
    }
  }

  override def render(): ReactElement = {
    div()(
      h3("form"),
      createForm()
    )
  }

  def createForm(): ReactElement = {
    form(
      onSubmit := (handleSubmit(_))
    )(
      label()("検索範囲: "),
      input(
        onChange :=(handleChange(_)),
        value := state.min
      )(),
      label()("から"),
      input(
        onChange := (handleChange2(_)),
        value := state.max
      )(),
      label()("まで"),
      //button(`type` := "submit")("送信"),
      BrowserRouter()(
        div()(
          button(`type` := "submit")(Link[js.Object](to = "/subimit")("送信")),
          Route(RouteProps().setPath("/subimit").setRender(props => ResultCalc(state.min.toIntOption.getOrElse(0), state.max.toIntOption.getOrElse(0)))),
          // TODO: java io from scalajs
          //Route(RouteProps().setPath("/subimit").setRender(props => ResultCalcImpl(false, state.min.toIntOption.getOrElse(0), state.max.toIntOption.getOrElse(0)))),
        )
      )
    )
  }
}

@react object ResultCalc {
  @js.native
  trait Param extends js.Object {
    val resultId: String = js.native
    //val resultId: List[String] = js.native
  }

  //case class Props(`match`: `match`[Result.Param])
  case class Props(min: Int, max: Int)

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    div(
      h3(s"Result. min: ${props.min}, max: ${props.max}")
    )
  }
}

@react object ResultCalcImpl {
  @js.native
  trait Param extends js.Object {
    val resultId: String = js.native
  }

  case class Props(isRank: Boolean, min: Int, max: Int)

  val service = new MonsterListService(new MonsterListRepository)

  val component: FunctionalComponent[Props] = FunctionalComponent[Props] { props =>
    val list = service.getMonsterList(props.isRank, props.min, props.max)
    div(
      h3(s"Result. min: ${props.min}, max: ${props.max}"),
      table()(
        tbody()(
            list.map { data =>
              tr()(
                td()(data._1),
                td()(data._2)
              )
            }
        )
      )
    )
  }
}
