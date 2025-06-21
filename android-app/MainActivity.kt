class MainActivity : AppCompatActivity() {
    private lateinit var urlInput: EditText
    private lateinit var downloadBtn: Button
    private lateinit var formatsList: RecyclerView
    private val api = ApiClient.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        urlInput = findViewById(R.id.url_input)
        downloadBtn = findViewById(R.id.btn_fetch)
        formatsList = findViewById(R.id.recycler_formats)
        formatsList.layoutManager = LinearLayoutManager(this)

        downloadBtn.setOnClickListener {
            val url = urlInput.text.toString()
            if (url.isEmpty()) return@setOnClickListener
            lifecycleScope.launch {
                val formats = api.getFormats(mapOf("url" to url))
                formatsList.adapter = FormatAdapter(formats) { selected ->
                    download(selected.format_id, url)
                }
            }
        }
    }

    private fun download(formatId: String, url: String) {
        lifecycleScope.launch {
            val body = api.download(mapOf("url" to url, "format_id" to formatId))
            val file = File(getExternalFilesDir(null), "video_${System.currentTimeMillis()}.mp4")
            file.outputStream().use { output ->
                body.byteStream().copyTo(output)
            }
            Toast.makeText(this@MainActivity, "Descarga completada", Toast.LENGTH_SHORT).show()
        }
    }
}
