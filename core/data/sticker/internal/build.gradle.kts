plugins {
	id("convention.android.library")
	id("convention.android.hilt")
}

android {
	namespace = "com.kova700.mviphotopicker.core.data.sticker.internal"
}

dependencies {
	implementation(project(":core:data:sticker:external"))
}