package com.luczka.miquidorecruitment.data.remote

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException

class PicsumApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var picsumApiService: PicsumApiService

    private fun loadJson(fileName: String): String {
        return File("src/test/java/com/luczka/miquidorecruitment/data/remote/resources/$fileName")
            .readText(Charsets.UTF_8)
    }

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        picsumApiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PicsumApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test getPicsumImages returns expected data`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(loadJson("picsum_images_success_page_full.json"))

        mockWebServer.enqueue(mockResponse)

        val result = picsumApiService.getPicsumImages(page = 1, limit = 20)

        assertEquals(20, result.size)
        assertEquals("0", result[0].id)
        assertEquals("Alejandro Escamilla", result[0].author)
        assertEquals(5000, result[0].width)
        assertEquals(3333, result[0].height)
        assertEquals("https://unsplash.com/photos/yC-Yzbqy7PY", result[0].url)
        assertEquals("https://picsum.photos/id/0/5000/3333", result[0].downloadUrl)
    }

    @Test
    fun `test getPicsumImages returns empty list`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(loadJson("picsum_images_success_page_empty.json"))
        mockWebServer.enqueue(mockResponse)

        val result = picsumApiService.getPicsumImages(page = 1, limit = 20)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `test getPicsumImages handles server error`() = runBlocking {
        val mockResponse = MockResponse().setResponseCode(500)

        mockWebServer.enqueue(mockResponse)

        try {
            picsumApiService.getPicsumImages(page = 1, limit = 20)
            fail("Expected an HttpException to be thrown")
        } catch (e: HttpException) {
            assertEquals(500, e.code())
        }
    }

    @Test
    fun `test getPicsumImages handles network error`() = runBlocking {
        mockWebServer.shutdown()

        try {
            picsumApiService.getPicsumImages(page = 1, limit = 20)
            fail("Expected an IOException to be thrown")
        } catch (e: IOException) {
            assertTrue(true)
        }
    }


}