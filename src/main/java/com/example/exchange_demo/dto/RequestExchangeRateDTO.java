package com.example.exchange_demo.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RequestExchangeRateDTO {

  private Date date;

  private Float usdToNtd;

  private Float rmbToNtd;

  private Float usdToRmb;

}
