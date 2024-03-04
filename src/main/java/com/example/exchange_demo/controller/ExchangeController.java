package com.example.exchange_demo.controller;

import com.example.exchange_demo.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;

@Controller
public class ExchangeController {

  @Autowired
  private ExchangeRateService exchangeRateService;

  @RequestMapping("/greeting") //對應的網址
  public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
    model.addAttribute("name", name); //讓HTML中的Thymeleaf語法{name}取得屬性值
    return "Index"; //!!請自行修正：剛才建立的HTML檔案名稱
  }

  @GetMapping("/ExchangeIndex")
  public String routeToExchangeIndex(Model model) throws ParseException {
    model.addAttribute("exchangeRates", this.exchangeRateService.getRates());
    return "ExchangeIndex";
  }

}
