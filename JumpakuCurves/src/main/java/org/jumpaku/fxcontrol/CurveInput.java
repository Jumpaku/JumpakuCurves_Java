package org.jumpaku.fxcontrol;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import main.old.curves.TimeSeriesData;

/** Model の機能を定義します. */
/*package private*/ interface CurveInputModel {
    void clear();
    void addPoint(Point2D p);
    void curveInputDone();
}

/** Control クラスを継承した CurveInput クラスで Model の実装を行います. */
public class CurveInput extends Control implements CurveInputModel{
    
    /** コントロールの点列データは Model の状態です. */
    private final List<TimeSeriesData<Point2D>> points = new LinkedList<>();

    /** 曲線入力完了時のイベントハンドラのプロパティ. */
    private final ObjectProperty<EventHandler<CurveInputEvent>>
        onCurveInputDone = new SimpleObjectProperty<EventHandler<CurveInputEvent>>(this, "onCurveInputDone", e -> {}){
            @Override protected void invalidated() { CurveInput.this.setEventHandler(CurveInputEvent.CURVE_INPUT_DONE, get()); }
        };
    public final EventHandler<CurveInputEvent> getOnCurveInputDone() { return onCurveInputDoneProperty().get(); }
    public final void setOnCurveInputDone(EventHandler<CurveInputEvent> onCurveInput) { onCurveInputDoneProperty().set(onCurveInput); }
    public ObjectProperty<EventHandler<CurveInputEvent>> onCurveInputDoneProperty() { return onCurveInputDone; }

    /**
     * スキンを生成します.
     * @return このコントロールのスキン
     */
    @Override protected Skin<?> createDefaultSkin() {
        return new CurveInputSkin(this);
    }
    
    /**
     * Model の状態変化を View に通知します.
     * View の render メソッドを呼び出して描画に必要な点列データを渡すと, View は UI を変化させます.
     */
    private void update(){
        ((CurveInputView)getSkin()).render(points.stream().map(TimeSeriesData::getData).collect(Collectors.toList()));
    }

    /**
     * コントロールの点列に点を追加して Model の状態を更新します.
     * @param p 追加する点
     */
    @Override public void addPoint(Point2D p) {
        points.add(TimeSeriesData.currentData(p));
        update();
    }

   /** コントロールの点列を削除して Model の状態を更新します. */
    @Override public void clear() {
        points.clear();
        update();
    }

    /** 点列の入力を完了し, CURVE_INPUT_DONE イベントを発行します. */
    @Override public void curveInputDone() {
        fireEvent(new CurveInputEvent(points, ((CurveInputSkin)getSkin()).getGraphicsContext2D(), CurveInputEvent.CURVE_INPUT_DONE));
    }
}