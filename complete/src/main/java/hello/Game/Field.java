package hello.Game;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Field")
@XmlType(propOrder = { "id", "x", "y", "value", "hit"})
public class Field {

    private int x,y,value, id;
    private boolean hit;

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public boolean isHit() {
        return hit;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
