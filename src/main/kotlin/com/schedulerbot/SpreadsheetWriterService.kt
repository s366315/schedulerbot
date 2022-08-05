package com.schedulerbot

import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class SpreadsheetWriterService : SpreadsheetWriter<RowModel> {
    var rows = 0

    private var book: XSSFWorkbook? = null
    private var sheet: XSSFSheet? = null


    override fun write(data: RowModel) {
        book ?: return

        val list = listOf("", "", data.ukrpost, data.url, "", "", data.fullname, data.phone, data.dollar, data.price, data.city, data.post, data.payment)

        val row = sheet?.createRow(rows)
        for ((index, value) in list.withIndex()) {
            val cell = row?.createCell(index)
            cell?.setCellValue(value as String)
        }

        rows++

        println(row?.physicalNumberOfCells)
    }

    override fun createNew() {
        book = XSSFWorkbook()
        sheet = book?.createSheet("new sheet")
    }

    override fun saveBook(): File {
        val filename = "${SimpleDateFormat("dd.MM.yy-HHmm").format(Date(System.currentTimeMillis()))}.xlsx"
        val fot = FileOutputStream(filename)
        book?.write(fot)
        book?.close()
        fot.close()
        rows = 0
        return File(filename)
    }
}