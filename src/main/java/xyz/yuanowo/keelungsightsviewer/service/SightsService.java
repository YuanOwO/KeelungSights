package xyz.yuanowo.keelungsightsviewer.service;

import org.springframework.stereotype.Service;
import xyz.yuanowo.keelungsightsviewer.crawler.SightsCrawler;
import xyz.yuanowo.keelungsightsviewer.exception.InternalServerError;
import xyz.yuanowo.keelungsightsviewer.exception.NotFoundException;
import xyz.yuanowo.keelungsightsviewer.model.Sight;

import java.io.IOException;
import java.util.List;


@Service
public class SightsService {

    public List<Sight> getSightsByDistrict(String district) {
        SightsCrawler crawler = new SightsCrawler();

        try {
            return crawler.getSightsList(district);
        } catch (IOException e) {
            throw new InternalServerError("未知的錯誤: " + e.getMessage());
        }
    }

    public Sight getSightById(Long id) {
        SightsCrawler crawler = new SightsCrawler();

        try {
            return crawler.getSightDetails(String.format("/tourguide/scenery%d.html", id));
        } catch (IOException e) {
            throw new NotFoundException("未知的景點 ID: " + id);
        }
    }

}
