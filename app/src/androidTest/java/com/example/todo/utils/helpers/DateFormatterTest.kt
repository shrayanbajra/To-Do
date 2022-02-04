package com.example.todo.utils.helpers

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.todo.R
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.util.*

class DateFormatterTest {

    lateinit var instrumentationContext: Context

    @Before
    fun setup() {
        instrumentationContext = ApplicationProvider.getApplicationContext<Context>()
    }

    @Test
    fun currentDate_returnsOutputAsToday() {

        val expectedResult = instrumentationContext.resources.getString(R.string.today)

        val currentDate = Date()
        val formattedDate = DateFormatter().getFormattedDueDate(
            context = instrumentationContext, date = currentDate
        )

        println("Actual result -> $formattedDate")
        println("Expected result -> $expectedResult")
        Truth.assertThat(formattedDate).isEqualTo(expectedResult)

    }

    @Test
    fun date_returnsOutputAsFormattedDate() {

        val expectedResult = "3 Feb"

        val thirdFeb2022 = Date(1643988989)

        val formattedDate = DateFormatter().getFormattedDueDate(
            context = instrumentationContext, date = thirdFeb2022
        )

        println("Actual result -> $formattedDate")
        println("Expected result -> $expectedResult")
        Truth.assertThat(formattedDate).isEqualTo(expectedResult)

    }

}