package org.bcm.advent.solutions

import cats.effect.IO
import cats.syntax.traverse.*
import org.bcm.advent.PuzzleSolution

object Day2 extends PuzzleSolution {
  private case class Report(levels: List[Int])
  private case class ParsedInput(reports: List[Report])
  private val lineRegex = """\s+""".r

  private def parseInput(input: List[String]): IO[ParsedInput] =
    input
      .map { line =>
        for {
          levelLine <- IO(lineRegex.split(line).toList)
          levels <- levelLine
            .map(l =>
              IO.fromOption(l.toIntOption)(new RuntimeException(s"Invalid number for level: $l"))
            )
            .sequence
        } yield Report(levels)
      }
      .sequence
      .map(ParsedInput(_))

  private def isValidReport(report: Report): Boolean = {
    val inOrder =
      report.levels == report.levels.sorted || report.levels == report.levels.sorted.reverse
    val betweenOneAndThreeGaps = report.levels.sliding(2).toList.forall {
      case List(a, b) if (a > b && a - b <= 3) || (b > a && b - a <= 3) => true
      case _                                                            => false
    }
    inOrder && betweenOneAndThreeGaps
  }

  override def partOne(input: List[String]): IO[String] =
    for {
      parsed <- parseInput(input)
      validReports = parsed.reports.filter(isValidReport)
    } yield validReports.size.toString

  override def partTwo(input: List[String]): IO[String] = {
    case class Permutations(report: Report, reportsWithMissing: List[Report])

    for {
      parsed <- parseInput(input)
      permutations = parsed.reports.map { r =>
        Permutations(
          r,
          r.levels.indices.toList.map { i =>
            Report(r.levels.zipWithIndex.collect { case (level, index) if index != i => level })
          }
        )
      }
      validReports = permutations.filter(p =>
        isValidReport(p.report) || p.reportsWithMissing.exists(m => isValidReport(m))
      )
    } yield validReports.size.toString
  }
}
