package hello.Game;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@XmlRootElement(name = "Board")
public class Board {


    private List<Field> fieldList;
    private int id;

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement(name = "field")
    public List<Field> getFieldList() {
        return fieldList;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return fieldList.toString();
    }

    public List<Field> getRow(int rowNumber) {
        int startIndex = rowNumber * 5;
        int endIndex = startIndex + 5;
        List<Field> row = new LinkedList<>();
        for (int i = startIndex; i < endIndex; i++) {
            row.add(fieldList.get(i));
        }
        return row;
    }

    public List<List<Field>> getRows() {
        List<List<Field>> result = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            List<Field> row = getRow(i);
            result.add(row);
        }
        return result;
    }

    public Field getField(int id) {
        return fieldList.get(id);
    }
}
