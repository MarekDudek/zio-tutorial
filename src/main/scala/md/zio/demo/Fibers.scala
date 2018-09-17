package md.zio.demo

object Fibers {

  import scalaz.zio._

  trait Analysis

  def analyseData(): IO[Exception, Analysis] = ???

  def validateData(): IO[Exception, Boolean] = ???

  val analyzed: IO[Exception, Analysis] =
    for {
      fiber1 <- analyseData().fork: IO[Nothing, Fiber[Exception, Analysis]]
      fiber2 <- validateData().fork: IO[Nothing, Fiber[Exception, Boolean]]
      valid <- fiber2.join
      _ <-
        if (!valid)
          fiber1.interrupt(new IllegalArgumentException)
        else
          IO.unit
      analyzed <- fiber1.join
    } yield analyzed

  def fib(n: Int): IO[Nothing, Int] =
    if (n <= 1)
      IO.point(1)
    else
      for {
        f1 <- fib(n - 2).fork
        f2 <- fib(n - 1).fork
        v2 <- f2.join
        v1 <- f1.join
      } yield v1 + v2

  trait Matrix

  def computeInverse(matrix: Matrix): IO[Nothing, Matrix] = ???

  def applyMatrices(m1: Matrix, m2: Matrix, v: Matrix): IO[Nothing, Matrix] = ???

  def bigCompute(m1: Matrix, m2: Matrix, v: Matrix): IO[Nothing, Matrix] =
    for {
      t <- computeInverse(m1).par(computeInverse(m2))
      (i1, i2) = t
      r <- applyMatrices(i1, i2, v)
    } yield r
}
