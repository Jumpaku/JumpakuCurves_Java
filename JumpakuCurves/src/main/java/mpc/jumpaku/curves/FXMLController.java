package mpc.jumpaku.curves;

import fj.data.Stream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import mpc.jumpaku.curves.beziercurve.BezierCurve2DByBernstein;
import mpc.jumpaku.curves.beziercurve.BezierCurve2DByDeCasteljau;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class FXMLController implements Initializable {
    
    @FXML
    private Canvas canvas;
    
    private final List<Vector2D> controlPoints = new LinkedList<>();
    
    @FXML
    private void onClick(MouseEvent e){
        synchronized(controlPoints){
            controlPoints.add(new Vector2D(e.getX(), e.getY()));
            GraphicsContext context = canvas.getGraphicsContext2D();
            context.clearRect(0, 0, 600, 400);

            final Curve<Vector2D> decas = new BezierCurve2DByDeCasteljau(controlPoints);
            context.setStroke(Color.CORAL);
            context.setLineWidth(5);
            renderCurve(context, decas);

            final Curve<Vector2D> bern = new BezierCurve2DByBernstein(controlPoints);
            context.setStroke(Color.CADETBLUE);
            context.setLineWidth(1);
            renderCurve(context, bern);

            context.setFill(Color.GOLD);
            renderPoints(context, controlPoints);
            context.setStroke(Color.GOLD);
            renderPolyline(context, controlPoints);

            outputScript(System.out, controlPoints.size());
        }
    }
    
    private static void renderCurve(GraphicsContext context, Curve<Vector2D> curve){
        final Double d = Math.pow(2, -6);
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
    
    private static void renderPoints(GraphicsContext context, List<Vector2D> points){
        points.forEach(p -> context.fillOval(p.getX()-4, p.getY()-4, 8, 8));
    }
    
    private static void renderPolyline(GraphicsContext context, List<Vector2D> points){
        Stream<Vector2D> ps = Stream.iterableStream(points);
        context.beginPath();
        ps.zip(ps.drop(1))
                .forEach(p -> {
                    context.moveTo(p._1().getX(), p._1().getY());
                    context.lineTo(p._2().getX(), p._2().getY());
                });
        
        context.stroke();
    }
    
    private static void outputScript(PrintStream ostream, Integer n){
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
        
    }    
}
