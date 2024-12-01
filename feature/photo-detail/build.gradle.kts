plugins {
	id("convention.android.feature")
}

android {
	namespace = "com.kova700.mviphotopicker.feature.photo_detail"
}

dependencies {
	implementation(project(":core:data:album:external"))
	implementation(project(":core:data:sticker:external"))
}