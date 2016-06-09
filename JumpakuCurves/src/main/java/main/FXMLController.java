package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javaslang.collection.Stream;
import javax.imageio.ImageIO;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.jumpaku.curves.bezier.twod.BezierCurve2D;
import org.jumpaku.curves.spline.BSplineCurveDeBoor;
import org.jumpaku.curves.spline.BSplineCurveReduction;
import org.jumpaku.curves.spline.SplineCurve;

public class FXMLController implements Initializable {
    
    @FXML
    private Canvas canvas;
    
    private List<Vector2D> controlPoints = new LinkedList<>();
    private SplineCurve<Euclidean2D, Vector2D> curve = null;
    
    @FXML
    private synchronized void onClick(MouseEvent e){
        controlPoints.add(new Vector2D(e.getX(), e.getY()));
        //curve = BezierCurve2D.create(controlPoints);
        
        render();
    }
    
    @FXML
    private synchronized void onCompute(ActionEvent e){
        curve = new BSplineCurveDeBoor<>(Stream.rangeClosed(0, controlPoints.size() + 3).map(i -> Double.valueOf(i)).toJavaList(), controlPoints, 3);
        render();
    }
    
    @FXML
    private synchronized void onElevate(ActionEvent e){
        //curve = curve.elevate();
        controlPoints = curve.getControlPoints();
        render();
    }
    
    @FXML
    private synchronized void onReduce(ActionEvent e){
        //curve = curve.reduce();
        controlPoints = curve.getControlPoints();
        render();
    }
    
    @FXML
    private synchronized void onClear(ActionEvent e){
        curve = null;
        controlPoints = new LinkedList<>();
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, 640, 430);
    }
    
    @FXML
    private synchronized void onSaveGraph(ActionEvent e){
        if(curve == null)
            return;
        WritableImage image = canvas.snapshot(null, null);
        String filePath = curve.getDegree() + ".png";
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bufferedImage, "png", new File(filePath));
        }
        catch (IOException ex) {
        }
    }

    private void render(){
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, 640, 430);

        context.setLineWidth(1);
        if(curve != null){
            renderCurve(context, curve, Color.CADETBLUE);
        }
        renderPoints(context, controlPoints, Color.GOLD);
        renderPolyline(context, controlPoints, Color.GOLD, false);
    }
    
    private static void renderCurve(GraphicsContext context, SplineCurve<Euclidean2D, Vector2D> curve, Paint color){
        context.setStroke(color);
        final Double d = Math.pow(2, -5);
        List<Vector2D> points = Stream.gen(curve.getDomain().getFrom(), t -> t + d)
                .takeWhile(t -> t < curve.getDomain().getTo())
                .map(curve::evaluate)
                .toJavaList();
        context.setLineWidth(3);
        renderPolyline(context, points, color, Boolean.FALSE);
        SplineCurve<Euclidean2D, Vector2D> c = new BSplineCurveReduction<>(curve.getKnots(), curve.getControlPoints(), 3);
        List<Vector2D> points_ = Stream.gen(c.getDomain().getFrom(), t -> t + d)
                .takeWhile(t -> t < c.getDomain().getTo())
                .map(c::evaluate)
                .toJavaList();
        context.setLineWidth(1);
        renderPolyline(context, points_, Color.BEIGE, Boolean.FALSE);
    }
    
    private static void renderPoints(GraphicsContext context, List<Vector2D> points, Paint color){
        if(points.isEmpty())
            return;        context.setFill(color);
        points.forEach(p -> context.fillOval(p.getX()-4, p.getY()-4, 8, 8));

        context.setFill(Color.BLACK);
    }
    
    private static void renderPolyline(GraphicsContext context, List<Vector2D> points, Paint color, Boolean closed){
        if(points.isEmpty())
            return;
        context.setStroke(color);
        if(closed){
            context.strokePolygon(points.stream().mapToDouble(
                    v->v.getX()).toArray(), points.stream().mapToDouble(v->v.getY()).toArray(), points.size());
        }
        else{
            context.strokePolyline(points.stream().mapToDouble(
                    v->v.getX()).toArray(), points.stream().mapToDouble(v->v.getY()).toArray(), points.size());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
}
