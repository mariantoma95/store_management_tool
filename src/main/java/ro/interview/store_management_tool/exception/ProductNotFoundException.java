package ro.interview.store_management_tool.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
    public ProductNotFoundException(String sku) {
        super("Product not found with sku: " + sku);
    }
}
