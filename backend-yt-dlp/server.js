const express = require("express");
const cors = require("cors");
const ytdlp = require("yt-dlp-exec");
const fs = require("fs");
const path = require("path");

const app = express();
app.use(cors());
app.use(express.json());

// Tu lógica API...
// ...

// CAMBIA ESTO:
const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en puerto ${PORT}`);
});
