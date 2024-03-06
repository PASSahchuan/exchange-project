package com.example.exchange_demo.repository;

import com.example.exchange_demo.model.ExchangeRateDaily;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface ExchangeRateDailyRepository extends JpaRepository<ExchangeRateDaily, Date> {

}
