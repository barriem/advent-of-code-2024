package org.bcm.advent

import com.monovore.decline.*
import com.monovore.decline.refined.*
import eu.timepit.refined.*
import eu.timepit.refined.api.{RefType, Refined}
import eu.timepit.refined.numeric.*

object RunnerArgs {
  type Day  = Int Refined Interval.Closed[1, 24]
  type Part = Int Refined Interval.Closed[1, 2]

  val dayArg: Opts[Day] =
    Opts.option[Day](
      long = "day",
      short = "d",
      help = "Pick a day of the Advent between 1 and 24",
      metavar = "1"
    )

  val partArg: Opts[Part] =
    Opts.option[Part](
      long = "part",
      short = "p",
      help = "Pick a part of puzzle for the given day",
      metavar = "1"
    )
}
