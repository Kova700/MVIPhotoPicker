pluginManagement {
	repositories {
		google {
			content {
				includeGroupByRegex("com\\.android.*")
				includeGroupByRegex("com\\.google.*")
				includeGroupByRegex("androidx.*")
			}
		}
		mavenCentral()
		gradlePluginPortal()
	}
}
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		google()
		mavenCentral()
	}
}

rootProject.name = "MVIPhotoPicker"
include(":app")
include(":core:design-system")
include(":core:permission")
include(":core:util")

include(":feature:base")
include(":feature:main")
include(":feature:album-list")
include(":feature:photo-list")
include(":feature:photo-detail")

include(":core:data:album:external", ":core:data:album:internal")
include(":core:data:sticker:external", ":core:data:sticker:internal")
include(":core:external-data:album", ":core:external-data:internal")
include(":core:common")
