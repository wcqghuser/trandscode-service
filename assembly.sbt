import AssemblyKeys._

assemblySettings

test in assembly := {}

mergeStrategy in assembly := {
  case "BUILD" => MergeStrategy.discard
  case PathList("org", "apache", xs @_*) => MergeStrategy.last
  case PathList("com", "twitter", xs @_*) => MergeStrategy.last
  case PathList(xs @ _*) if xs.last endsWith ".properties" => MergeStrategy.last
  case x => {
    val oldStrategy = (mergeStrategy in assembly).value
    oldStrategy(x)
  }
}

outputPath in assembly := new File("target/transcode-service.jar")
