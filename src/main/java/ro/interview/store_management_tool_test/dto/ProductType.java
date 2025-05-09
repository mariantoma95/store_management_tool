package ro.interview.store_management_tool_test.dto;

public enum ProductType {
    ELECTRONICS("electronics"),
    FASHION("fashion"),
    HOME("home"),
    OTHER("other");

    private String productType;

    ProductType(String productType) {
        this.productType = productType;
    }
}
