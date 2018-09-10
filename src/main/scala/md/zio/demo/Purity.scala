package md.zio.demo

import scalaz.zio._

import java.io.File
import org.apache.commons.io.FileUtils

object Purity {

  object PureValues {

    val z: IO[Nothing, String] = IO.point("Hello, World")

    val x: IO[Nothing, String] = IO.now("Hello, World")
  }

  object InfallibleIO {

    val i: IO[Nothing, Int] = IO.point(23)
  }

  object UnproductiveIO {

    val u: IO[Int, Nothing] = IO.fail(-1)
  }

  object ImpureCode {

    val i: IO[Nothing, Long] = IO.sync(System.nanoTime())

    def readFile(name: String): IO[Exception, Array[Byte]] =
      IO.syncException(FileUtils.readFileToByteArray(new File(name)))
  }
}
