package xyz.yuanowo.keelungsightsviewer.model

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import java.io.Serializable
import java.time.LocalDateTime

@Schema(description = "錯誤訊息")
data class ResponseMessage(
    @field:Schema(description = "HTTP 狀態碼") val status: Int,
    @field:Schema(description = "狀態原因") val reason: String,
    @field:Schema(description = "錯誤訊息") val message: String,

    @field:Schema(description = "時間戳記", example = "2025-08-01 12:00:00.000")
    @field:DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "UTC+8")
    val timestamp: LocalDateTime = LocalDateTime.now()
) : Serializable {

    constructor(httpStatus: HttpStatus, message: String) : this(httpStatus.value(), httpStatus.reasonPhrase, message)

}
