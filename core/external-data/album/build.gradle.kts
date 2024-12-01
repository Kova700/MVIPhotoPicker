plugins {
	id("convention.android.library")
	id("convention.android.hilt")
}

android {
	namespace = "com.kova700.mviphotopicker.core.external_data.album"
}

dependencies {
	implementation(project(":core:data:album:external"))
}