package md.zio.demo

import java.io.IOException

import scalaz.zio.console._
import scalaz.zio.{App, IO}

object MyApp extends App {

  override def run(args: List[String]): IO[Nothing, ExitStatus] =
    myAppLogic.
      attempt.
      map(_.fold(_ => 1, _ => 0)).
      map(ExitStatus.ExitNow(_))

  def myAppLogic: IO[IOException, Unit] =
    for {
      _ <- putStrLn("Hello, what's your name?")
      n <- getStrLn
      _ <- putStrLn("Hello, " + n + ", good to meet you!")
    } yield ()
}
