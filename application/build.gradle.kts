plugins {
    `java-conventions`
    `java-library`
}

group = "io.github.guimatech.application"

dependencies {
    implementation(project(":domain"))
}