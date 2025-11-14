package com.modura.app.util.platform

expect fun readBytesFromFilePath(uri: String): ByteArray
expect fun readFileAsBytes(uri: String): ByteArray