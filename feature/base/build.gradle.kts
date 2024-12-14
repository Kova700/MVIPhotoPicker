plugins {
	id("convention.android.library")
	id("convention.android.hilt")
}

android {
	namespace = "com.kova700.mviphotopicker.feature.base"
}

dependencies {
	implementation(project(":core:common"))
	implementation(libs.androidx.core.ktx)
	implementation(libs.bundles.androidx.lifecycle)
}