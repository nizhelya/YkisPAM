pluginManagement {
    repositories {
        google()
        mavenCentral()
       gradlePluginPortal()
        maven {
            url = uri("https://jitpack.io")
            credentials {
                username = "jp_qmo7ur7lrm7bvb3gsdlqfuk1lp"


            }
        }

    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
            credentials {
                username = "jp_qmo7ur7lrm7bvb3gsdlqfuk1lp"


            }
        }
    }
}

rootProject.name = "YkisPAM"
include(":app")
