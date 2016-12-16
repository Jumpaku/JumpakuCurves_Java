package org.jumpaku.fxcontrol;

import java.util.List;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javaslang.collection.Array;
import org.jumpaku.curves.TimeSeriesData;

/** 曲線入力イベント. */
public class CurveInputEvent extends Event{

    /** CURVE_INPUT_DONE イベントタイプを定義します. */
    public static final EventType<CurveInputEvent> CURVE_INPUT_DONE = new EventType<>("CURVE_INPUT_DONE");

    /** イベント発生時の点列データを保持します. */
    private final Array<TimeSeriesData<Point2D>> points;
    
    private final GraphicsContext graphicContext;

    /**
     * 曲線入力イベントオブジェクトを構築します.
     * @param points イベント発生時の点列データ
     * @param ctx
     * @param eventType 発生したイベントの種類
     */
    public CurveInputEvent(List<TimeSeriesData<Point2D>> points, GraphicsContext ctx, EventType<? extends Event> eventType) {
        super(eventType);
        this.points = Array.ofAll(points);
        this.graphicContext = ctx;
    }

    /**
     * 保持している点列データを返します.
     * @return 保持している点列データ
     */
    public Array<TimeSeriesData<Point2D>> getPoints(){
        return Array.ofAll(points);
    }

    public GraphicsContext getGraphicContext() {
        return graphicContext;
    }
}