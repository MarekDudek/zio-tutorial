package md.zio.demo

import java.io.IOException

import scalaz.zio._

object Failure {

  val failure: IO[String, Unit] = IO.fail("Oh noes!")

  def readData(fileName: String): IO[IOException, String] = ???

  val attempted: IO[Nothing, Either[IOException, String]] = readData("data.json").attempt

  val absolved: IO[IOException, String] = IO.absolve(attempted)

  val x: IO[Nothing, String] = readData("data.json").attempt.map {
    case Left(_) => "42"
    case Right(data) => data
  }

  def openFile(name: String): IO[IOException, Array[Byte]] = ???

  val caughtAll: IO[IOException, Array[Byte]] = openFile("primary.json").catchAll(_ => openFile("backup.json"))

  val caughtSome: IO[IOException, Array[Byte]] = openFile("primary.json").catchSome {
    case _ => openFile("backup.json")
  }

  val alternative: IO[IOException, Array[Byte]] = openFile("primary.json").orElse(openFile("backup.json"))

  val redeemed: IO[Nothing, Array[Byte]] = openFile("data.json").
    redeem[Nothing, Array[Byte]](
    _ => IO.point(new Array(0)),
    content => IO.point(content)
  )

  val untilFirstFailure: IO[IOException, Nothing] = openFile("primary.json").forever

  val policy: Schedule[String, Int] = ???

  val retryWithSchedule: IO[String, Unit] = failure.retry(policy)

  val fallback: (String, Int) => IO[String, Unit] = ???

  val retriedWithScheduleAndFallback: IO[String, Unit] = failure.retryOrElse(policy, fallback)
}
