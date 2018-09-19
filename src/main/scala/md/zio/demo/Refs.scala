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

  val account: IO[Nothing, Ref[Int]] = Ref(100)

  val john: Ref[Int] => IO[Nothing, Boolean] =
    (act: Ref[Int]) =>
      act.get.flatMap(
        v => act.compareAndSet(v, v - 30)
      )

  val gaston: Ref[Int] => IO[Nothing, Boolean] =
    (act: Ref[Int]) =>
      act.get.flatMap(
        v => act.compareAndSet(v, v - 70)
      )

  val res: IO[Nothing, Int] = for {
    accountR <- account
    _ <- john(accountR) par gaston(accountR)
    v <- accountR.get
  } yield v

  Ref(0).flatMap {

    idCounter =>

      def freshVar: IO[Nothing, String] =
        idCounter.modify(cpt => (s"var${cpt + 1}", cpt + 1))

      for {
        _ <- freshVar
        _ <- freshVar
        _ <- freshVar
      } yield ()
  }
}
