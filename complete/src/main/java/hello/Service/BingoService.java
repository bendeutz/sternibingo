package hello.Service;

import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import hello.Game.Board;
import hello.Game.Field;
import hello.Manager.XMLWrapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class BingoService {

    private static final String XML_PATH = "./boards.xml";

    public Board createBoard(String input) throws WrongNumberArgsException, JAXBException {
        int[] inputAsIntArray = getIntArray(input);

        List<Field> fieldList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Field field = new Field();
                field.setX(i);
                field.setY(j);
                field.setValue(inputAsIntArray[5 * i + j]);
                fieldList.add(field);
            }
        }
        Board board = new Board();
        board.setFieldList(fieldList);
        board.setId(UUID.randomUUID());
        checkIfBoardIsCorrect();
        saveBoardToXML(board);
        return board;
    }

    private int[] getIntArray(String input) throws WrongNumberArgsException {
        int[] result = new int[25];
        if (input.length() != 25) {
            throw new WrongNumberArgsException("We need exactly 25 numbers");
        } else {
            for (int i = 0; i < 25; i++) {
                result[i] = (int) input.charAt(i) - 48;
            }
        }
        return result;
    }

    //TODO: Doubled numbers etc. maybe not necessary
    private void checkIfBoardIsCorrect() {

    }

    private void saveBoardToXML(Board board) throws JAXBException {
        // create JAXB context
        JAXBContext jaxbContext = JAXBContext.newInstance(XMLWrapper.class);

        // create marshaller
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        // create unmarshaller
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // Load boards out of Xml-File, if possible
        File xmlFile = new File(XML_PATH);

        // initiate wrapper
        XMLWrapper xmlWrapper;

        if (xmlFile.exists()) {
            xmlWrapper = (XMLWrapper) unmarshaller.unmarshal(xmlFile);
        } else {
            xmlWrapper = new XMLWrapper();
            xmlWrapper.setBoardList(new LinkedList<>());
        }

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
        JAXBContext jaxbContext = JAXBContext.newInstance(XMLWrapper.class);

        // create unmarshaller
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        // Load boards out of Xml-File, if possible
        File xmlFile = new File(XML_PATH);

        XMLWrapper xmlWrapper = null;
        if (xmlFile.exists()) {
            xmlWrapper = (XMLWrapper) unmarshaller.unmarshal(xmlFile);
        }
        if(xmlWrapper != null) {
            return xmlWrapper.getBoardList();
        }
        return null;
    }


}

