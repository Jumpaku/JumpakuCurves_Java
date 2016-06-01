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
import javafx.scene.control.Slider;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import javax.imageio.ImageIO;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.jumpaku.curves.Curve;
import org.jumpaku.curves.bezier.twod.BezierCurve2D;
import org.jumpaku.curves.spline.SplineCurve2D;

public class FXMLController implements Initializable {
    
    @FXML
    private Canvas canvas;
    
    private final List<Vector2D> controlPoints = new LinkedList<>();
    private BezierCurve2D curve = null;
    private final Integer n = 3;
    
    Array<Double> knots;
    
    @FXML
    private synchronized void onClick(MouseEvent e){
        controlPoints.add(new Vector2D(e.getX(), e.getY()));
        curve = BezierCurve2D.createBernstein(controlPoints);
        render();
    }
    
    @FXML
    private synchronized void onClear(ActionEvent e){
        curve = null;
        controlPoints.clear();
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
    
    private static void renderCurve(GraphicsContext context, BezierCurve2D curve, Paint color){
        context.setStroke(color);
        final Double d = Math.pow(2, -5);
        List<Vector2D> points = Stream.gen(0.0, t -> t + d)
                .takeWhile(t -> t <= 1)
                .map(curve::evaluate)
                .toJavaList();
        renderPolyline(context, points, color, Boolean.FALSE);
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
        /*divideAt.valueProperty().addListener((v, p, n)->{
            synchronized(this){
                if(curve != null){
                    List<BezierCurve2D> divideds = curve.divide((Double) n);
                    firstCp = divideds.get(0).getControlPoints();
                    secondCp = divideds.get(1).getControlPoints();
                    render();
                }
            }
        });*/
    }    
}
