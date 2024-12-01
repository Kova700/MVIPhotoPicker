plugins {
	id("convention.android.feature")
}

android {
	namespace = "com.kova700.mviphotopicker.feature.main"
}

dependencies {
	implementation(project(":feature:album-list"))
	implementation(project(":feature:photo-list"))
	implementation(project(":feature:photo-detail"))
}