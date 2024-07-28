# Fribe Android SDK

## Overview
The Fribe Android SDK allows you to integrate the Fribe services into your Android application. This guide provides instructions on how to include the SDK in your project.

## Installation

### Step 1: Add JitPack Repository

Add the following code to your root `build.gradle` file at the end of the `repositories` section:

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

```gradle
dependencies {
    implementation 'com.github.talharasool:FribeAndroidSDK:Tag'
}
