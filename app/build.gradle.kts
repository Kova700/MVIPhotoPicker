plugins {
	id("convention.android.application")
	id("convention.android.compose")
	id("convention.android.hilt")
}

android {
	namespace = "com.kova700.mviphotopicker"
	defaultConfig {
		applicationId = "com.kova700.mviphotopicker"
		versionCode = libs.versions.versionCode.get().toInt()
		val majorVersion = libs.versions.major.get()
		val minorVersion = libs.versions.minor.get()
		val hotfixVersion = libs.versions.hotfix.get()
		versionName = "$majorVersion.$minorVersion.$hotfixVersion"
	}
}

dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.bundles.androidx.lifecycle)

	implementation(project(":feature:main"))
	implementation(project(":core:data:album:internal"))
	implementation(project(":core:external-data:internal"))
	implementation(project(":core:data:sticker:internal"))
	implementation(project(":core:permission"))
}