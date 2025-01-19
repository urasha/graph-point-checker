package ru.urasha.pointservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.urasha.pointservice.models.Point;
import ru.urasha.pointservice.services.PointService;

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

    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> save(@Validated @RequestBody Point point) {
        logger.info("Handling point: " + point);

        pointService.save(point);

        Map<String, String> response = Map.of("hit", "true");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
