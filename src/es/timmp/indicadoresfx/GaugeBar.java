package es.timmp.indicadoresfx;

import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.geometry.Bounds;

import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GaugeBar extends Control {

    public static final EventType<Event> EVENT_TYPE_REDRAW = new EventType<Event>("EVENT_TYPE_REDRAW");

    public static final int DISC_BACKGORUND_COLOR = 1;
    public static final int TEXT_VALUE_COLOR = 2;
    public static final int TEXT_UNIT_COLOR = 3;
    public static final int TEXT_TITLE_COLOR = 4;
    public static final int DISC_MAX_COLOR = 5;
    public static final int DISC_VALUE_COLOR = 6;
    public static final int DISC_TEXT_COLOR = 7;

    private int maxValue = 120;
    private int minValue = 0;
    private int value = 0;
    private int memoriValue = 0;
    private int size = 200;
    private String units = "db";

    private ObservableMap<Integer, Paint> colores;

    private String title = "";

    private ObservableList<Secciones> secciones;

    public GaugeBar() {

        setSkin(new GaugeBarSkin(this));
        addEvents();
    }

    private void addEvents() {
        this.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                Pane papa = (Pane) getParent();
                double w = papa.widthProperty().get();
                double h = papa.heightProperty().get();
                //System.out.println("w: " + w + " h: " + h);
                double s = (w < h ? w : h);
                Number n = s / 2;
                //System.out.println("SIZE : " + n.intValue());
                setSize(n.intValue());
            }
        });
        this.secciones.addListener(new ListChangeListener() {
            @Override
            public void onChanged(ListChangeListener.Change c) {
                System.out.println("Añadiendo Seccion");
                fireEvent(new Event(secciones, GaugeBar.this, EVENT_TYPE_REDRAW));
            }
        });
        this.colores.addListener(new MapChangeListener() {
            @Override
            public void onChanged(MapChangeListener.Change c) {
                fireEvent(new Event(colores, GaugeBar.this, EVENT_TYPE_REDRAW));
            }
        });
    }

    public ObservableMap<Integer, Paint> getColores() {
        return colores;
    }

    public void setColores(ObservableMap<Integer, Paint> colores) {
        this.colores = colores;
        fireEvent(new Event(this.colores, this, EVENT_TYPE_REDRAW));
    }

    protected void defaultColors() {
        colores = FXCollections.observableHashMap();//new HashMap<Integer,Paint>();
        colores.put(DISC_BACKGORUND_COLOR, Color.KHAKI);
        colores.put(TEXT_VALUE_COLOR, Color.BLACK);
        colores.put(TEXT_UNIT_COLOR, Color.BLACK);
        colores.put(TEXT_TITLE_COLOR, Color.BLACK);
        colores.put(DISC_MAX_COLOR, Color.CHARTREUSE);
        colores.put(DISC_VALUE_COLOR, Color.CORNFLOWERBLUE);
        colores.put(DISC_TEXT_COLOR, Color.AZURE);
       
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
    
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        Number s = size;
        this.size = s.intValue();
        fireEvent(new Event(Integer.valueOf(this.size), this, EVENT_TYPE_REDRAW));
    }

    public int getMaxValue() {
        return this.maxValue;
    }

    public void setMaxValue(int maxValue) {
        if (maxValue < this.value) {
            throw new IllegalArgumentException("Max value must be bigger than value!");
        }
        this.maxValue = maxValue;
        fireEvent(new Event(Integer.valueOf(maxValue), this, EVENT_TYPE_REDRAW));
    }

    public void setMemoriValue(int value) {
        if (this.maxValue < value) {
            throw new IllegalArgumentException("Value must be smaller than max value!");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Value must be bigger than zero!");
        }
        this.memoriValue = value;
        fireEvent(new Event(Integer.valueOf(value), this, EVENT_TYPE_REDRAW));
    }

    public int getMemoriValue() {
        return this.memoriValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public void setValue(int value) {
       
        this.value = value;
        fireEvent(new Event(Integer.valueOf(value), this, EVENT_TYPE_REDRAW));
    }

    public int getValue() {
        return this.value;
    }

    public ObservableList<Secciones> getSecciones() {
        return secciones;
    }

    public void setSecciones(ObservableList<Secciones> secciones) {
        this.secciones = secciones;
        fireEvent(new Event(this.colores, this, EVENT_TYPE_REDRAW));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
