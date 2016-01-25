libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.6" % "test",
  "org.scalacheck" %% "scalacheck" % "1.12.5"
)

scalaSource in Compile := baseDirectory.value / "src"

scalaSource in Test := baseDirectory.value / "test-src"

mainClass in (Compile, run) := Some("CookieSelection")
