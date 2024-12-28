import com.qriz.app.setNamespace

plugins {
    id("qriz.android.feature")
}

android {
    setNamespace("feature.main")
}

dependencies {
    implementation(projects.feature.sign)
    implementation(projects.feature.onboard)
}