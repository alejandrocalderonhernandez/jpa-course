package com.debuggeandoideas.gadgetplus;

import com.debuggeandoideas.gadgetplus.repositories.BillRepository;
import com.debuggeandoideas.gadgetplus.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GadgetPlusApplication implements CommandLineRunner {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BillRepository billRepository;

	public static void main(String[] args) {
		SpringApplication.run(GadgetPlusApplication.class, args);
	}


//ver cascade
	@Override
	public void run(String... args) throws Exception {
		this.billRepository.findAll().forEach(bill -> System.out.println(bill.toString()));
		this.orderRepository.findAll().forEach(orderEntity -> System.out.println(orderEntity.toString()));
	}
}
