const express = require("express");
const cors = require("cors");
const ytdlp = require("yt-dlp-exec");
const fs = require("fs");
const path = require("path");

const app = express();
app.use(cors());
app.use(express.json());

app.post("/getFormats", async (req, res) => {
  const { url } = req.body;
  try {
    const output = await ytdlp(url, {
      dumpSingleJson: true,
      noCheckCertificates: true,
    });
    res.json(output.formats.map(f => ({
      format_id: f.format_id,
      ext: f.ext,
      resolution: f.format_note,
      filesize: f.filesize,
      url: f.url
    })));
  } catch (err) {
    res.status(500).json({ error: err.toString() });
  }
});

app.post("/download", async (req, res) => {
  const { url, format_id } = req.body;
  const outputPath = path.join(__dirname, "downloads", `video-${Date.now()}.mp4`);
  try {
    await ytdlp(url, {
      format: format_id,
      output: outputPath,
    });
    res.download(outputPath, () => {
      fs.unlinkSync(outputPath);
    });
  } catch (err) {
    res.status(500).json({ error: err.toString() });
  }
});

app.listen(5000, () => {
  console.log("Servidor corriendo en puerto 5000");
});
