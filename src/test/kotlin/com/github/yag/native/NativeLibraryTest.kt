package com.github.yag.native

import java.nio.file.Files
import kotlin.test.Test

class NativeLibraryTest {

    @Test
    fun test() {
        val path = NativeLibrary.getLibraryPath("fake")
        assert(Files.isRegularFile(path))
    }

    @Test(expected = UnsatisfiedLinkError::class)
    fun testLoad() {
        NativeLibrary.load("fake")
    }

    @Test(expected = IllegalStateException::class)
    fun testLoadNotExistLib() {
        NativeLibrary.load("not-exist-lib")
    }
}
