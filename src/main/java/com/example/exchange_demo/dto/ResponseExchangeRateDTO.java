package com.example.exchange_demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseExchangeRateDTO {

  private String date;

  private Float usdToNtd;

  private Float rmbToNtd;

  private Float usdToRmb;

}
