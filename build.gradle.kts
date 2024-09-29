plugins {
    kotlin("jvm") version "1.9.21"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"

}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencyManagement {
    imports {
        mavenBom("io.projectreactor:reactor-bom:2023.0.8")
    }
}

dependencies {
    implementation("io.projectreactor:reactor-core")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("io.projectreactor:reactor-test")

    // ktor
    implementation("io.ktor:ktor-server-core:2.0.3")
    implementation("io.ktor:ktor-server-netty:2.0.3")
    implementation("io.ktor:ktor-server-content-negotiation:2.0.3")
    implementation("io.ktor:ktor-serialization-jackson:2.0.3")
    implementation("io.ktor:ktor-serialization-gson:2.0.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
