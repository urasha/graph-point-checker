package ru.urasha.pointservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.urasha.pointservice.models.Point;
import ru.urasha.pointservice.services.PointService;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/points")
public class PointController {

    private final PointService pointService;
    private final Logger logger = Logger.getLogger(PointController.class.getName());

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Point>>> getAllPoints() {
        List<Point> allPoints = pointService.findAllForCurrentUser();

        logger.info(allPoints.size() + " points found for current user");

        Map<String, List<Point>> response = Map.of("points", allPoints);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Boolean>> save(@Validated @RequestBody Point point) {
        logger.info("Handling point: " + point);

        pointService.save(point);

        boolean isHit = pointService.checkHit(point);

        Map<String, Boolean> response = Map.of("hit", isHit);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
