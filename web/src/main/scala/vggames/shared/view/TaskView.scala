package vggames.shared.view

import vggames.shared.GamesConfiguration
import vggames.shared.Game
import scala.collection.concurrent.Map
import scalatags.Text.all._
import vggames.shared.vraptor.GameFactoryCache
import scala.util.Try
import vggames.shared.GameView
import scala.util.Success
import scala.util.Failure
import vggames.shared.task.JudgedTask
import vggames.shared.task.Exercise

class TaskView extends TypedView[(String, Exercise, Game, Option[JudgedTask], String)] {

  val title = "title".tag[String]

  override def render(t: (String, Exercise, Game, Option[JudgedTask], String)) = {
    val (gameName, task, game, judgedTask, lastAttempt) = t

    html(
      head(
        title(s"Exercício ${task.index} de ${game.name}"),
        meta(name := "robots", "content".attr := "noindex")),
      body(
        raw(renderGameView(game, task, judgedTask, lastAttempt))))
  }

  private def renderGameView(game: Game, task: Exercise, judgedTask: Option[JudgedTask], lastAttempt: String): String = {
    val viewName = s"vggames.${game.path}.${game.path.capitalize}GameView"

    Try(Class.forName(viewName).newInstance.asInstanceOf[GameView]).
      map(_.render(game, task, judgedTask, lastAttempt)) match {
        case Success(string) => string
        case Failure(t) => s"Não foi encontrada view para o jogo ${game.name}. " +
        s"Exceção: ${t.getClass.getName} ${t.getMessage} <pre>${t.getStackTraceString}</pre>"
      }
  }
}
