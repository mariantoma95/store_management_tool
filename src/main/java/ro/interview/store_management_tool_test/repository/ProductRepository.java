package ro.interview.store_management_tool_test.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.interview.store_management_tool_test.model.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
