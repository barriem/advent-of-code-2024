import sbt.*

object Dependencies {
  val catsEffect = Seq("org.typelevel" %% "cats-effect" % Versions.catsEffect)

  val decline = Seq(
    "com.monovore" %% "decline-effect" % Versions.decline,
    "com.monovore" %% "decline-refined" % Versions.decline,
  )
}

object Versions {
  val catsEffect = "3.5.7"
  val decline = "2.4.1"
}
