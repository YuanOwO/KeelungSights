package xyz.yuanowo.keelungsightsviewer.model

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.HttpStatus
import java.io.Serializable
import java.time.LocalDateTime

@Schema(description = "錯誤訊息")
data class ResponseMessage(
    @field:Schema(description = "HTTP 狀態碼") val status: Int,
    @field:Schema(description = "狀態原因") val reason: String,
    @field:Schema(description = "錯誤訊息") val message: String,

    @field:Schema(description = "時間戳記", example = "1980-01-01T08:00:00.000000000")
    val timestamp: String = LocalDateTime.now().toString()
) : Serializable {

    constructor(httpStatus: HttpStatus, message: String) : this(httpStatus.value(), httpStatus.reasonPhrase, message)

}
