package mpc.jumpaku.curves;

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
import mpc.jumpaku.curves.beziercurve.BezierCurve;
import mpc.jumpaku.curves.beziercurve.BezierCurveByBernstein;
import mpc.jumpaku.curves.transform.Affine2D;
import mpc.jumpaku.curves.transform.Affine2DChain;
import mpc.jumpaku.curves.transform.Matrix3x3;
import mpc.jumpaku.curves.utils.GeomUtils;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class FXMLController implements Initializable {
    
    @FXML
    private Canvas canvas;
    
    @FXML
    private Slider divideAt;
    
    private List<Vector2D> firstCp = new LinkedList<>();
    private List<Vector2D> secondCp = new LinkedList<>();
    private BezierCurve<Vector2D> curve = null;
    
    @FXML
    private synchronized void onClick(MouseEvent e){
        LinkedList<Vector2D> controlPoints = new LinkedList<>();
        if(curve != null){
            controlPoints.addAll(curve.getControlPoints());
        }
        controlPoints.add(new Vector2D(e.getX(), e.getY()));
        curve = new BezierCurveByBernstein<>(controlPoints);
        render();
    }
    
    @FXML
    private synchronized void onTransform(ActionEvent e){
        Affine2D affine = Affine2DChain.identity();//Matrix3x3.identity();//
        affine = affine.translate(new Vector2D(-100, -100)).refrectOrigin().translate(new Vector2D(100,100));
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

        List<Vector2D> convexHull = GeomUtils.createConvexHull(curve);
        renderPolyline(context, convexHull, Color.AQUAMARINE, true);
        
        renderPoints(context, firstCp, Color.RED);
        renderPoints(context, secondCp, Color.RED);
        renderPolyline(context, firstCp, Color.RED, false);  
        renderPolyline(context, secondCp, Color.RED, false);       
    }
    
    private static void renderCurve(GraphicsContext context, Curve<Vector2D> curve, Paint color){
        context.setStroke(color);
        final Double d = Math.pow(2, -10);
        Stream<Vector2D> begins = Stream.gen(0.0, t -> t + d).takeWhile(t -> t <= 1.0).map(curve::evaluate);
        Stream<Vector2D> ends = Stream.gen(0.0, t -> t + d).takeWhile(t -> t <= 1.0).map(curve::evaluate).drop(1);
        context.beginPath();
        begins.zip(ends)
                .forEach(p -> {
                    context.moveTo(p._1().getX(), p._1().getY());
                    context.lineTo(p._2().getX(), p._2().getY());
                });        
        context.stroke();
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
        Stream<Vector2D> begins = Stream.ofAll(points);
        Stream<Vector2D> ends = Stream.ofAll(points).drop(1);
        context.beginPath();
        begins.zip(closed ? ends.append(points.get(0)) : ends)
                .forEach(p -> {
                    context.moveTo(p._1().getX(), p._1().getY());
                    context.lineTo(p._2().getX(), p._2().getY());
                });
        
        context.stroke();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        divideAt.valueProperty().addListener((v, p, n)->{
            synchronized(this){
                if(curve != null){
                    List<BezierCurve<Vector2D>> divideds = curve.divide((Double) n);
                    firstCp = divideds.get(0).getControlPoints();
                    secondCp = divideds.get(1).getControlPoints();
                    render();
                }
            }
        });
    }    
}
