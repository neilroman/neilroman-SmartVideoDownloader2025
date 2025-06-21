interface ApiClient {
    @POST("/getFormats")
    suspend fun getFormats(@Body body: Map<String, String>): List<FormatItem>

    @Streaming
    @POST("/download")
    suspend fun download(@Body body: Map<String, String>): ResponseBody

    companion object {
        fun create(): ApiClient {
            val client = OkHttpClient.Builder().build()
            return Retrofit.Builder()
                .baseUrl("https://tu-backend-en-render.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(ApiClient::class.java)
        }
    }
}
