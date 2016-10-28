/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.timmp.indicadoresfx;

import javafx.scene.paint.Paint;

/**
 *
 * @author aperalta
 */
 public class Secciones {
        int inicio,fin;
        Paint color;

        public Secciones(int inicio, int fin, Paint color) {
            this.inicio = inicio;
            this.fin = fin;
            this.color = color;
        }

        public int getInicio() {
            return inicio;
        }

        public void setInicio(int inicio) {
            this.inicio = inicio;
        }

        public int getFin() {
            return fin;
        }

        public void setFin(int fin) {
            this.fin = fin;
        }

        public Paint getColor() {
            return color;
        }

        public void setColor(Paint color) {
            this.color = color;
        }
        
    }