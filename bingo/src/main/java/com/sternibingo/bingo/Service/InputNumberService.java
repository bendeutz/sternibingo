package com.sternibingo.bingo.Service;


import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Service;

import com.sternibingo.bingo.Game.InputNumber;
import com.sternibingo.bingo.Manager.XMLInputNumberWrapper;

@Service
public class InputNumberService {

    private final String XML_PATH = "./numbers.xml";

    public void saveInputNumber(String number) throws JAXBException {
        int numberAsInt = Integer.parseInt(number);
        List<InputNumber> inputNumberList = getInputNumberListFromXml();

        boolean set = false;
        for (InputNumber inputNumber : inputNumberList) {
            if (inputNumber.getValue() == numberAsInt) {
                inputNumber.count();
                set = true;
            }
        }

        //if number is not in the list
        if(!set) {
            InputNumber inputNumber = new InputNumber();
            inputNumber.setValue(numberAsInt);
            inputNumber.setCounter(1);
            inputNumberList.add(inputNumber);
        }
        saveInputNumberListToXml(inputNumberList);
    }

    private void saveInputNumberListToXml(List<InputNumber> inputNumberList) throws JAXBException {
        // create JAXB context
        JAXBContext jaxbContext = JAXBContext.newInstance(XMLInputNumberWrapper.class);

        // create marshaller
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // create unmarshaller
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // initiate wrapper
        XMLInputNumberWrapper xmlWrapper = new XMLInputNumberWrapper();
        xmlWrapper.setInputNumberList(inputNumberList);

        // Write to File
        m.marshal(xmlWrapper, new File(XML_PATH));
    }

    public List<InputNumber> getInputNumberListFromXml() throws JAXBException {
        // create JAXB context
        JAXBContext jaxbContext = JAXBContext.newInstance(XMLInputNumberWrapper.class);

        // create unmarshaller
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // Load boards out of Xml-File, if possible
        File xmlFile = new File(XML_PATH);

        XMLInputNumberWrapper xmlWrapper = null;
        if (xmlFile.exists()) {
            xmlWrapper = (XMLInputNumberWrapper) unmarshaller.unmarshal(xmlFile);
        }
        if(xmlWrapper != null) {
            return xmlWrapper.getInputNumberList();
        }
        return new LinkedList<>();
    }

    public void deleteInputNumberList() {
        File xmlFile = new File(XML_PATH);
        xmlFile.delete();
    }
}
