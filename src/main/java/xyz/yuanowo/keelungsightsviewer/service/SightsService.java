package xyz.yuanowo.keelungsightsviewer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(SightsService.class);

    @Autowired
    SightDao sightDao;

    @EventListener(ApplicationReadyEvent.class)
    public void crawler() {
        // Server 啟動完畢再開始爬蟲
        SightsCrawler crawler = new SightsCrawler();
        log.info("Start crawling sights...");
        List<Sight> sights = crawler.fetchSightsListSync(null);

        // 更新資料庫
        log.info("Crawled {} sights. Saving to database...", sights.size());

        if (sights.size() != 41) {
            log.warn("Crawling failed or incomplete. Expected 41 sights, but got {}.", sights.size());
            log.warn("Database not updated.");
            return;
        }

        sightDao.deleteAll();
        sightDao.saveAll(sights);
        log.info("Saved all sights to database.");
    }

    public List<Sight> getSightsByDistrict(String district) {
        if (!district.endsWith("區")) district += "區";
        return sightDao.findByDistrict(district);
    }

    public Sight getSightById(Long id) {
        Sight sight = sightDao.findBySightId(id);
        if (sight == null) {
            log.warn("Sight ID {} not found.", id);
            throw new NotFoundException("未知的景點 ID: " + id);
        }
        return sight;
    }

}
