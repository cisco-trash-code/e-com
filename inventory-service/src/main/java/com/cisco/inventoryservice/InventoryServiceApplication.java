package com.cisco.inventoryservice;

import com.cisco.inventoryservice.model.Inventory;
import com.cisco.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inv1 = new Inventory();
			inv1.setSkuCode("PS5");
			inv1.setQuantity(2);

			Inventory inv2 = new Inventory();
			inv2.setSkuCode("Nintendo");
			inv2.setQuantity(0);

			inventoryRepository.save(inv1);
			inventoryRepository.save(inv2);
		};
	}

}
