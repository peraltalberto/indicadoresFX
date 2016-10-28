/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmp.indicadoresfx.test;

import es.timmp.indicadoresfx.GaugeBar;
import static es.timmp.indicadoresfx.GaugeBar.DISC_BACKGORUND_COLOR;
import static es.timmp.indicadoresfx.GaugeBar.DISC_MAX_COLOR;
import static es.timmp.indicadoresfx.GaugeBar.DISC_TEXT_COLOR;
import static es.timmp.indicadoresfx.GaugeBar.DISC_VALUE_COLOR;
import static es.timmp.indicadoresfx.GaugeBar.TEXT_TITLE_COLOR;
import static es.timmp.indicadoresfx.GaugeBar.TEXT_UNIT_COLOR;
import static es.timmp.indicadoresfx.GaugeBar.TEXT_VALUE_COLOR;
import es.timmp.indicadoresfx.Secciones;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author aperalta
 */
public class FXMLGraficoController implements Initializable {

    @FXML
    private AnchorPane grafico;

    @FXML
    private Slider max;

    @FXML
    private Slider value;

    GaugeBar gaugeBar = new GaugeBar();
    
    int maximo = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        value.setMax(4500);
        max.setMax(4500);
        
        AnchorPane.setTopAnchor(gaugeBar, 0.0);
        AnchorPane.setLeftAnchor(gaugeBar, 0.0);
        AnchorPane.setRightAnchor(gaugeBar, 0.0);
        AnchorPane.setBottomAnchor(gaugeBar, 0.0);
        grafico.getChildren().add(gaugeBar);
        
        gaugeBar.setUnits("RPM");
        gaugeBar.getColores().put(DISC_BACKGORUND_COLOR, Color.web("#FF5B3F"));
        gaugeBar.getColores().put(TEXT_VALUE_COLOR, Color.BLACK);
        gaugeBar.getColores().put(TEXT_UNIT_COLOR, Color.BLACK);
        gaugeBar.getColores().put(TEXT_TITLE_COLOR, Color.BLACK);
        gaugeBar.getColores().put(DISC_MAX_COLOR, Color.CHARTREUSE);
        gaugeBar.getColores().put(DISC_VALUE_COLOR,Color.web("#03CC10"));
        gaugeBar.getColores().put(DISC_TEXT_COLOR, Color.WHITE);
        
        value.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {

                gaugeBar.setValue(new_val.intValue());

                if(new_val.intValue() < gaugeBar.getSecciones().get(0).getInicio() 
                        ||new_val.intValue() > gaugeBar.getSecciones().get(0).getFin()){
                     gaugeBar.getColores().put(DISC_VALUE_COLOR,Color.web("#7F1300"));
                }else{
                     gaugeBar.getColores().put(DISC_VALUE_COLOR,Color.web("#03CC10"));
                }
            }
        });
        
        max.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                    Number old_val, Number new_val) {

            }
        });
        gaugeBar.setMaxValue(4500);
        gaugeBar.setMinValue(500);
        //gaugeBar.setMemoriValue(8000);
        gaugeBar.getSecciones().addAll(new Secciones(2700, 3300, Color.web("#027F0A")));
        
      
    }

}
