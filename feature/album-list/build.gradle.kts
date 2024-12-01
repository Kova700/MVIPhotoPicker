plugins {
	id("convention.android.feature")
}

android {
	namespace = "com.kova700.mviphotopicker.feature.album_list"
}

dependencies {
	implementation(project(":core:data:album:external"))
}