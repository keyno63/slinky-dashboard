package com.github.keyno.dashboard

import slinky.core.annotations.react
import slinky.core.Component
import slinky.core.facade.ReactElement
import slinky.web.html.button
import slinky.web.html.className
import slinky.web.html.onClick

@react class Square extends Component {
  //case class Props(value: Int)
  type Props = Unit
  case class State(value: Option[String])

  override def initialState: State = State(None)

  override def render(): ReactElement =
    button(
      className := "square",
      onClick := (_ => setState(State(Some("x"))))
    )(state.value)
}
