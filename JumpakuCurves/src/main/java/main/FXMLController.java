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
import javaslang.collection.Array;
import javaslang.collection.Stream;
import javax.imageio.ImageIO;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.random.MersenneTwister;
import org.jumpaku.curves.bezier.twod.BezierCurve2D;
import org.jumpaku.curves.interpolation.BSplineInterpolater2D;
import org.jumpaku.curves.spline.BSplineCurveDeBoor;
import org.jumpaku.curves.spline.SplineCurve;
import org.jumpaku.curves.interpolation.Interpolater;
import org.jumpaku.curves.spline.BSplineCurve2D;

public class FXMLController implements Initializable {
    
    @FXML
    private Canvas canvas;
    
    //private List<Vector2D> controlPoints = new LinkedList<>();
    private SplineCurve<Euclidean2D, Vector2D> curve = null;
    private List<Vector2D> dataPoint = new LinkedList<>();
    
    @FXML
    private synchronized void onClick(MouseEvent e){
        //controlPoints.add(new Vector2D(e.getX(), e.getY()));
        dataPoint.add(new Vector2D(e.getX(), e.getY()));
        
        render();
    }
    
    @FXML
    private synchronized void onCompute(ActionEvent e){
        //curve = BezierCurve2D.create(Array.ofAll(controlPoints));
        Array<Double> knots = Stream.rangeClosed(0, dataPoint.size() + 3).map(i -> Double.valueOf(i)).toArray();
        Double d = (knots.get(knots.size()-4) - knots.get(3)) / (double)(dataPoint.size()-1);
        Array<Interpolater.Data<Euclidean2D, Vector2D>> data = Stream.from(0).map(i -> d*i + knots.get(3))
                .zip(dataPoint)
                .map(tmp -> tmp.transform((t, p) -> new Interpolater.Data<>(p, t))).toArray();
        curve = BSplineInterpolater2D.builder()
                .addAllData(data)
                .degree(3)
                .knots(knots)
                .build()
                .interpolate(); 
            
        //curve = new BSplineCurveDeBoor<>(knots, Array.ofAll(controlPoints), 3);

        render();
    }
    
    @FXML
    private synchronized void onElevate(ActionEvent e){
        //curve = curve.elevate();
        //controlPoints = curve.getControlPoints().toJavaList();
        //curve = curve.insertKnot(new MersenneTwister().nextDouble()*(curve.getDomain().getTo()-curve.getDomain().getFrom()) + curve.getDomain().getFrom());
        //controlPoints = curve.getControlPoints().toJavaList();
        render();
    }
    
    @FXML
    private synchronized void onReduce(ActionEvent e){
        //curve = curve.reduce();
        //controlPoints = curve.getControlPoints().toJavaList();
        render();
    }
    
    @FXML
    private synchronized void onClear(ActionEvent e){
        curve = null;
        //controlPoints = new LinkedList<>();
        dataPoint = new LinkedList<>();
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, 640, 430);
    }
    
    @FXML
    private synchronized void onSaveGraph(ActionEvent e){
        if(curve == null)
            return;
        WritableImage image = canvas.snapshot(null, null);
        String filePath = curve.getDegree() + ".png";//curve.getKnots().size()+ ".png";
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
            renderCurve(context, curve, Color.BLUE);
            renderPoints(context, curve.getControlPoints().toJavaList(), Color.BLUE);
            renderPolyline(context, curve.getControlPoints().toJavaList(), Color.BLUE, false);
    
            BSplineCurve2D compared = 
                    BSplineCurve2D.create(curve.getKnots(), curve.getControlPoints(), curve.getDegree());
         
            renderCurve(context, compared, Color.RED);
            renderPoints(context, compared.getControlPoints().toJavaList(), Color.RED);
            renderPolyline(context, compared.getControlPoints().toJavaList(), Color.RED, false);
        }
        renderPoints(context, dataPoint, Color.GREEN);
    }
    
    private static void renderCurve(GraphicsContext context, /*BezierCurve*/SplineCurve<Euclidean2D, Vector2D> curve, Paint color){
        final Double d = Math.pow(2, -7);
        List<Vector2D> points = Stream.gen(/*0.0*/curve.getKnots().head(), t -> t + d)
                .takeWhile(t -> t <= curve.getKnots().last())
                .filter(t -> curve.getDomain().isIn(t))
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
    }    
}
