package md.zio.demo

object Refs {

  import scalaz.zio._

  val z: IO[Nothing, Unit] = for {
    ref <- Ref(100)
    v1 <- ref.get
    v2 <- ref.set(v1 - 50)
  } yield v2

  def repeat[E, A](n: Int)(io: IO[E, A]): IO[E, Unit] =
    Ref(0).flatMap {
      iRef =>
        def loop: IO[E, Unit] = iRef.get.flatMap {
          i =>
            if (i < n)
              io *> iRef.update(_ + 1) *> loop
            else
              IO.unit
        }

        loop
    }
}
