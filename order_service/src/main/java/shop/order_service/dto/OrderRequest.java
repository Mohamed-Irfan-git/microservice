package shop.order_service.dto;

public record OrderRequest(
        Long productId,
        Integer quantity
) {
}