package com.github.yag.native

import java.lang.IllegalStateException

object NativeLibrary {

    fun load(library: String) {
        loadInternal(library) {
            System.load(it)
        }
    }

    fun getLibraryPath(library: String) : String {
        return loadInternal(library)
    }

    private fun loadInternal(library: String, loader: (String) -> Unit = {}) : String {
        val libraryName = System.mapLibraryName(library)
        val url = NativeLibrary::class.java.classLoader.getResource(libraryName) ?: throw IllegalStateException("No library file found for: $library.")

        val uri = url.toURI()
        val path = if (uri.scheme.equals("file", true)) {
            uri.path
        } else {
            val tmpFile = createTempFile()
            tmpFile.deleteOnExit()

            url.openStream().use { src ->
                tmpFile.outputStream().use { dest ->
                    src.copyTo(dest)
                }
            }
            tmpFile.absolutePath
        }
        loader(path)
        return path
    }

}