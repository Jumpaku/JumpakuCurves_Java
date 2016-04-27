package mpc.jumpaku.curves;

import fj.F;
import fj.data.Stream;
import java.net.URL;
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
        controlPoints.add(new Vector2D(e.getX(), e.getY()));
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.clearRect(0, 0, 600, 400);

        final Curve2D decas = new BezierCurve2DByDeCasteljau(controlPoints);
        context.setStroke(Color.CORAL);
        context.setLineWidth(5);
        renderCurve(context, decas);

        final Curve2D bern = new BezierCurve2DByBernstein(controlPoints);
        context.setStroke(Color.CADETBLUE);
        context.setLineWidth(1);
        renderCurve(context, bern);
        
        context.setFill(Color.GOLD);
        renderPoints(context, controlPoints);
    }
    
    private static void renderCurve(GraphicsContext context, Curve2D curve){
        final Double d = Math.pow(2, -4);
        Stream<Vector2D> points = Stream.iterateWhile(t -> t + d, t -> t <= 1.0, 0.0)
                .map(curve::evaluate);
        Vector2D begin = points.head();
        context.beginPath();
        context.moveTo(begin.getX(), begin.getY());
        points.drop(1).forEach(p -> context.lineTo(p.getX(), p.getY()));

        context.stroke();
    }
    private static void renderPoints(GraphicsContext context, List<Vector2D> points){
        points.forEach(p -> context.fillOval(p.getX()-4, p.getY()-4, 8, 8));
    }
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
}
