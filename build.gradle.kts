plugins {
    id("java")
}

group = "bstefanov.transportOrg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.github.cdimascio:dotenv-java:2.2.0")

    implementation("org.hibernate.orm:hibernate-core:6.6.4.Final")
    implementation("mysql:mysql-connector-java:8.0.33")
    //implementation("org.jboss.logging:jboss-logging:3.6.1.Final")
    implementation("org.apache.logging.log4j:log4j-core:2.24.3")
    //implementation("org.apache.logging.log4j:log4j-api:2.24.3")
    implementation("org.hibernate.validator:hibernate-validator:8.0.2.Final")
    implementation("org.hibernate.validator:hibernate-validator-annotation-processor:8.0.2.Final")
    implementation("org.glassfish.expressly:expressly:6.0.0-M1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("com.h2database:h2:2.3.232")
}

tasks.test {
    useJUnitPlatform()
}