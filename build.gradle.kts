buildscript {
    dependencies {
        constraints {
            classpath("org.apache.commons:commons-lang3:3.18.0")
            classpath("org.bitbucket.b_c:jose4j:0.9.6")
            classpath("org.bouncycastle:bcpkix-jdk18on:1.84")
            classpath("org.bouncycastle:bcprov-jdk18on:1.84")
            classpath("org.bouncycastle:bcutil-jdk18on:1.84")
            classpath("org.jdom:jdom2:2.0.6.1")
        }
    }
}

plugins {
    id("com.android.application") version "9.3.2" apply false
    id("org.jetbrains.kotlin.android") version "2.2.10" apply false
    id("com.google.devtools.ksp") version "2.3.10" apply false
    id("androidx.room") version "2.8.4" apply false
}
