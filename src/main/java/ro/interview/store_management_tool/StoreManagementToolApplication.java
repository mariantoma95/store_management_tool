package ro.interview.store_management_tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StoreManagementToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreManagementToolApplication.class, args);
	}
}
