package mpc.jumpaku.curves;

import mpc.jumpaku.curves.beziercurve.BezierCurve2DByDeCasteljau;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mpc.jumpaku.curves.beziercurve.BezierCurve2DByBernstein;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        Scene scene = new Scene(root, 640, 480);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(new BezierCurve2DByDeCasteljau(new Vec2D(), new Vec2D(1.0, 0.0), new Vec2D(0.0, 1.0), new Vec2D(1.0, 1.0)).evaluate(0.5));
        System.out.println(new BezierCurve2DByBernstein(new Vec2D(), new Vec2D(1.0, 0.0), new Vec2D(0.0, 1.0), new Vec2D(1.0, 1.0)).evaluate(0.5));

        launch(args);
    }

}
