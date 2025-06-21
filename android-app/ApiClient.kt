import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.http.Streaming

interface ApiClient {

    @POST("getFormats")
    suspend fun getFormats(@Body body: Map<String, String>): List<FormatItem>

    @Streaming
    @POST("download")
    suspend fun download(@Body body: Map<String, String>): ResponseBody

    companion object {
        fun create(): ApiClient {
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .baseUrl("https://yt-dlp-backend-8sqc.onrender.com/") // 🔁 Aquí tu backend real
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiClient::class.java)
        }
    }
}
