import com.kova700.mviphotopicker.buildsrc.configure.configureKotlinAndroid
import com.kova700.mviphotopicker.buildsrc.configure.libs

plugins {
	id("com.android.library")
	kotlin("android")
}

android {
	configureKotlinAndroid(this)
	defaultConfig.targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
	defaultConfig {
		consumerProguardFiles("proguard-rules.pro")
	}
}

dependencies{
	testImplementation(libs.findBundle("unit-test").get())
}

tasks.withType<Test> {
	useJUnitPlatform()
}