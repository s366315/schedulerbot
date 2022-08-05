package com.schedulerbot

import java.io.File

interface SpreadsheetWriter<T> {
    fun createNew()
    fun write(data: T)
    fun saveBook(): File
}