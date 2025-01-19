package ru.urasha.pointservice.utils;

import org.springframework.stereotype.Component;
import ru.urasha.pointservice.models.Point;

@Component
public class HitChecker {

    public boolean checkHit(Point point) {
        if (point.getX() < 0 & point.getY() < 0) {
            return false;
        }

        return checkTriangle(point) || checkRectangle(point) || checkCircle(point);
    }

    private boolean checkTriangle(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();

        return x <= 0 && y >= 0 && y <= (-x + r / 2);
    }

    private boolean checkRectangle(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();

        return x >= 0 && y <= 0 && x <= r && y >= -r;
    }

    private boolean checkCircle(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();

        return x >= 0 && y >= 0 && (x * x + y * y) <= ((r * r) / 4);
    }
}
