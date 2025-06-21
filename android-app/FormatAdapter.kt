class FormatAdapter(
    private val formats: List<FormatItem>,
    private val onDownloadClick: (FormatItem) -> Unit
) : RecyclerView.Adapter<FormatAdapter.FormatViewHolder>() {

    inner class FormatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val btn: Button = view.findViewById(R.id.btn_format)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FormatViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_format, parent, false)
        return FormatViewHolder(view)
    }

    override fun onBindViewHolder(holder: FormatViewHolder, position: Int) {
        val format = formats[position]
        val label = "${format.ext.uppercase()} ${format.resolution ?: ""}"
        holder.btn.text = label
        holder.btn.setOnClickListener { onDownloadClick(format) }
    }

    override fun getItemCount(): Int = formats.size
}
