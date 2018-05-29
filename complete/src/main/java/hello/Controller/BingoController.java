package hello.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.Game.Field;
import hello.Service.BingoService;
import hello.Service.InputNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Map;


@Controller
public class BingoController {


    @Autowired
    private BingoService bingoService;

    @Autowired
    InputNumberService inputNumberService;

    @RequestMapping(value = "bingo")
    public String bingo(Model model) {
        try {
            model.addAttribute("boards", bingoService.getBoardsFromXml());
        } catch (JAXBException e) {}
        return "bingo";
    }

    @RequestMapping(value = "/createbingoboard")
    public String createBingoBoard(@RequestParam List<String> field) throws JAXBException {
        bingoService.createBoard(field);
        return "redirect:/bingo";
    }

    @RequestMapping(value = "/checknumber")
    public String checkNumber(@RequestParam String number, RedirectAttributes redirectAttributes) throws JAXBException {
        Map<Integer, Field> fields = bingoService.getFieldsWithNumber(number);
        bingoService.saveFields(fields);
        inputNumberService.saveInputNumber(number);
        redirectAttributes.addFlashAttribute("fieldsWithNumber", fields);
        return "redirect:/bingo";
    }

    @RequestMapping(value = "/deletenumberlist")
    public String deleteNumberList() throws JAXBException {
        inputNumberService.deleteInputNumberList();
        bingoService.clearBoards();
        return "redirect:/bingo";
    }


    //how to get a map to a json file
    @RequestMapping(value = "/getfieldswithnewnumber", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String getFieldsWithNewNumber(@RequestParam String number) throws JAXBException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(bingoService.getFieldsWithNumber(number));
        return json;
    }
}
