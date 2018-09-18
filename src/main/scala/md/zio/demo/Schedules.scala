package md.zio.demo

object Schedules {

  import scalaz.zio._

  import scala.concurrent.duration._

  val forever: Schedule[Any, Int] = Schedule.forever
  val never: Schedule[Any, Nothing] = Schedule.never
  val recur10times: Schedule[Any, Int] = Schedule.recurs(10)
  val spaced: Schedule[Any, Int] = Schedule.spaced(10.milliseconds)
  val exponential: Schedule[Any, Duration] = Schedule.exponential(10.milliseconds)
  val fibonacci: Schedule[Any, Duration] = Schedule.fibonacci(10.milliseconds)

  val jittered: Schedule[Any, Duration] = fibonacci.jittered
  val boosted: Schedule[Any, Duration] = exponential.delayed(_ + 100.milliseconds)

  val sequential: Schedule[Any, Either[Int, Int]] = recur10times <||> spaced
  val expUpTo10: Schedule[Any, (Duration, Int)] = exponential && recur10times
  val excCapped: Schedule[Any, (Duration, Int)] = exponential || spaced
}
