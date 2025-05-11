package ro.interview.store_management_tool.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.interview.store_management_tool.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAll(Pageable pageable);

    Optional<Product> findBySku(String sku);

    boolean existsBySku(String sku);
}
