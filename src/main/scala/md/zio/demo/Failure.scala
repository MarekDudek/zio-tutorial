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

  val open: IO[IOException, Array[Byte]] = openFile("primary.json")

  val schedule: Schedule[IOException, Array[Byte]] = ???

  val retried: IO[IOException, Array[Byte]] = open.retry(schedule)

  val fallback: (IOException, Array[Byte]) => IO[IOException, Array[Byte]] = ???

  val retriedWithScheduleAndFallback: IO[IOException, Array[Byte]] = open.retryOrElse(schedule, fallback)
}
