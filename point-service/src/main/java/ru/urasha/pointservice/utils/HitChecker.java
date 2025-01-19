package ru.urasha.pointservice.utils;

import org.springframework.stereotype.Component;
import ru.urasha.pointservice.models.Point;

@Component
public class HitChecker {

    public boolean checkHit(Point point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();

        if (x < 0 & y < 0) {
            return false;
        }

        return checkTriangle(x, y, r) || checkRectangle(x, y, r) || checkCircle(x, y, r);
    }

    private boolean checkTriangle(double x, double y, double r) {
        return x <= 0 && x >= -r / 2 && y >= 0 && y <= r && y <= 2 * x + r;
    }

    private boolean checkRectangle(double x, double y, double r) {
        return x >= 0 && y <= 0 && x <= r && y >= -r;
    }

    private boolean checkCircle(double x, double y, double r) {
        return x >= 0 && y >= 0 && (x * x + y * y) <= ((r * r) / 4);
    }
}
