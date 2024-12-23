package org.bcm.advent

import cats.effect.IO

trait PuzzleSolution {
  def partOne(input: List[String]): IO[String]
  def partTwo(input: List[String]): IO[String] = IO.pure("We didn't do Part 2 for this day")
}
