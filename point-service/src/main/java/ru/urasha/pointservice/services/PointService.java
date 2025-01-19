package ru.urasha.pointservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.urasha.pointservice.models.Point;
import ru.urasha.pointservice.repositories.PointRepository;
import ru.urasha.pointservice.security.CustomAuthentication;
import ru.urasha.pointservice.utils.HitChecker;

import java.util.List;

@Service
public class PointService {

    private final PointRepository pointRepository;
    private final HitChecker hitChecker;

    @Autowired
    public PointService(PointRepository pointRepository, HitChecker hitChecker) {
        this.pointRepository = pointRepository;
        this.hitChecker = hitChecker;
    }

    public void save(Point point) {
        point.setHit(checkHit(point));
        point.setUserId(getCurrentUserId());
        pointRepository.save(point);
    }

    public List<Point> findAllForCurrentUser() {
        return pointRepository.findAllByUserId(getCurrentUserId());
    }

    public boolean checkHit(Point point) {
        return hitChecker.checkHit(point);
    }

    private Long getCurrentUserId() {
        CustomAuthentication authentication =
                (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return authentication.getUserId();
    }
}
