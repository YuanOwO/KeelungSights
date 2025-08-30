package xyz.yuanowo.keelungsightsviewer.service;

import org.springframework.stereotype.Service;
import xyz.yuanowo.keelungsightsviewer.crawler.SightsCrawler;
import xyz.yuanowo.keelungsightsviewer.exception.NotFoundException;
import xyz.yuanowo.keelungsightsviewer.model.Sight;

import java.util.List;


@Service
public class SightsService {


    public List<Sight> getSightsByDistrict(String district) {
        SightsCrawler crawler = new SightsCrawler();
        return crawler.fetchSightsListSync(district);
    }

    public Sight getSightById(Long id) {
        SightsCrawler crawler = new SightsCrawler();
        Sight sight = crawler.fetchSightDetailsSync(String.format("/tourguide/scenery%d.html", id));

//     找不到或不在基隆市
        if (sight == null || sight.getCity() == null || !sight.getCity().equals("基隆市")) {
            throw new NotFoundException("未知的景點 ID: " + id);
        }

        return sight;
    }

}
