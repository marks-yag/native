package com.github.yag.native

import kotlin.test.Test
import kotlin.test.assertEquals

class NativeLibraryTest {

    @Test
    fun testLoadInternal() {
        val path = NativeLibrary.loadInternal("fake")
        assertEquals("This is a fake library file.\n", path.readText())
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
