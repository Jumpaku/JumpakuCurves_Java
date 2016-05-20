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
import javaslang.collection.Stream;
import javax.imageio.ImageIO;
import org.jumpaku.curves.beziercurve.BezierCurve;
import org.jumpaku.curves.beziercurve.twod.BezierCurve2D;
import org.jumpaku.curves.beziercurve.BezierCurveByBernstein;
import org.jumpaku.curves.transform.Affine2D;
import org.jumpaku.curves.transform.Affine2DChain;
import org.jumpaku.curves.transform.Matrix3x3;
import org.jumpaku.curves.utils.GeomUtils;
import org.apache.commons.math3.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class FXMLController implements Initializable {
    
    @FXML
    private Canvas canvas;
    
    @FXML
    private Slider divideAt;
    
    private List<Vector2D> firstCp = new LinkedList<>();
    private List<Vector2D> secondCp = new LinkedList<>();
    private BezierCurve2D curve = null;
    
    @FXML
    private synchronized void onClick(MouseEvent e){
        LinkedList<Vector2D> controlPoints = new LinkedList<>();
        if(curve != null){
            controlPoints.addAll(curve.getControlPoints());
        }
        controlPoints.add(new Vector2D(e.getX(), e.getY()));
        curve = BezierCurve2D.createBernstein(controlPoints);
        render();
    }
    
    @FXML
    private synchronized void onTransform(ActionEvent e){
        Affine2D affine = Affine2DChain.identity();//Matrix3x3.identity();//
        affine = affine.rotateAt(curve.getControlPoints().get(curve.getDegree()), new Vector2D(1.0,0.0), new Vector2D(1.0, 1.0));//translate(new Vector2D(-100, -100)).refrectOrigin().translate(new Vector2D(100,100));
        curve = curve.transform(affine);//Matrix3x3.createRotationAt(new Vector2D(320, 215), Math.PI/3));//RotationAt(new Vector2D(320, 215), Math.PI/3));//createScalingAt(new Vector2D(320, 215), 1.5));//.createShearingAt(new Vector2D(320, 215), 0.5, 0.0));//
        render();
    }
    @FXML
    private synchronized void onClear(ActionEvent e){
        firstCp = new LinkedList<>();
        secondCp = new LinkedList<>();
        curve = null;
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, 640, 430);
    }
    
    @FXML
    private synchronized void onElevate(ActionEvent e){
        if(curve != null){
            curve = curve.elevate();
            render();
        }
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
        renderCurve(context, curve, Color.CADETBLUE);

        renderPoints(context, curve.getControlPoints(), Color.GOLD);
        renderPolyline(context, curve.getControlPoints(), Color.GOLD, false);

        //List<Vector2D> convexHull = GeomUtils.createConvexHull(curve);
        //renderPolyline(context, convexHull, Color.AQUAMARINE, true);
        
        renderPoints(context, firstCp, Color.RED);
        renderPoints(context, secondCp, Color.RED);
        renderPolyline(context, firstCp, Color.RED, false);  
        renderPolyline(context, secondCp, Color.RED, false);       
    }
    
    private static void renderCurve(GraphicsContext context, BezierCurve<Euclidean2D, Vector2D> curve, Paint color){
        context.setStroke(color);
        final Double d = Math.pow(2, -5);
        List<Vector2D> points = Stream.gen(0.0, t -> t + d).takeWhile(t -> t <= 1.0).map(curve::evaluate).toJavaList();
        renderPolyline(context, points, color, Boolean.FALSE);
        /*Stream<Vector2D> tangents = Stream.gen(0.0, t -> t + d)
                .takeWhile(t -> t <= 1.0)
                .map((Double t) -> GeomUtils.computeTangent(curve, t));
        context.beginPath();
        tangents.zip(points)
                .forEach(t -> {
                    Vector2D to = t._1().add(t._2());
                    context.moveTo(t._2().getX(), t._2().getY());
                    context.lineTo(to.getX(), to.getY());
                });
        context.stroke();*/        
    }
    
    private static void renderPoints(GraphicsContext context, List<Vector2D> points, Paint color){
        if(points.isEmpty())
            return;        context.setFill(color);
        points.forEach(p -> context.fillOval(p.getX()-4, p.getY()-4, 8, 8));

        context.setFill(Color.BLACK);
        //context.fillOval(points.get(0).getX()-4, points.get(0).getY()-4, 8, 8);
        //context.fillOval(points.get(points.size()-1).getX()-4, points.get(points.size()-1).getY()-4, 8, 8);
    }
    
    private static void renderPolyline(GraphicsContext context, List<Vector2D> points, Paint color, Boolean closed){
        if(points.isEmpty())
            return;
        context.setStroke(color);
        if(closed){
            context.strokePolygon(points.stream().mapToDouble(v->v.getX()).toArray(), points.stream().mapToDouble(v->v.getY()).toArray(), points.size());
        }
        else{
            context.strokePolyline(points.stream().mapToDouble(v->v.getX()).toArray(), points.stream().mapToDouble(v->v.getY()).toArray(), points.size());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        divideAt.valueProperty().addListener((v, p, n)->{
            synchronized(this){
                if(curve != null){
                    List<BezierCurve2D> divideds = curve.divide((Double) n);
                    firstCp = divideds.get(0).getControlPoints();
                    secondCp = divideds.get(1).getControlPoints();
                    render();
                }
            }
        });
    }    
}
