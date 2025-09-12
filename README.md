# KeelungSights

一個基於 Spring Boot (Java + Kotlin 混合開發) 的全端專案，提供：

- RESTful API
- 前端網頁 (static HTML/CSS/JS)
- 資料庫 (~~H2, JPA~~ &rarr; MongoDB Atlas)
- 使用 Kotlin Coroutine 的非同步爬蟲 (OkHttp + Jsoup)
- Swagger UI 文件
- 全域錯誤處理 (ExceptionHandler)
- CORS 支援 (跨來源存取)

此專案用於展示基隆市的各個景點，前端頁面可瀏覽清單，API 可存取詳細資訊。

## 系統需求

- Java 21+
- Maven 3.8+
- Spring Boot 5.3+
- MongoDB Atlas (免費雲端資料庫)
