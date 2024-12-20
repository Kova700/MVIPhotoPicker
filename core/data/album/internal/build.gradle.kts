plugins {
	id("convention.android.library")
	id("convention.android.hilt")
}

android {
	namespace = "com.kova700.mviphotopicker.core.data.album.internal"
}

dependencies {
	implementation(project(":core:data:album:external"))
	implementation(project(":core:external-data:album"))
}
