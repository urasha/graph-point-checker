package ru.urasha.pointservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.urasha.pointservice.models.Point;
import ru.urasha.pointservice.repositories.PointRepository;
import ru.urasha.pointservice.security.CustomAuthentication;

@Service
public class PointService {

    private final PointRepository pointRepository;

    @Autowired
    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public void save(Point point) {
        point.setHit(true);
        point.setUserId(getCurrentUserId());

        pointRepository.save(point);
    }

    public Long getCurrentUserId() {
        CustomAuthentication authentication =
                (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getUserId();
    }
}
