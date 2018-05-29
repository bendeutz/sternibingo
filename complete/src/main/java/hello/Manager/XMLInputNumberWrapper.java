package hello.Manager;

import hello.Game.InputNumber;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "InputNumberWrapper")
public class XMLInputNumberWrapper {

    private List<InputNumber> inputNumberList;

    public List<InputNumber> getInputNumberList() {
        return inputNumberList;
    }

    public void setInputNumberList(List<InputNumber> inputNumberList) {
        this.inputNumberList = inputNumberList;
    }
}
