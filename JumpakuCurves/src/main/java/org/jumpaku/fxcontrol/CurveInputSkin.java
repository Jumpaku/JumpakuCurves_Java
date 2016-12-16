package org.jumpaku.fxcontrol;

import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;

/** 曲線入力のコントローラ */
/*package private*/ class CurveInputController{
    /** Model を保持します. */
    private final CurveInputModel model;
    /**
     * モデルを設定するコンストラクタ.
     * @param model 設定するモデル
     */
    public CurveInputController(CurveInputModel model) {
        this.model = model;
    }
    /**
     * マウスイベントを受け取って, 点列を初期化し, 点列に押下点を追加します.
     * @param e マウスイベント
     */
    public void handleMousePressed(MouseEvent e){
        model.clear();
        model.addPoint(new Point2D(e.getX(), e.getY()));
    }
    /**
     * マウスイベントを受け取って, 点列にドラッグ点を追加します.
     * @param e マウスイベント
     */
    public void handleMouseDragged(MouseEvent e){
        model.addPoint(new Point2D(e.getX(), e.getY()));
    }
    /**
     * マウスイベントを受け取って, 入力を完了します.
     * @param e マウスイベント
     */
    public void handleMouseReleased(MouseEvent e){
        model.curveInputDone();
    }
}

/** View のインターフェイス. */
/*package private*/ interface CurveInputView{
    /**
     * Model の状態変化が通知される時に呼び出され, Model の点列データを受け取って, UI を更新します.
     * @param points Model の点列データ
     */
    void render(List<Point2D> points);
}
/** Skin インターフェイスを実現した CurveInputSkin クラスで View を実装します. */
/*package private*/ class CurveInputSkin implements Skin<CurveInput>, CurveInputView {

    /** スキンの UI を定義するシーングラフのルートノード. */
    private Canvas rootNode;
    
    /** スキンを適用する対象のコントロール. */
    private CurveInput control;

    /**
     * コントロールを受け取るコンストラクタ.
     * @param curveInput 曲線入力コントロール
     */
    public CurveInputSkin(CurveInput curveInput) {
        //UI を定義するシーングラフの組み立て
        this.rootNode = new Canvas(640, 480);
        //コントロールの保持
        this.control = curveInput;
        //シーングラフを構成するノードのイベントハンドラにコントローラのイベントハンドラを設定
        CurveInputController controller = new CurveInputController(curveInput);
        rootNode.addEventHandler(MouseEvent.MOUSE_PRESSED, controller::handleMousePressed);
        rootNode.addEventHandler(MouseEvent.MOUSE_DRAGGED, controller::handleMouseDragged);
        rootNode.addEventHandler(MouseEvent.MOUSE_RELEASED, controller::handleMouseReleased);
    }
    
    /**
     * スキンを適用する対象のコントロールを返します.
     * @return スキンを適用する対象のコントロール
     */
    @Override public CurveInput getSkinnable() {
        return control;
    }

    /**
     * スキンの UI を定義するシーングラフのルートノードを返します.
     * @return UI を定義するシーングラフのルートノード
     */
    @Override public Canvas getNode() {
        return rootNode;
    }

    /** 不要になったスキンのフィールドを null にします. */
    @Override public void dispose() {
        control = null;
        rootNode = null;
    }

    /**
     * 受け取った点列データから曲線を描画します.
     * @param points 点列データ
     */
    @Override public void render(List<Point2D> points){
        rootNode.getGraphicsContext2D().clearRect(0, 0, rootNode.getWidth(), rootNode.getHeight());
        if(points.size() == 1){
            Point2D p = points.get(0);
            rootNode.getGraphicsContext2D().fillOval(p.getX() - 0.5, p.getY() - 0.5, 1, 1);
        }
        else if(points.size() > 1){
            Point2D begin = points.get(0);
            for (Point2D end : points) {
                rootNode.getGraphicsContext2D().strokeLine(begin.getX(), begin.getY(), end.getX(), end.getY());
                begin = end;
            }
        }
    }
    
    /**
     * GraphicContext を返します.
     * @return GraphicContext
     */
    public GraphicsContext getGraphicsContext2D(){
        return rootNode.getGraphicsContext2D();
    }
}