/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mpc.jumpaku.curves.beziercurve;

import mpc.jumpaku.curves.domain.Domain;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import mpc.jumpaku.curves.Curve2D;
import mpc.jumpaku.curves.domain.ClosedDomain;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 *
 * @author ito
 */
public abstract class BezierCurve2D implements Curve2D{
    
    private final List<Vector2D> controlPoints;
    
    private final Domain domain;
    
    public BezierCurve2D(List<Vector2D> cp) {
        if(cp.isEmpty()){
            throw new IllegalArgumentException("The number of control points must be greater than 0 but the number of given control points was 0.");
        }
        controlPoints = new LinkedList<>();
        domain = new ClosedDomain(0.0, 1.0);
        cp.forEach(v -> controlPoints.add(v));
    }
    
    public BezierCurve2D(Vector2D... cp) {
        this(Arrays.asList(cp));
    }

    public final Domain getDomain() {
        return domain;
    }
    
    public List<Vector2D> getControlPoints() {
        return Collections.unmodifiableList(controlPoints);
    }
    
    public Integer getDegree(){
        return controlPoints.size() - 1;
    }

    
}
