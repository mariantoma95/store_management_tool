package ro.interview.store_management_tool_test.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String sku) {
        super("Product already exists with sku: " + sku);
    }
}
