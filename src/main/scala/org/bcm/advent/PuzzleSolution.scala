package org.bcm.advent

import cats.effect.IO

trait PuzzleSolution {
  def partOne(input: List[String]): IO[String]
  def partTwo(input: List[String]): IO[String] = IO.pure("There was no Part 2 for this day")
}
