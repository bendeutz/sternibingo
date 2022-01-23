package com.sternibingo.bingo.Controller;

import com.sternibingo.bingo.Service.InputNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.bind.JAXBException;

@Controller
public class NumbersController {

    @Autowired
    InputNumberService inputNumberService;

    @RequestMapping(value = "/numbers")

    public String numbers(Model model) throws JAXBException {
        model.addAttribute("inputnumbers", inputNumberService.getInputNumberListFromXml());
        return "numbers";
    }
}
