package views.html

import play.api.libs.json._

import lila.api.Context
import lila.app.templating.Environment._
import lila.app.ui.ScalatagsTemplate._
import lila.common.String.html.safeJsonValue

object msg {

  def home(json: JsObject)(implicit ctx: Context) =
    views.html.base.layout(
      moreCss = frag(cssTag("msg")),
      moreJs = frag(
        jsAt(s"compiled/lichess.msg${isProd ?? (".min")}.js"),
        embedJsUnsafe(
          s"""$$(() =>LichessMsg.default(document.querySelector('.msg-app'), ${safeJsonValue(
            Json.obj(
              "data" -> json,
              "i18n" -> jsI18n
            )
          )}))"""
        )
      ),
      title = "Lichess Inbox"
    ) {
      main(cls := "box msg-app")(
        "loading"
      )
    }

  def jsI18n(implicit ctx: Context) = i18nJsObject(translations)

  private val translations = List(
    trans.inbox
  )
}
