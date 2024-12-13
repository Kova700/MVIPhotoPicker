import com.kova700.mviphotopicker.buildsrc.configure.libs

plugins {
	id("convention.android.library")
	id("convention.android.compose")
	id("convention.android.hilt")
}

android {
	defaultConfig {
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
}

dependencies {
	implementation(project(":feature:base"))
	implementation(project(":core:design-system"))
	implementation(project(":core:permission"))
	implementation(project(":core:common"))
	implementation(project(":core:util"))

	implementation(libs.findLibrary("androidx-core-ktx").get())
	implementation(libs.findBundle("androidx-lifecycle").get())
	implementation(libs.findLibrary("coil-compose").get())
	implementation(libs.findBundle("coil").get())

	testImplementation(libs.findBundle("unit-test").get())
	androidTestImplementation(libs.findBundle("android-test").get())
	debugImplementation(libs.findBundle("debug-test").get())
}

tasks.withType<Test> {
	useJUnitPlatform()
}