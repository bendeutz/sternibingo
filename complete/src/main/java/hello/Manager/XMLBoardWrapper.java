package hello.Manager;

import hello.Game.Board;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "BoardWrapper")
public class XMLBoardWrapper {

    private List<Board> boardList;

    @XmlElement
    public List<Board> getBoardList() {
        return boardList;
    }

    public void setBoardList(List<Board> boardList) {
        this.boardList = boardList;
    }
}
