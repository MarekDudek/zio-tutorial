package md.zio.demo

import scalaz.zio._

object MappingChaining {

  object mapping {

    val z: IO[Nothing, Int] = IO.point(21).map(_ * 2)

    val x: IO[Exception, String] = IO.fail("No no!").leftMap(msg => new Exception(msg))
  }

  object chaining {

    val z: IO[Nothing, List[Int]] =
      IO.point(List(1, 2, 3)).
      flatMap{
        list => IO.point(list.map(_ + 1))
      }

    val x: IO[Nothing, List[Int]] =
      for {
        list <- IO.point(List(1, 2, 3))
        added <- IO.point(list.map(_ + 1))
      } yield added
  }
}
