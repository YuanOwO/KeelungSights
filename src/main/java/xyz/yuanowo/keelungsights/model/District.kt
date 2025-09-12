package xyz.yuanowo.keelungsights.model

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

@Schema(description = "行政區資料")
data class District(
    @field:Schema(description = "郵遞區號", example = "200") val zipcode: String? = null,
    @field:Schema(description = "中文名稱", example = "仁愛區") val zh: String? = null,
    @field:Schema(description = "英文名稱", example = "Renai") val en: String? = null
) : Serializable
