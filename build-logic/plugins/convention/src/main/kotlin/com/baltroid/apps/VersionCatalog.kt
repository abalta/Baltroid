package com.baltroid.apps

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

/**
 * An extension for easy access to the version catalog.
 */
internal val Project.libs get() = the<LibrariesForLibs>()
