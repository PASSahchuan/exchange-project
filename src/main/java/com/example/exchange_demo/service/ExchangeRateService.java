package com.example.exchange_demo.service;

import com.example.exchange_demo.advice.exception.CustomException;
import com.example.exchange_demo.dto.ExchangeRateDTO;
import com.example.exchange_demo.dto.ResponseExchangeRateDTO;
import com.example.exchange_demo.model.ExchangeRateDaily;
import com.example.exchange_demo.repository.ExchangeRateDailyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ExchangeRateService {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private ExchangeRateDailyRepository exchangeRateDailyRepository;

  public void add(ExchangeRateDaily exchangeRate) {
    this.exchangeRateDailyRepository.save(exchangeRate);
  }

  public void delete(String date) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    try {
      this.exchangeRateDailyRepository.deleteById(formatter.parse(date));
    } catch (ParseException e) {
      throw new CustomException(e.getMessage());
    }
  }

  public List<ResponseExchangeRateDTO> getRates() throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat responseFormatter = new SimpleDateFormat("yyyy-MM-dd");

    ResponseEntity<ExchangeRateDTO[]> resEntity = restTemplate.getForEntity(
        "https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates",
        ExchangeRateDTO[].class
    );

    ExchangeRateDTO[] exchangeRates = resEntity.getBody();

    for (ExchangeRateDTO exchangeRate: exchangeRates) {

      try {
        ExchangeRateDaily tmpExchange = new ExchangeRateDaily(formatter.parse(exchangeRate.getDate()), exchangeRate.getUsdToNtd(), exchangeRate.getRmbToNtd(), exchangeRate.getUsdToRmb());
        this.exchangeRateDailyRepository.save(tmpExchange);
      } catch (ParseException e) {
        throw new CustomException(e.getMessage());
      }

    }

    List<ExchangeRateDaily> exchangeData = this.exchangeRateDailyRepository.findAll();
    List<ResponseExchangeRateDTO> response = exchangeData.stream().map(
        data ->
            new ResponseExchangeRateDTO(
                responseFormatter.format(data.getDate()),
                data.getUsdToNtd(),
                data.getRmdToNtd(),
                data.getUsdToRmb()
            )
    ).toList();

    // 查詢所有資料
    return response;

  }

  public List<ResponseExchangeRateDTO> getRatesPageable(Integer pageNo, Integer size) {
    SimpleDateFormat responseFormatter = new SimpleDateFormat("yyyy-MM-dd");

    if (pageNo <= 0) {
      throw new CustomException("頁數輸入錯誤，應大於0");
    }

    PageRequest pageRequest = PageRequest.of(pageNo, size);
    Page<ExchangeRateDaily> exchangeDatas = this.exchangeRateDailyRepository.findAll(pageRequest);

    List<ResponseExchangeRateDTO> response = exchangeDatas.stream().map(
        data ->
            new ResponseExchangeRateDTO(
                responseFormatter.format(data.getDate()),
                data.getUsdToNtd(),
                data.getRmdToNtd(),
                data.getUsdToRmb()
            )
    ).toList();

    return response;

  }

  public ResponseExchangeRateDTO getRate(Date date) {
    SimpleDateFormat responseFormatter = new SimpleDateFormat("yyyy-MM-dd");
    Optional<ExchangeRateDaily> exchange = this.exchangeRateDailyRepository.findById(date);

//    exchange.orElseThrow();
    log.info(exchange.toString());

    ExchangeRateDaily data = exchange.get();
    ResponseExchangeRateDTO response = new ResponseExchangeRateDTO(responseFormatter.format(data.getDate()), data.getUsdToNtd(), data.getRmdToNtd(), data.getUsdToRmb());

    return response;
  }

  public void update(ExchangeRateDaily exchangeRateDaily) throws Exception {
    log.info(exchangeRateDaily.toString());
    if (this.exchangeRateDailyRepository.findById(exchangeRateDaily.getDate()).isEmpty()) {
      throw new CustomException("欲修改之資料不存在");
    }

    this.exchangeRateDailyRepository.save(exchangeRateDaily);
  }

}
