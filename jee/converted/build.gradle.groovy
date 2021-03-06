plugins {
    id("org.jetbrains.kotlin.jvm").version("1.1.2")
}

apply {
    plugin("kotlin")
    plugin("war")
}

description = "Kotlin Server without Plugins"

dependencies {
    compile ("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version") // needed for ::class.java
}
