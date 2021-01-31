package com.github.yag.native

import java.net.URL
import java.nio.file.Path
import java.nio.file.Paths

object NativeLibrary {

    fun load(library: String) {
        loadInternal(library) {
            System.load(it.toString())
        }
    }

    internal fun loadInternal(library: String, loader: (Path) -> Unit = {}) : URL {
        val libraryName = System.mapLibraryName(library)
        val url = NativeLibrary::class.java.classLoader.getResource(libraryName) ?: throw IllegalStateException("No library file: $libraryName found for: $library.")

        val uri = url.toURI()
        if (uri.scheme.equals("file", true)) {
            loader(Paths.get(uri))
        } else {
            val tmpFile = createTempFile()
            tmpFile.deleteOnExit()

            url.openStream().use { src ->
                tmpFile.outputStream().use { dest ->
                    src.copyTo(dest)
                }
            }
            loader(tmpFile.toPath())
            tmpFile.delete()
        }

        return url
    }

}
