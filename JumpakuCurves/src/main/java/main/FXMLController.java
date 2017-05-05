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
import javaslang.Tuple2;
import javaslang.collection.Stream;
import javax.imageio.ImageIO;

import main.old.curves.bezier.Bezier2D;
import main.old.curves.vector.Point2D;
import main.old.curves.bezier.RationalBezier;

public class FXMLController implements Initializable {
    
    @FXML
    private Canvas canvas;
    
    private List<Point2D> controlPoints = new LinkedList<>();
    private Bezier2D curve = null;
    
    @FXML
    private synchronized void onClick(MouseEvent e){
        controlPoints.add(new Point2D(e.getX(), e.getY()));
        //dataPoint.add(new Point2D(e.getX(), e.getY()));
        
        render();
    }
    
    @FXML
    private synchronized void onCompute(ActionEvent e){
        Point2D p0 = new Point2D(100.0, 100.0), a = new Point2D(200.0, 350.0), p2 = new Point2D(300.0, 100.0);
        Tuple2<RationalBezier, RationalBezier> rbs = RationalBezier.circularArc(p0, a, p2);
        /*Stream.iterate(0.0, v -> v + Math.pow(2, -8)).takeWhile(v -> v <= 1.0)
                .map(rbs._1())
                .forEach(p -> canvas.getGraphicsContext2D().fillOval(p.get(0)-1, p.get(1)-1, 2, 2));*/
        Stream.iterate(0.0, v -> v + Math.pow(2, -8)).takeWhile(v -> v <= 1.0)
                .map(rbs._2())
                .forEach(p -> canvas.getGraphicsContext2D().fillOval(p.get(0)-1, p.get(1)-1, 2, 2));
        //curve = BSpline2D.create(Array.rangeBy(0, controlPoints.size() + 3 + 1.0, 1.0), Array.ofAll(controlPoints), 3);
        //curve = Bezier2D.create(Array.ofAll(controlPoints));
        //Array<Double> knots = Stream.rangeClosed(0, dataPoint.size() + 3).map(i -> Double.valueOf(i)).toArray();
        //Double d = (knots.get(knots.size()-4) - knots.get(3)) / (double)(dataPoint.size()-1);
        //Array<Data<Point2D>> data = Stream.from(0).map(i -> d*i + knots.get(3))
        //        .zip(dataPoint)
        //        .map(tmp -> tmp.transform((t, p) -> new Data<>(p, t))).toArray();
        //curve = BSplineInterpolater2D.builder()
        //        .addAllData(data)
        //        .degree(3)
        //        .knots(knots)
        //        .build()
        //        .interpolate(); 
            
        //curve = new BSplineCurveDeBoor<>(knots, Array.ofAll(controlPoints), 3);

        //render();
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
        controlPoints = new LinkedList<>();
        //dataPoint = new LinkedList<>();
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
            //renderPoints(context, curve.getControlPoints().map(p->new Point2D(p)).toJavaList(), Color.BLUE);
            renderPolyline(context, curve.getControlPoints().map(p->new Point2D(p)).toJavaList(), Color.BLUE, false);
    
            /*BSpline2D compared = 
                    BSpline2D.create(curve.getKnots(), curve.getControlPoints(), curve.getDegree());
         
            renderCurve(context, compared, Color.RED);
            renderPoints(context, compared.getControlPoints().toJavaList(), Color.RED);
            renderPolyline(context, compared.getControlPoints().toJavaList(), Color.RED, false);*/
        }
        renderPoints(context, controlPoints, Color.GREEN);
    }
    
    private static void renderCurve(GraphicsContext context, Bezier2D curve, Paint color){
        final Double d = Math.pow(2, -5);

        /*context.setStroke(Color.RED);
        Stream.gen(curve.getDomain().getFrom(), t -> t + d)
                .takeWhile(t -> t < curve.getDomain().getTo())
                .map(t -> new Tuple2<Point2D, Point2D>(curve.evaluate(t), curve.evaluate(t).move(curve.computeTangent(t))))
                .forEach(t -> context.strokeLine(t._1().getX(), t._1().getY(), t._2().getX(), t._2().getY()));
        
        context.setStroke(Color.BLUE);

        List<Point2D> points = Stream.gen(curve.getDomain().getFrom(), t -> t + d)
                .takeWhile(t -> t < curve.getDomain().getTo())
                .map(curve::evaluate)
                .toJavaList();
        renderPolyline(context, points, color, Boolean.FALSE);*/
        

    }
    
    private static void renderPoints(GraphicsContext context, List<Point2D> points, Paint color){
        if(points.isEmpty())
            return;

        context.setFill(color);
        points.forEach(p -> context.fillOval(p.getX()-4, p.getY()-4, 8, 8));

        context.setFill(Color.BLACK);
    }
    
    private static void renderPolyline(GraphicsContext context, List<Point2D> points, Paint color, Boolean closed){
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
        canvas.getGraphicsContext2D().clearRect(0, 0, 640, 430);
        canvas.getGraphicsContext2D().setLineWidth(1);
        
        /*curve = new RationalBezierFast(Array.closed(
                new WeightedPoint2D(200+150.0, 200+0.0, 1.0),
                new WeightedPoint2D(200+150.0, 200+50.0, 0.0),
                new WeightedPoint2D(200+0.0, 200+50.0, 1.0)), 2);
        renderCurve(canvas.getGraphicsContext2D(), curve, Color.RED);
        renderPoints(canvas.getGraphicsContext2D(), curve.getControlPoints().map(p->new Point2D(p)).toJavaList(), Color.BLUE);
        renderPolyline(canvas.getGraphicsContext2D(), curve.getControlPoints().map(p->new Point2D(p)).toJavaList(), Color.BLUE, false);
        curve = new RationalBezierFast(Array.closed(
                new WeightedPoint2D(200+0.0, 200+50.0, 1.0),
                new WeightedPoint2D(200-150.0, 200+50.0, 0.0),
                new WeightedPoint2D(200-150.0, 200+0.0, 1.0)), 2);
        renderCurve(canvas.getGraphicsContext2D(), curve, Color.RED);
        renderPoints(canvas.getGraphicsContext2D(), curve.getControlPoints().map(p->new Point2D(p)).toJavaList(), Color.BLUE);
        renderPolyline(canvas.getGraphicsContext2D(), curve.getControlPoints().map(p->new Point2D(p)).toJavaList(), Color.BLUE, false);
        curve = new RationalBezierFast(Array.closed(
                new WeightedPoint2D(200-150.0, 200+0.0, 1.0),
                new WeightedPoint2D(200-150.0, 200-50.0, 0.0),
                new WeightedPoint2D(200+0.0, 200-50.0, 1.0)), 2);
        renderCurve(canvas.getGraphicsContext2D(), curve, Color.RED);
        renderPoints(canvas.getGraphicsContext2D(), curve.getControlPoints().map(p->new Point2D(p)).toJavaList(), Color.BLUE);
        renderPolyline(canvas.getGraphicsContext2D(), curve.getControlPoints().map(p->new Point2D(p)).toJavaList(), Color.BLUE, false);
        curve = new RationalBezierFast(Array.closed(
                new WeightedPoint2D(200+0.0, 200-50.0, 1.0),
                new WeightedPoint2D(200+150.0, 200-50.0, 0.0),
                new WeightedPoint2D(200+150.0, 200+0.0, 1.0)), 2);
        renderCurve(canvas.getGraphicsContext2D(), curve, Color.RED);
        renderPoints(canvas.getGraphicsContext2D(), curve.getControlPoints().map(p->new Point2D(p)).toJavaList(), Color.BLUE);
        renderPolyline(canvas.getGraphicsContext2D(), curve.getControlPoints().map(p->new Point2D(p)).toJavaList(), Color.BLUE, false);*/
    }    
}
