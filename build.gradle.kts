plugins {
    id("java")
    alias(libs.plugins.paperweight)
    alias(libs.plugins.shadow)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle(libs.versions.paper)
}

extra.apply {
    set("pluginName", project.name)
    set("className", project.name.replace("RX-",""))
    set("packageName", project.name.replace("RX-", "").lowercase())
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

val fileName = "${project.name}-${project.version}.jar"

tasks.jar {
    archiveFileName = fileName
}

tasks {
    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
            expand(extra.properties)
        }
    }
}

tasks.register("copyPlugin", Copy::class) {
    doFirst {
        println("copying build plugin ...")
    }

    from("build/libs/$fileName")
    into("C:/Users/rkdwo/OneDrive/문서/ServerEngine/servers/server_818280382/plugins")

    doLast {
        println("copied build plugin!")
    }
}

tasks.named("copyPlugin") {
    dependsOn("jar")
}