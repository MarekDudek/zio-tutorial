package md.zio.demo

import java.io.IOException

import scalaz.zio.IO

object Brackets {

  val open: IO[IOException, Array[Byte]] = ???

  val close: Array[Byte] => IO[Nothing, Unit] = ???

  val use: Array[Byte] => IO[IOException, String] = ???

  val handle: IO[IOException, String] = open.bracket(close)(use)

  val cleanup: IO[Nothing, Unit] = ???

  val handle0: IO[IOException, Array[Byte]] = open.ensuring(cleanup)
}
