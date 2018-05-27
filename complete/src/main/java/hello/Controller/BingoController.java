package hello.Controller;

import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import hello.Game.Board;
import hello.Game.Field;
import hello.Service.BingoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Map;


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

    @RequestMapping(value = "/createbingoboard")
    public String createBingoBoard(@RequestParam List<String> field) throws WrongNumberArgsException, JAXBException {
        bingoService.createBoard(field);
        return "redirect:/bingo";
    }

    @RequestMapping(value = "/checknumber")
    public String checkNumber(@RequestParam String number, RedirectAttributes redirectAttributes) throws JAXBException {
        Map<Integer, Field> fields = bingoService.getFieldsWithNumber(number);
        bingoService.saveFields(fields);
        redirectAttributes.addFlashAttribute("fieldsWithNumber", fields);
        return "redirect:/bingo";
    }
}
