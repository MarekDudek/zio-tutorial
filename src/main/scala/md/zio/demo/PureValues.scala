package md.zio.demo

import scalaz.zio._


object PureValues {

  val z: IO[Nothing, String] = IO.point("Hello, World")

  val x: IO[Nothing, String] = IO.now("Hello, World")
}
