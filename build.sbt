libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

scalaSource in Compile := baseDirectory.value / "src"

scalaSource in Test := baseDirectory.value / "test-src"

mainClass in (Compile, run) := Some("CookieSelection")
