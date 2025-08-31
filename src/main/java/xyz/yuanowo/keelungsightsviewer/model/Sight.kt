package xyz.yuanowo.keelungsightsviewer.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.io.Serializable

@Entity
@Table
@Schema(description = "景點資料")
data class Sight(
    @Id
    @field:Schema(description = "景點ID", example = "9527")
    var id: Long? = null,

    @Column
    @field:Schema(description = "景點名稱", example = "摩艾DUMDUM")
    var name: String? = null,

    @Column
    @field:Schema(description = "所在縣市", example = "基隆市")
    var city: String? = null,

    @Column
    @field:Schema(description = "所在區域", example = "中正區")
    var district: String? = null,

    @Column
    @field:Schema(description = "景點類別", example = "石雕")
    var category: String? = null,

    @Column(length = 4096)
    @field:Schema(
        description = "景點描述",
        example = "基隆私房景點，縮小版智利\uD83C\uDDE8\uD83C\uDDF1復活節島！\n還有遊艇\uD83D\uDEE5\uFE0F、基隆嶼、基隆的海❤\uFE0F"
    )
    var description: String? = null,

    @Column
    @field:Schema(description = "景點地址", example = "基隆市中正區北寧路2號")
    var address: String? = null,

    @Column
    @field:Schema(description = "地圖連結", example = "https://maps.app.goo.gl/MXQsFcCQkUYHhUk87")
    var mapUrl: String? = null,

    @Column
    @field:Schema(description = "照片連結", example = "/image/dumdum.jpg")
    var photoUrl: String? = null,

    @Column
    @field:Schema(description = "資料來源網址", example = "https://www.example.com/dumdum")
    var sourceUrl: String? = null
) : Serializable

// https://lh3.googleusercontent.com/geougc-cs/AB3l90B5FADQ9qRvuKIdl5TOraPfSEV1ym_mZh7_2Vqusq_-Gjfp7TcZgpzkESN9RSFxJaiYx3xH3YicIdAfqkPhyKZRA0dGoN6qD3mZmIVjyDZWQsT180qcaE7qGIMzbLKKz7IfmIAQ=w2048-h1536-p