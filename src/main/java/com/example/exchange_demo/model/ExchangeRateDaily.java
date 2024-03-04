package com.example.exchange_demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "EXCHANGE_RATE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRateDaily {

  @Id
  @Temporal(TemporalType.DATE)
  @Column(name = "DATE")
  private Date date;

  @Column(name = "USD_TO_NTD")
  private Float usdToNtd;

  @Column(name = "RMB_TO_NTD")
  private Float rmdToNtd;

  @Column(name = "USD_TO_RMB")
  private Float usdToRmb;

}
