package es.timmp.indicadoresfx;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;

import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GaugeBarSkin implements Skin<GaugeBar> {

   
    private static final double GAUGE_MAX_SIZE = 0.25;
    private static final int TICK_DEGREE = 10;
    private static final double DIF_RADIO_MAX = 0.1;

    private final GaugeBar gaugeBar;
    private Group rootNode;
    
    

    public GaugeBarSkin(GaugeBar gaugeBar) {
        this.gaugeBar = gaugeBar;
        this.rootNode = (Group)getNode();
        hookEventHandler();

    }

    private void hookEventHandler() {
        getSkinnable().addEventHandler(GaugeBar.EVENT_TYPE_REDRAW, new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                redraw();
            }
        });
    }

    @Override
    public GaugeBar getSkinnable() {
        return this.gaugeBar;
    }

    @Override
    public final Node getNode() {
        if (this.rootNode == null) {
            this.rootNode = new Group();
            getSkinnable().setSecciones(FXCollections.observableList(new ArrayList<Secciones>()));
            getSkinnable().defaultColors();
        }
        redraw();
        return this.rootNode;
    }

    
    Number disk_gauge;
    Number disk_value;
    Number disk_max;
    Number disk_back_text;
    /**
     * Pinta todo el grafico.
     */
    protected void redraw() {
        
        
        
        
        List<Node> rootChildren = new ArrayList<Node>();
        rootChildren.add(createGauge());
        rootChildren.add(createSeccion());
        rootChildren.add(createGaugeBlend());
        rootChildren.add(createValue());
        rootChildren.add(createTitle());
        this.rootNode.getChildren().setAll(rootChildren);
    }

    @Override
    public void dispose() {
        // nothing to do
    }

  

    private Node createTitle() {
        Text text = new Text(0,getSkinnable().getSize()*0.50,getSkinnable().getTitle());
        text.setFont(new Font("Courier New",getSkinnable().getSize() * 0.20));
        text.setWrappingWidth(getSkinnable().getSize()*2);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setFill(getSkinnable().getColores().get(GaugeBar.TEXT_TITLE_COLOR));
       
        return text;
    }
    /**
     * 
     * @return Node circulo que representara la pista de fondo del recorrido de valores
     */
    private Node createGauge() {
        Circle circle = new Circle(getSkinnable().getSize(), getSkinnable().getSize(), getSkinnable().getSize());
        circle.setFill(getSkinnable().getColores().get(GaugeBar.DISC_BACKGORUND_COLOR));
        //circle.getStyleClass().add("gauge");
        return circle;
    }

    private Node createTicks() {
        Path tickMarks = new Path();
        ObservableList<PathElement> pathChildren = tickMarks.getElements();
        for (int i = 0; i < 360; i += TICK_DEGREE) {
            pathChildren.add(new MoveTo(getSkinnable().getSize(), getSkinnable().getSize()));
            pathChildren.add(new LineTo(getSkinnable().getSize() * Math.cos(Math.toRadians(i)) 
                    + getSkinnable().getSize(), getSkinnable().getSize()
                    * Math.sin(Math.toRadians(i)) + getSkinnable().getSize()));
        }
        return tickMarks;
    }

    private Node createSeccion() {
        Group group = new Group();
        try {
        Path tickMarks = new Path();
        ObservableList<PathElement> pathChildren = tickMarks.getElements();
            for (Secciones s : getSkinnable().getSecciones()) {
                
                
                
                float fin = ((float)( s.getFin() -getSkinnable().getMinValue() )/( getSkinnable().getMaxValue()-getSkinnable().getMinValue() )) * 360;
                
               
                
                float start = ((float) (s.getInicio()-getSkinnable().getMinValue() )/( getSkinnable().getMaxValue()-getSkinnable().getMinValue() )) * 360;
                Arc arcBlend = new Arc(getSkinnable().getSize(), 
                        getSkinnable().getSize(),
                        getSkinnable().getSize(),
                        getSkinnable().getSize(), 270 - fin, fin - start);
                arcBlend.setType(ArcType.ROUND);
                arcBlend.setFill(s.getColor());
                
                /*
                pathChildren.add(new MoveTo(getSkinnable().getSize(), getSkinnable().getSize()));
                pathChildren.add(new LineTo(getSkinnable().getSize() * Math.cos(Math.toRadians(90- start)) 
                    + getSkinnable().getSize(), getSkinnable().getSize()
                    * Math.sin(Math.toRadians( 90-start)) + getSkinnable().getSize()));
                pathChildren.add(new MoveTo(getSkinnable().getSize(), getSkinnable().getSize()));
                
                pathChildren.add(new LineTo(getSkinnable().getSize() * Math.cos(Math.toRadians(90- fin)) 
                    + getSkinnable().getSize(), getSkinnable().getSize()
                    * Math.sin(Math.toRadians(90- fin)) + getSkinnable().getSize()));
                        */
                 group.getChildren().add(arcBlend);
            
            }
           // group.getChildren().add(tickMarks);
        } catch (Exception e) {

        }
        return group;
    }

    private Node createGaugeBlend() {
        
        Group group = new Group();
        Number resta = (getSkinnable().getSize()*(GAUGE_MAX_SIZE-0.15));
        
        int value = (getSkinnable().getValue()<getSkinnable().getMinValue()
                ?getSkinnable().getMinValue():getSkinnable().getValue())
                -getSkinnable().getMinValue();
        
        float arcBlendDegrees = ((float) value
                / (getSkinnable().getMaxValue()  -getSkinnable().getMinValue())) * 360;

        Arc arcBlend = new Arc(getSkinnable().getSize(), getSkinnable().getSize(),
                getSkinnable().getSize() -resta.intValue(), getSkinnable().getSize() -resta.intValue(),
                270 - arcBlendDegrees, arcBlendDegrees);

        arcBlend.setType(ArcType.ROUND);
        arcBlend.setFill(getSkinnable().getColores().get(GaugeBar.DISC_VALUE_COLOR));

        resta = (getSkinnable().getSize()*(GAUGE_MAX_SIZE-0.05));
        
         int memorivalue = (getSkinnable().getMemoriValue()<getSkinnable().getMinValue()
                ?getSkinnable().getMinValue():getSkinnable().getMemoriValue())
                -getSkinnable().getMinValue();
        float arcBlendMax = ((float) memorivalue 
                / getSkinnable().getMaxValue()) * 360;
        
        Arc arcBlend2 = new Arc(getSkinnable().getSize(), getSkinnable().getSize(),
                getSkinnable().getSize() -resta.intValue(), getSkinnable().getSize() -resta.intValue(),
                270 - arcBlendMax, arcBlendMax);
        arcBlend2.setType(ArcType.ROUND);
        arcBlend2.setFill(getSkinnable().getColores().get(GaugeBar.DISC_MAX_COLOR));
        
        resta = (getSkinnable().getSize()*GAUGE_MAX_SIZE);
        Circle circleBlend = new Circle(getSkinnable().getSize(), 
                getSkinnable().getSize(), 
                getSkinnable().getSize() - resta.intValue() );
        circleBlend.setFill(getSkinnable().getColores().get(GaugeBar.DISC_TEXT_COLOR));

        group.getChildren().setAll(arcBlend, arcBlend2, circleBlend);
        return group;
    }

    private Node createValue() {
        Group group = new Group();
        
        
        
        Text tx_value = new Text(0,getSkinnable().getSize()*1.15,
                String.valueOf(getSkinnable().getValue()));
        tx_value.setWrappingWidth(getSkinnable().getSize()*2);
        tx_value.setTextAlignment(TextAlignment.CENTER);
       
        Number tx_size = (getSkinnable().getSize() *0.6);
        // System.out.println("TX_SIZE: "+tx_size);
        tx_value.setFont(Font.font("Courier New", tx_size.intValue()));
        tx_value.setFill(getSkinnable().getColores().get(GaugeBar.TEXT_VALUE_COLOR));
        Text tx_unit = new Text(0,
                getSkinnable().getSize() * 1.62,
                getSkinnable().getUnits());
        tx_unit.setWrappingWidth(getSkinnable().getSize()*2);
        Number unit_size = getSkinnable().getSize() * 0.30;
        tx_unit.setTextAlignment(TextAlignment.CENTER);
        // System.out.println("TX_SIZE: "+tx_size);
        tx_unit.setFont(Font.font("Courier New", unit_size.intValue()));
        tx_unit.setFill(getSkinnable().getColores().get(GaugeBar.TEXT_UNIT_COLOR));
        group.getChildren().setAll(tx_unit, tx_value);
        return group;
    }

  

}
