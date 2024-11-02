plugins {
  id("java")
  id("org.jetbrains.intellij.platform") version "2.1.0"
}

repositories {
  mavenCentral()

  intellijPlatform {
    defaultRepositories()
  }
}

sourceSets {
  main {
    java {
      srcDirs("src/main/gen")
    }
  }
}

dependencies {
  intellijPlatform {
    intellijIdeaCommunity("2024.2.4")

    bundledPlugin("com.intellij.java")

    pluginVerifier()
    zipSigner()
    instrumentationTools()
  }
  testImplementation("junit:junit:4.13.2")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
}

// See https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellijPlatform {
  group = "com.vibrant.prismio"
  buildSearchableOptions = true
  projectName = project.name

  pluginConfiguration {
    version = "0.0.3"
  }
}

//tasks {
//  patchPluginXml {
//    version.set("${project.version}")
//    sinceBuild.set("233")
//    untilBuild.set("242.*")
//  }
//}
