/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package p2048.model;

import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Luc Cheng
 */
public class SimpleIntegerPropertySerializable extends SimpleIntegerProperty implements Serializable {
    public SimpleIntegerPropertySerializable(int n){
        super(n);
    }
}
