package com.company.turtleTask;

import com.company.scanner.ParallelScanner;

public class StatePair {
    public Double angle; // radians
    public Double distance;

    public StatePair(Double angle, Double distance) {
        this.angle = angle;
        this.distance = distance;
    }

    public static StatePair operator(StatePair a, StatePair b) {
        double delta = b.angle - a.angle;
        double d = Math.sqrt(a.distance * a.distance + b.distance * b.distance + 2 * a.distance * b.distance * Math.cos(delta));
        double phi = a.angle + Math.asin((b.distance * Math.sin(delta)) / d);
        if (phi > 2 * Math.PI) {
            phi -= 2 * Math.PI;
        }
        return new StatePair(phi, d);
    }

    @Override
    public String toString() {
        return "(" + angle + ", " + distance + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StatePair)) {
            return false;
        }
        StatePair sp = (StatePair) obj;
        return Double.compare(angle, sp.angle) == 0
                && Double.compare(distance, sp.distance) == 0;
    }


}
