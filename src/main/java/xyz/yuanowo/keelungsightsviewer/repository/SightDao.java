package xyz.yuanowo.keelungsightsviewer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import xyz.yuanowo.keelungsightsviewer.model.Sight;

import java.util.List;


@Repository
public interface SightDao extends MongoRepository<Sight, String> {

    Sight findBySightId(Long sightId);

    List<Sight> findByDistrict(String district);

}
