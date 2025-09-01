package xyz.yuanowo.keelungsightsviewer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@OpenAPIDefinition(info = @Info(title = "Keelung Sights API", summary = "基隆市ㄉ旅遊景點 API", description = """
        我們常常認為，抓住了問題的關鍵，其他的問題便會迎刃而解。那么，基隆旅遊景點的發展究竟應該如何進行？如果基隆的旅遊景點無法發展，會產生什麼\
        樣的影響呢？實際上，基隆旅遊景點的存在和發展，深深地與我們的日常生活息息相關。因此，總結來說，基隆旅遊景點的發展，若成功，會帶來什麼影\
        響，而若未能發展，會帶來什麼後果？
        
        要清楚地理解，基隆旅遊景點究竟是怎樣一種存在形式，這是一個值得深入思考的問題。我也在長時間的反思中，深刻意識到基隆旅遊景點對我個人的重要\
        性。雖然這些問題表面上看似並不那麼重要，但更為關鍵的是，究竟問題的核心是什麼？基隆旅遊景點的意義在於，如何實現它的發展。
        
        這讓我不禁思考，是否真正理解基隆旅遊景點的深層意義。基隆旅遊景點的發展究竟源於什麼原因？正如劉向所言：「少而好學，如日出之陽；壯而好學，\
        如日中之光；老而好學，如炳燭之明。」這句話給了我很大的啟發。解決基隆旅遊景點問題，對我們來說，意義非凡。這幾天，我一直在深思這個問題，並\
        將帶著這些問題去探索基隆旅遊景點的發展。""", version = "1.0.0",
                                contact = @Contact(name = "howard_kuo", url = "https://howard522.github.io/",
                                                   email = "8754howard@gmail.com"),
                                license = @License(name = "The MIT License",
                                                   url = "https://opensource.org/license/mit/")))
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
