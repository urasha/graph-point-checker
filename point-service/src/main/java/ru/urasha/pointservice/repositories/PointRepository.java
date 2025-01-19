package ru.urasha.pointservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.urasha.pointservice.models.Point;

import java.util.List;

@Repository
public interface PointRepository extends CrudRepository<Point, Long> {

    List<Point> findAllByUserId(Long userId);
}
