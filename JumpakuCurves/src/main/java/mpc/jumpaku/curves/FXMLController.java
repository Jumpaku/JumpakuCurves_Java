package mpc.jumpaku.curves;

import fj.data.Stream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.Arrays;
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
import javax.imageio.ImageIO;
import mpc.jumpaku.curves.beziercurve.BezierCurve;
import mpc.jumpaku.curves.beziercurve.BezierCurveByBernstein;
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
        renderPolyline(context, curve.getControlPoints(), Color.GOLD);

        renderPoints(context, firstCp, Color.RED);
        renderPoints(context, secondCp, Color.RED);
        renderPolyline(context, firstCp, Color.RED);  
        renderPolyline(context, secondCp, Color.RED);       
    }
    
    private static void renderCurve(GraphicsContext context, Curve<Vector2D> curve, Paint color){
        context.setStroke(color);
        final Double d = Math.pow(2, -5);
        Stream<Vector2D> points = Stream.iterateWhile(t -> t + d, t -> t <= 1.0, 0.0)
                .map(curve::evaluate);
        context.beginPath();
        points.zip(points.drop(1))
                .forEach(p -> {
                    context.moveTo(p._1().getX(), p._1().getY());
                    context.lineTo(p._2().getX(), p._2().getY());
                });
        
        context.stroke();
    }
    
    private static void renderPoints(GraphicsContext context, List<Vector2D> points, Paint color){
        context.setFill(color);
        points.forEach(p -> context.fillOval(p.getX()-4, p.getY()-4, 8, 8));
        if(points.isEmpty())
            return;
        context.setFill(Color.BLACK);
        context.fillOval(points.get(0).getX()-4, points.get(0).getY()-4, 8, 8);
        context.fillOval(points.get(points.size()-1).getX()-4, points.get(points.size()-1).getY()-4, 8, 8);
    }
    
    private static void renderPolyline(GraphicsContext context, List<Vector2D> points, Paint color){
        context.setStroke(color);
        Stream<Vector2D> ps = Stream.iterableStream(points);
        context.beginPath();
        ps.zip(ps.drop(1))
                .forEach(p -> {
                    context.moveTo(p._1().getX(), p._1().getY());
                    context.lineTo(p._2().getX(), p._2().getY());
                });
        
        context.stroke();
    }
    
    @FXML
    private void onSaveScript(ActionEvent e){
        String path = "./Berntein.plt";
        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException ex) {
            }
        }
        try{
            writeScript(new PrintStream(new FileOutputStream(file)), curve.getDegree());
        } catch (FileNotFoundException ex) {
        }
    }
    private static void writeScript(PrintStream ostream, Integer n){
        ostream.println(String.join(System.lineSeparator(), Arrays.asList(new String[]{
                "n="+n,
                "set terminal png size 640,480",
                "set key outside",
                "set size ratio 1.0",
                "set xrange [0.0:1.0]",
                "set yrange [0.0:1.0]",
                "set xlabel \"t\"",
                "set ylabel \"Berunstein(n,i,t)\"",
                "set samples 1000",
                "comb(n,r) = (n==0 || r==0 || n==r) ? 1 : (n-r<r) ? comb(n,n-r) : comb(n-1, r-1)+comb(n-1,r)",
                "bernstein(n,r,t) = comb(n,r)*((1.0-t)**(n-r))*(t**r)",
                "plot bernstein(n,0,x) title sprintf(\"Bernstein(%d,%d,t)\", n, 0)",
                "replot for [i=1:n] bernstein(n,i,x) title sprintf(\"Bernstein(%d,%d,t)\", n, i)",
                "set output \"Bernstein.png\"",
                "replot"})));
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
