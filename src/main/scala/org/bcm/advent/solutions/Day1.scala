package org.bcm.advent.solutions

import cats.effect.IO
import org.bcm.advent.PuzzleSolution

object Day1 extends PuzzleSolution {
  private case class ParsedInput(list1: List[Int], list2: List[Int])
  private val lineRegex = """\s+""".r

  private def parseInput(input: List[String]): IO[ParsedInput] =
    input.foldLeft(IO.pure(ParsedInput(Nil, Nil))) { case (existing, line) =>
      for {
        ex   <- existing
        pair <- IO(lineRegex.split(line).toList)
        head <- IO.fromOption(pair.headOption.flatMap(_.trim.toIntOption))(
          new RuntimeException("Invalid first input")
        )
        tail <- IO.fromOption(pair.lastOption.flatMap(_.trim.toIntOption))(
          new RuntimeException("Invalid second input")
        )
      } yield ParsedInput(ex.list1 :+ head, ex.list2 :+ tail)
    }

  override def partOne(input: List[String]): IO[String] =
    for {
      parsed <- parseInput(input)
      sorted    = ParsedInput(parsed.list1.sorted, parsed.list2.sorted)
      zipped    = sorted.list1.zip(sorted.list2)
      distances = zipped.map((a, b) => if a > b then a - b else if b > a then b - a else 0)
    } yield distances.sum.toString

  override def partTwo(input: List[String]): IO[String] =
    for {
      parsed <- parseInput(input)
      amounts = parsed.list1.map { i => parsed.list2.count(i2 => i2 == i) * i }
    } yield amounts.sum.toString
}
