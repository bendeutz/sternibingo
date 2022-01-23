package com.sternibingo.bingo.Service;


import com.sternibingo.bingo.Game.Board;
import com.sternibingo.bingo.Game.Field;
import com.sternibingo.bingo.Manager.XMLBoardWrapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class BingoService {

    private static final String XML_PATH = "./boards.xml";

    public Board createBoard(List<String> input) throws JAXBException {

        List<Field> fieldList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Field field = new Field();
                field.setX(i);
                field.setY(j);
                field.setValue(Integer.parseInt(input.get(5 * i + j)));
                field.setId(5 * i + j);
                fieldList.add(field);
            }
        }
        Board board = new Board();
        board.setFieldList(fieldList);
        checkIfBoardIsCorrect();
        saveBoardToXML(board);
        return board;
    }

    //TODO: Doubled numbers etc. maybe not necessary
    private void checkIfBoardIsCorrect() {

    }

    private void saveBoardToXML(Board board) throws JAXBException {
        // create JAXB context
        JAXBContext jaxbContext = JAXBContext.newInstance(XMLBoardWrapper.class);

        // create marshaller
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // create unmarshaller
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // Load boards out of Xml-File, if possible
        File xmlFile = new File(XML_PATH);

        // initiate wrapper
        XMLBoardWrapper xmlWrapper;

        if (xmlFile.exists()) {
            xmlWrapper = (XMLBoardWrapper) unmarshaller.unmarshal(xmlFile);
        } else {
            xmlWrapper = new XMLBoardWrapper();
            xmlWrapper.setBoardList(new LinkedList<>());
        }

        board.setId(xmlWrapper.getBoardList().size());
        xmlWrapper.getBoardList().add(board);

        // Write to File
        m.marshal(xmlWrapper, new File(XML_PATH));
    }

    /**
     * Load boards from file and return the list of boards out of the wrapper
     * @return the board list or null if no wrapper was found
     * @throws JAXBException
     */
    public List<Board> getBoardsFromXml() throws JAXBException {
        // create JAXB context
        JAXBContext jaxbContext = JAXBContext.newInstance(XMLBoardWrapper.class);

        // create unmarshaller
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // Load boards out of Xml-File, if possible
        File xmlFile = new File(XML_PATH);

        XMLBoardWrapper xmlWrapper = null;
        if (xmlFile.exists()) {
            xmlWrapper = (XMLBoardWrapper) unmarshaller.unmarshal(xmlFile);
        }
        if(xmlWrapper != null) {
            return xmlWrapper.getBoardList();
        }
        return null;
    }



    public Map<Integer, Field> getFieldsWithNumber(String number) throws JAXBException {
        int numberAsInt = Integer.parseInt(number);
        Map<Integer, Field> result = new LinkedHashMap<>();
        List<Board> boardList = getBoardsFromXml();
        for (Board board : boardList) {
            for (Field field : board.getFieldList()) {
                if (field.getValue() == numberAsInt) {
                    result.put(board.getId(), field);
                }
            }
        }
        return result;
    }

    public void saveFields(Map<Integer, Field> fields) throws JAXBException {
        List<Board> boardList = getBoardsFromXml();
        fields.forEach((boardID, field) -> boardList.get(boardID).getField(field.getId()).setHit(true));
        deleteBoards();
        for (Board board : boardList) {
            saveBoardToXML(board);
        }
    }

    public void deleteBoards() throws JAXBException {
        File xmlFile = new File(XML_PATH);
        xmlFile.delete();
    }

    public void clearBoards() throws JAXBException {
        List<Board> boardList = getBoardsFromXml();
        if (boardList != null) {
            boardList.forEach(b -> b.getFieldList().forEach(f -> f.setHit(false)));
            deleteBoards();
            for (Board board : boardList) {
                saveBoardToXML(board);
            }
        }
    }
}

