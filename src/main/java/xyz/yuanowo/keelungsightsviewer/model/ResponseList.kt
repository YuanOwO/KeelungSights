package xyz.yuanowo.keelungsightsviewer.model

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable

@Schema(description = "列表回應")
data class ResponseList<T>(
    @field:Schema(description = "總數", example = "1") val total: Int,
    @field:Schema(description = "資料列表") val results: List<T>
) : Serializable {

    constructor(list: List<T>) : this(list.size, list)

}
