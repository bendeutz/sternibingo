package com.sternibingo.bingo.Game;


import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "InputNumber")
@XmlType(propOrder = { "value", "counter"})
public class InputNumber {

    private int value, counter;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public void count() {
        counter++;
    }
}
