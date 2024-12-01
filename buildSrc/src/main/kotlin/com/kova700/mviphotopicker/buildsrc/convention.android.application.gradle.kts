import com.kova700.mviphotopicker.buildsrc.configure.configureKotlinAndroid
import com.kova700.mviphotopicker.buildsrc.configure.libs

plugins {
	id("com.android.application")
	kotlin("android")
}

android {
	configureKotlinAndroid(this)
	defaultConfig.targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
	buildTypes {
		release {
			isMinifyEnabled = true
			isShrinkResources = true
			proguardFiles(
				getDefaultProguardFile("proguard-android-optimize.txt"),
				"proguard-rules.pro"
			)
		}
	}

	packaging {
		resources {
			excludes += "/META-INF/{AL2.0,LGPL2.1}"
		}
	}
}