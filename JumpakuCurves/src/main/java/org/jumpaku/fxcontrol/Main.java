package org.jumpaku.fxcontrol;

import java.util.Comparator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javaslang.collection.Array;
import javaslang.collection.Stream;
import main.old.curves.TimeSeriesData;
import main.old.curves.fitting.SplineFitting;
import main.old.curves.spline.BSpline;
import main.old.curves.spline.Spline;
import main.old.curves.vector.Point;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        // 曲線入力コントロールの生成
        CurveInput curveInput = new CurveInput();
        // 曲線入力コントロールにイベントハンドラを設定
        curveInput.setOnCurveInputDone(e -> {
            GraphicsContext ctx = e.getGraphicContext();
            Array<TimeSeriesData<Point>> data = e.getPoints()
                    .map(d -> new TimeSeriesData<>(Point.of(d.getData().getX(), d.getData().getY()), d.getTime()))
                    .sorted(Comparator.comparing(TimeSeriesData::getTime));
            Array<Double> knots = BSpline.createUniformedClampedKnots(3, data.head().getTime(), data.last().getTime(), 0.03125);
            Array<Point> controlPoints = SplineFitting.coreateControlPoints(
                    3, 0.03125, e.getPoints().map(p -> new TimeSeriesData<>(Point.of(p.getData().getX(), p.getData().getY()), p.getTime())));
            
            Spline spline = BSpline.create(knots, controlPoints, 3, 2);
            Stream.range(0, 256).map(i -> (256 - i)/256.0 * spline.getDomain().getFrom() + i/256.0*spline.getDomain().getTo())
                    .map(spline::evaluate)
                    .forEach(p -> {
                        ctx.setFill(Color.RED);
                        ctx.fillOval(p.get(0)-2, p.get(1)-2, 4, 4);
                        ctx.setFill(Color.BLACK);
                    });
            spline.getControlPoints().forEach(cp -> {
                    ctx.setFill(Color.BLUE);
                    ctx.fillOval(cp.get(0)-4, cp.get(1)-4, 8, 8);
                    ctx.setFill(Color.BLACK);
            });
            ctx.setStroke(Color.BLUE);
            ctx.strokePolyline(spline.getControlPoints().map(p -> p.get(0)).toJavaStream().mapToDouble(d->d).toArray(), 
                    spline.getControlPoints().map(p -> p.get(1)).toJavaStream().mapToDouble(d->d).toArray(),
                    spline.getControlPoints().size());
            ctx.setStroke(Color.BLACK);            
        });
        Scene scene = new Scene(curveInput);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}