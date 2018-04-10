package com.wedoogift.mastermind.service.implementation;

import com.wedoogift.mastermind.dao.contract.IMastermindManagement;
import com.wedoogift.mastermind.dao.contract.IMastermindManagementDao;
import com.wedoogift.mastermind.dao.implementation.MastermindManagementDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bob
 */
@Configuration
@EnableAutoConfiguration
public class ServiceClientTestConfiguration {

  @Bean
  public IMastermindManagementDao mastermindManagementDao() {
    return new MastermindManagementDao();
  }

  @Bean
  public IMastermindManagement mastermindManagement() {
    return new MastermindManagement();
  }

  /**
   * @param args
   */
  public static void main(String[] args) {
    SpringApplication.run(ServiceClientTestConfiguration.class, args);
  }
}
