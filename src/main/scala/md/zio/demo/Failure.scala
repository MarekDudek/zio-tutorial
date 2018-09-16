package md.zio.demo

import java.io.{FileNotFoundException, IOException}

import md.zio.demo.Purity.ImpureCode._
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

  val caughtAll: IO[Exception, Array[Byte]] = readFile("data.json").catchAll(_ => readFile("backup.json"))

  val caughtSome: IO[Exception, Array[Byte]] = readFile("data.json").catchSome {
    case _ => readFile("backup.json")
  }

  val alternative : IO[Exception, Array[Byte]] = readFile("data.json").orElse(readFile("backup.json"))

  val redeemed: IO[Nothing, Int] = readFile("data.json").redeem[Nothing, Int](_ => IO.point(0), content => IO.point(content.length))
}
