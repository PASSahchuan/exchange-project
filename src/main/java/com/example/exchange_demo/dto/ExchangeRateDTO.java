package com.example.exchange_demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExchangeRateDTO {

  @JsonProperty("Date")
  private String date;

  @JsonProperty("USD/NTD")
  private Float usdToNtd;

  @JsonProperty("RMB/NTD")
  private Float rmbToNtd;

  @JsonProperty("EUR/USD")
  private Float eurToUsd;

  @JsonProperty("USD/JPY")
  private Float usdToJpy;

  @JsonProperty("GBP/USD")
  private Float gbpToUsd;

  @JsonProperty("AUD/USD")
  private Float audToUsd;

  @JsonProperty("USD/HKD")
  private Float usdToHkd;

  @JsonProperty("USD/RMB")
  private Float usdToRmb;

  @JsonProperty("USD/ZAR")
  private Float usdToZar;

  @JsonProperty("NZD/USD")
  private Float nzdToUsd;

}
