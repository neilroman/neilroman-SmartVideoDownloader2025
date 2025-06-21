class MainActivity : AppCompatActivity() {

    private lateinit var urlInput: EditText
    private lateinit var fetchBtn: Button
    private lateinit var recycler: RecyclerView
    private val api = ApiClient.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        urlInput = findViewById(R.id.url_input)
        fetchBtn = findViewById(R.id.btn_fetch)
        recycler = findViewById(R.id.recycler_formats)
        recycler.layoutManager = LinearLayoutManager(this)

        fetchBtn.setOnClickListener {
            val url = urlInput.text.toString().trim()
            if (url.isEmpty()) {
                Toast.makeText(this, "Pega un enlace válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val formats = api.getFormats(mapOf("url" to url))
                    recycler.adapter = FormatAdapter(formats) { format ->
                        download(format.format_id, url)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Error al obtener formatos", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun download(formatId: String, videoUrl: String) {
        lifecycleScope.launch {
            try {
                val response = api.download(mapOf("url" to videoUrl, "format_id" to formatId))
                val file = File(getExternalFilesDir(null), "video_${System.currentTimeMillis()}.mp4")
                file.outputStream().use { output ->
                    response.byteStream().copyTo(output)
                }
                Toast.makeText(this@MainActivity, "Descarga completada: ${file.name}", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error al descargar", Toast.LENGTH_LONG).show()
            }
        }
    }
}
