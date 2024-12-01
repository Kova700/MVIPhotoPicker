plugins {
	id("convention.android.library")
}

android {
	namespace = "com.kova700.mviphotopicker.feature.base"
}

dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.bundles.androidx.lifecycle)
}