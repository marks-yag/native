package com.github.yag.native

import java.nio.file.Path
import java.nio.file.Paths

object NativeLibrary {

    fun load(library: String) {
        loadInternal(library) {
            System.load(it.toString())
        }
    }

    fun getLibraryPath(library: String) : Path {
        return loadInternal(library)
    }

    private fun loadInternal(library: String, loader: (Path) -> Unit = {}) : Path {
        val libraryName = System.mapLibraryName(library)
        val url = NativeLibrary::class.java.classLoader.getResource(libraryName) ?: throw IllegalStateException("No library file: $libraryName found for: $library.")

        val uri = url.toURI()
        val path = if (uri.scheme.equals("file", true)) {
            Paths.get(uri)
        } else {
            val tmpFile = createTempFile()
            tmpFile.deleteOnExit()

            url.openStream().use { src ->
                tmpFile.outputStream().use { dest ->
                    src.copyTo(dest)
                }
            }
            tmpFile.toPath()
        }
        loader(path)
        return path
    }

}
