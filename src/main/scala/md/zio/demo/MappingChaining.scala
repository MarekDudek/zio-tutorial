package md.zio.demo

import scalaz.zio._

object MappingChaining {

  object mapping {

    val z: IO[Nothing, Int] = IO.point(21).map(_ * 2)

    val x: IO[Exception, String] = IO.fail("No no!").leftMap(msg => new Exception(msg))
  }
}
