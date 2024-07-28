# Fribe Android SDK

## Overview
The Fribe Android SDK allows you to integrate the Fribe services into your Android application. This guide provides instructions on how to include the SDK in your project.

## Installation

### Step 1: Add JitPack Repository

Add the JitPack repository to your root build.gradle file at the end of the repositories section:

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
### Step 2: Add JitPack Repository
Add the SDK dependency:

Add the following line to your module's build.gradle file inside the dependencies section:

gradle
Copy code
dependencies {
    implementation 'com.github.talharasool:FribeAndroidSDK:Tag'
}
Replace Tag with the appropriate release tag or version you want to use.
