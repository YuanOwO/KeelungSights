package xyz.yuanowo.keelungsightsviewer.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import xyz.yuanowo.keelungsightsviewer.model.Image;
import xyz.yuanowo.keelungsightsviewer.model.Sight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SightsCrawler {

    static String DOMAIN = "https://www.travelking.com.tw";
    static String UA =
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 " +
                    "Safari/537.36";

    public static void main(String[] args) throws IOException {
        SightsCrawler crawler = new SightsCrawler();
        crawler.getSightsList("七堵");
    }

    public List<Sight> getSightsList(String district) throws IOException {
        Document doc = getDocument("/tourguide/taiwan/keelungcity/");

        Element guidePoint = doc.getElementById("guide-point");
        assert guidePoint != null; // 找不到這個元素就GG了

        Elements districtHeader = guidePoint.select("h4:contains(" + district + ")");

        if (districtHeader.isEmpty()) {
            return List.of();
        }

        List<Sight> sights = new ArrayList<>();

        districtHeader.get(0).nextElementSibling().children().forEach(li -> {
            Element a = li.child(0);
            try {
                Sight sight = getSightDetails(a.attr("href"));
                sights.add(sight);
            } catch (IOException ignored) { }

        });

        return sights;
    }

    public Sight getSightDetails(String url) throws IOException {
        Document doc = getDocument(url);

        Sight sight = new Sight();

        sight.setSourceURL(DOMAIN + url);
        sight.setId(getIDFromURL(url));
        sight.setName(selectText(doc, "h1 > span"));
        sight.setDistrict(selectText(doc, ".breadcrumb .bc_last a"));
        sight.setCategory(selectText(doc, ".point_type > span:nth-child(2)"));
        sight.setDescription(getElementText(doc.selectFirst(".text")));

        Element addressElem = doc.selectFirst(".address a");
        if (addressElem != null) {
            sight.setMapURL(addressElem.attr("href"));
            sight.setAddress(addressElem.text());
        }

        Element img = doc.selectFirst("#galleria .swiper-slide img");
        if (img != null) {
//            Image image = new Image();
//            image.setUrl(img.attr("data-src"));
//            image.setAlternateText(img.attr("alt"));
//            sight.setPhoto(image);
            sight.setPhotoURL(img.attr("data-src"));
        }

        return sight;
    }

    private Long getIDFromURL(String url) {
        Matcher m = Pattern.compile("/tourguide/scenery(\\d+)\\.html").matcher(url);
        if (m.find()) {
            return Long.parseLong(m.group(1));
        } else {
            return null;
        }
    }

    private Document getDocument(String url) throws IOException {
        System.out.println("Fetching " + url);
        Document doc = Jsoup.connect(DOMAIN + url)
                .userAgent(UA)
                .timeout(10 * 1000)
                .get();
        System.out.println("Fetched " + doc.title());
        return doc;
    }

    private String selectText(Document doc, String cssQuery) {
        Element elem = doc.selectFirst(cssQuery);
        if (elem == null) {
            throw new RuntimeException("No such element: " + cssQuery);
        }
        return elem.text();
    }

    private String getElementText(Element elem) {
        if (elem == null) return "";

        StringBuilder builder = new StringBuilder();
        elem.childNodes().forEach(e -> {
            if (e instanceof TextNode el) { // 文字節點
                if (el.isBlank()) return; // 忽略空白
                builder.append(el.text().strip());
            } else if (e instanceof Element el) {
                String text = el.text().strip();
                if (el.tagName().equals("br")) {
                    // 換行符號
                    builder.append("\n");
                } else if (el.tagName().equals("h4")) {
                    // H4 小標題
                    builder.append("#### ").append(text).append("\n");
                } else if (el.tagName().equals("a") && !el.id().equals("po")) {
                    // 連結
                    builder.append(text);
                } else if (el.tagName().equals("strong")) {
                    // 粗體
                    builder.append("**").append(text).append("**");
                } else if (el.className().equals("othermsg")) {
                    builder.append(getElementText(el));
                } else if (!(el.classNames().contains("author") || el.classNames().contains("clear") || el.id().equals("po"))) {
                    System.out.println("Unknown node: " + e);
                }
            } else {
                System.out.println("Unknown node: " + e);
            }
        });
        return builder.toString().strip();
    }

}
