package com.example.exchange_demo.controller;

import com.example.exchange_demo.dto.ResponseExchangeRateDTO;
import com.example.exchange_demo.model.ExchangeRateDaily;
import com.example.exchange_demo.service.ExchangeRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Exchange Controller.
 */
@Slf4j
@RestController
public class ExchangeRateController {

  @Autowired
  private ExchangeRateService exchangeRateService;

  /**
   * 查詢政府資料API.
   *
   * @return 目前資料庫中所有匯率資料
   * @throws ParseException 時間格式轉換失敗.
   */
  @GetMapping("/getRates")
  public ResponseEntity<List<ResponseExchangeRateDTO>> getRates() throws ParseException {
    List<ResponseExchangeRateDTO> exchanges = this.exchangeRateService.getRates();
    return new ResponseEntity<>(exchanges, HttpStatus.OK);
  }

  /**
   * 查詢所有匯率資料(Pageable)
   *
   * @param pageNo 頁數
   * @param size 筆數/頁
   * @return 指定頁數之匯率資料
   */
  @GetMapping("getRatesPageable")
  public ResponseEntity<List<ResponseExchangeRateDTO>> getRatePageable(@RequestParam Integer pageNo, @RequestParam Integer size) {
    pageNo += -1;
    List<ResponseExchangeRateDTO> exchanges = this.exchangeRateService.getRatesPageable(pageNo, size);
    return new ResponseEntity<>(exchanges, HttpStatus.OK);
  }

  /**
   * 取得單筆匯率
   *
   * @param date 欲取得匯率之日期
   * @return 單日匯率
   */
  @GetMapping("getRate")
  public ResponseEntity<ResponseExchangeRateDTO> getRate(@RequestParam Date date) {
    ResponseExchangeRateDTO response = this.exchangeRateService.getRate(date);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * 匯率資料表新增API.
   *
   * @param usdToNtd USD to NTD
   * @param rmbToNtd RMB to NTD
   * @param usdToRmb USD to RMB
   * @return Add success message.
   */
  @PostMapping("/add")
  public ResponseEntity<String> addExchangeRate(
      @RequestParam String date,
      @RequestParam Float usdToNtd,
      @RequestParam Float rmbToNtd,
      @RequestParam Float usdToRmb
  ) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

    ExchangeRateDaily exchangeRate = new ExchangeRateDaily(formatter.parse(date), usdToNtd, rmbToNtd, usdToRmb);
    this.exchangeRateService.add(exchangeRate);

    return new ResponseEntity<>("Add Success", HttpStatus.OK);
  }

  /**
   * TODO
   * @return
   */
  @PostMapping("/update")
  public ResponseEntity<String> updateExchangeRate(@RequestBody ExchangeRateDaily exchange) throws Exception {
    this.exchangeRateService.update(exchange);
    return new ResponseEntity<>("Update Success", HttpStatus.OK);
  }

  /**
   * TODO 刪除資料
   *
   * @return
   * @throws ParseException
   */
  @DeleteMapping("/delete")
  public ResponseEntity<String> delete(@RequestParam String date) throws ParseException {

    this.exchangeRateService.delete(date);

    return new ResponseEntity<>("Delete Success", HttpStatus.OK);
  }
}

