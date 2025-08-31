package xyz.yuanowo.keelungsightsviewer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.yuanowo.keelungsightsviewer.model.Sight;

import java.util.List;


@Repository
public interface SightDao extends JpaRepository<Sight, Integer> {

    Sight findById(Long id);

    List<Sight> findByDistrict(String district);

}
