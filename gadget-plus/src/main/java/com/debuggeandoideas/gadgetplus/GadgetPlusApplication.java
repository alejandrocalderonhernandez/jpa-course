package com.debuggeandoideas.gadgetplus;

import com.debuggeandoideas.gadgetplus.entities.CategoryEntity;
import com.debuggeandoideas.gadgetplus.entities.ProductEntity;
import com.debuggeandoideas.gadgetplus.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@SpringBootApplication
public class GadgetPlusApplication implements CommandLineRunner {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private ProductCatalogRepository productCatalogRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private RejectProductRepository rejectProductRepository;


	public static void main(String[] args) {
		SpringApplication.run(GadgetPlusApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		//this.orderRepository.findAll().forEach(p -> System.out.println(p + p.getProducts().toString()));


		/*final var HOME = this.categoryRepository.findById(1L).orElseThrow();
		final var OFFICE = this.categoryRepository.findById(2L).orElseThrow();

		this.productCatalogRepository.findAll().forEach(product -> {

			if (product.getDescription().contains("home")) {
				product.addCategory(HOME);
			}

			if (product.getDescription().contains("office")) {
				product.addCategory(OFFICE);
			}

			this.productCatalogRepository.save(product);
		});

		var random = new Random();


		var productsCatalog = new LinkedList<>(this.productCatalogRepository.findAll());

		IntStream.range(0, productsCatalog.size()).forEach(i -> {
			var idOrderRandom = random.nextLong(16) + 1;
			var orderRandom = this.orderRepository.findById(idOrderRandom).orElseThrow();

			var product = ProductEntity.builder()
					.quantity(BigInteger.valueOf(random.nextInt(5) + 1))
					.catalog(productsCatalog.poll())
					.build();

			orderRandom.addProduct(product);
			product.setOrder(orderRandom);

			this.orderRepository.save(orderRandom);
		});*/

	}
}
