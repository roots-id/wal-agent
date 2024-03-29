import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.11"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

apply(plugin = "io.spring.dependency-management")

group = "com.rootsid.wal"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

springBoot {
	buildInfo()
}

repositories {
	mavenCentral()

	maven {
		url = uri("https://maven.pkg.github.com/input-output-hk/better-parse")
		credentials {
			username = System.getenv("PRISM_SDK_USER")
			password = System.getenv("PRISM_SDK_PASSWORD")
		}
	}

	maven {
		url = uri("https://maven.pkg.github.com/roots-id/wal-library")
		credentials {
			username = System.getenv("GITHUB_USER")
			password = System.getenv("GITHUB_TOKEN")
		}
	}
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	// Third-parties
	implementation("com.rootsid.wal:wal-library:2.0.7-SNAPSHOT")

	// Needed for cryptography primitives implementation
	implementation("io.iohk.atala:prism-api:v1.4.1")

	// DIDComm
	implementation("org.didcommx:didcomm:0.3.0")
	implementation("org.didcommx:peerdid:0.3.0")

	// Swagger
	val openapiVersion = "1.6.11"
	implementation("org.springdoc:springdoc-openapi-data-rest:$openapiVersion")
	implementation("org.springdoc:springdoc-openapi-ui:$openapiVersion")
	implementation("org.springdoc:springdoc-openapi-kotlin:$openapiVersion")

	// Test
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module = "mockito-core")
	}
	testImplementation("com.ninja-squad:springmockk:3.1.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.getByName<Jar>("jar") {
	enabled = false
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register("bootRunInviter") {
	group = "application"
	description = "Runs this project as a Spring Boot application with the inviter profile"
	doFirst {
		tasks.bootRun.configure {
			systemProperty("spring.profiles.active", "inviter")
		}
	}
	finalizedBy("bootRun")
}

tasks.register("bootRunInvitee") {
	group = "application"
	description = "Runs this project as a Spring Boot application with the invitee profile"
	doFirst {
		tasks.bootRun.configure {
			systemProperty("spring.profiles.active", "invitee")
		}
	}
	finalizedBy("bootRun")
}
