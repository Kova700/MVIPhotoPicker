import com.kova700.mviphotopicker.buildsrc.configure.android
import com.kova700.mviphotopicker.buildsrc.configure.libs

plugins {
	kotlin("plugin.compose")
}
android {
	buildFeatures.compose = true
	composeOptions {
		kotlinCompilerExtensionVersion = libs.findVersion("kotlinCompilerExtension").get().toString()
	}
}

composeCompiler {
	enableStrongSkippingMode = true
}

dependencies {
	"implementation"(platform(libs.findLibrary("compose-bom").get()))
	"implementation"(libs.findBundle("compose").get())
	"implementation"(libs.findLibrary("hilt-navigation-compose").get())

	"testImplementation"(libs.findBundle("unit-test").get())
	"androidTestImplementation"(libs.findBundle("android-test").get())
	"debugImplementation"(libs.findBundle("debug-test").get())
}