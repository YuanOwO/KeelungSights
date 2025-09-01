package xyz.yuanowo.keelungsightsviewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import xyz.yuanowo.keelungsightsviewer.crawler.SightsCrawler;
import xyz.yuanowo.keelungsightsviewer.exception.NotFoundException;
import xyz.yuanowo.keelungsightsviewer.model.Sight;
import xyz.yuanowo.keelungsightsviewer.repository.SightDao;

import java.util.List;


@Service
public class SightsService {

    @Autowired
    SightDao sightDao;

    @EventListener(ApplicationReadyEvent.class)
    public void crawler() {
        // Server 啟動完畢再開始爬蟲
        SightsCrawler crawler = new SightsCrawler();
        System.out.println("Start crawling sights...");
        List<Sight> sights = crawler.fetchSightsListSync(null);
        System.out.printf("Crawled %d sights. Saving to database...\n", sights.size());
        sightDao.saveAll(sights);
        System.out.println("All sights saved to database.");
    }

    public List<Sight> getSightsByDistrict(String district) {
        if (!district.endsWith("區")) district += "區";
        return sightDao.findByDistrict(district);
    }

    public Sight getSightById(Long id) {
        Sight sight = sightDao.findById(id);
        if (sight == null) throw new NotFoundException("未知的景點 ID: " + id);
        return sight;
    }

}
