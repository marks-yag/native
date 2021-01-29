package com.github.yag.native

import java.io.File
import java.lang.IllegalStateException
import kotlin.test.Test

class NativeLibraryTest {

    @Test
    fun test() {
        val path = NativeLibrary.getLibraryPath("fake")
        val file = File(path)
        assert(file.isFile)
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