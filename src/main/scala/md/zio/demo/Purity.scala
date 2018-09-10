package md.zio.demo

import java.io.{File, IOException}

import org.apache.commons.io.FileUtils
import scalaz.zio._

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

    def readFile2(name: String): IO[Throwable, Array[Byte]] =
      IO.syncThrowable(FileUtils.readFileToByteArray(new File(name)))

    def readFile3(name: String): IO[String, Array[Byte]] =
      IO.syncCatch(FileUtils.readFileToByteArray(new File(name))) {
        case _: IOException => "Could not read file"
      }
  }
}
