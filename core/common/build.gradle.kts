
plugins {
	id("convention.android.library")
	id("convention.android.hilt")
}

android {
	namespace = "com.kova700.mviphotopicker.core.common"
}

dependencies {
	implementation(libs.test.coroutines)
}