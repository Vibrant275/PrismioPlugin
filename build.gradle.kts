plugins {
  id("java")
  id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.vibrant.prismio"
version = "0.0.1"

repositories {
  mavenCentral()
}

// Include the generated files in the source set
sourceSets {
  main {
    java {
      srcDirs("src/main/gen")
    }
  }
}

dependencies {
  testImplementation("junit:junit:4.13.2")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

// See https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
  version.set("2023.3.7")
  plugins.set(listOf("com.intellij.java"))
}

tasks {
  buildSearchableOptions {
    enabled = false
  }

  patchPluginXml {
    version.set("${project.version}")
    sinceBuild.set("233")
    untilBuild.set("242.*")
  }
}
