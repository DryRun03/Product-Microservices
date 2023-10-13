package io.pankaj.inventoryservice;

import io.pankaj.inventoryservice.model.Inventory;
import io.pankaj.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
//		return args-> {
//			Inventory inventory1 = new Inventory();
//			inventory1.setSkuCode("iphone-13");
//			inventory1.setQuantity(100);
//
//			Inventory inventory2 = new Inventory();
//			inventory2.setSkuCode("iphone-13-red");
//			inventory2.setQuantity(0);
//
//			inventoryRepository.save(inventory1);
//			inventoryRepository.save(inventory2);
//
//		};
//	}
}
