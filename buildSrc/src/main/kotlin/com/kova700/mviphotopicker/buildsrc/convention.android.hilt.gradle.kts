import com.kova700.mviphotopicker.buildsrc.configure.libs

plugins {
	id("com.google.devtools.ksp")
	id("dagger.hilt.android.plugin")
}

dependencies {
	"ksp"(libs.findLibrary("hilt-android-compiler").get())
	"implementation"(libs.findBundle("hilt").get())
}