/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.model;

import java.io.Serializable;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Luc Cheng
 */
public class ListeCases extends SimpleListProperty<Case> implements Serializable {
    
    public ListeCases(ObservableList<Case> initialValue){
        super(initialValue);
    }
}
