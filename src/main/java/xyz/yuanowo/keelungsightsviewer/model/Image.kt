package xyz.yuanowo.keelungsightsviewer.model

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

data class Image(
    @field:Schema(description = "圖片網址", example = "https://www.example.com/image.jpg")
    val url: String? = null,
    @field:Schema(description = "替代文字", example = "這是一張圖片")
    val alternateText: String? = null
) : Serializable
