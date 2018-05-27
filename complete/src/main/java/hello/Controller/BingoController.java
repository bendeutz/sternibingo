package hello.Controller;

import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import hello.Game.Board;
import hello.Service.BingoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.bind.JAXBException;


@Controller
public class BingoController {


    @Autowired
    private BingoService bingoService;

    @RequestMapping(value = "bingo")
    public String bingo(Model model) {
        try {
            model.addAttribute("boards", bingoService.getBoardsFromXml());
        } catch (JAXBException e) {}
        return "bingo";
    }

    @RequestMapping(value = "/submitNumber", method = RequestMethod.GET)
    public String submitNumber(@RequestParam(value = "number") String number, Model model) {
        System.out.println(number);
        model.addAttribute("newNumber", number);
        return "bingo";
    }

    @RequestMapping(value = "/createbingoboard")
    public String createBingoBoard(@RequestParam String bingoNumbers, Model model) throws WrongNumberArgsException, JAXBException {
        bingoService.createBoard(bingoNumbers);
        return "redirect:/bingo";
    }
}
