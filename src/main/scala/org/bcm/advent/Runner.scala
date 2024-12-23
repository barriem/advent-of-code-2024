package org.bcm.advent

import cats.effect.{ExitCode, IO}
import cats.syntax.apply.*
import com.monovore.decline.*
import com.monovore.decline.effect.*
import eu.timepit.refined.*
import eu.timepit.refined.api.Refined
import solutions.*

object Runner extends CommandIOApp(name = "Advent24", header = "Advent of Code 2024 solutions") {
  import RunnerArgs.*

  private val solutions =
    Seq[PuzzleSolution](
      Day1,
      Day2
    )

  private def readInput(day: Day, part: Part): IO[List[String]] =
    IO(
      scala.io.Source
        .fromResource(s"day-${day.value}-part-${part.value}")
        .getLines()
        .toList
    )

  override def main: Opts[IO[ExitCode]] =
    (dayArg, partArg).mapN { (day, part) =>
      for {
        _     <- IO(println(s"Running Day ${day.value} - Part ${part.value}"))
        input <- readInput(day, part)
        _     <- IO(println("Found input"))
        solution = solutions(day.value - 1)
        result <- part.value match {
          case 1 => solution.partOne(input)
          case 2 => solution.partTwo(input)
          case _ => IO.raiseError(new RuntimeException("Should not be possible with refined types"))
        }
        _ <- IO(println(s"Puzzle result: ${result}"))
      } yield ExitCode.Success
    }
}
