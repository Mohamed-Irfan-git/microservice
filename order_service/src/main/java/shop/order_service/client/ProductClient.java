package shop.order_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import shop.order_service.client.dto.ProductResponse;
import shop.order_service.exception.ProductServiceUnavailableException;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final RestClient productRestClient;

//    @Retry(name = "productService")
    @CircuitBreaker(
            name = "productService",
            fallbackMethod = "productFallback"
    )
    public ProductResponse getProductById(Long productId) {
        System.out.println("Calling product service.....");

        return productRestClient
                .get()
                .uri("api/products/{id}",productId)
                .retrieve()
                .body(ProductResponse.class);
    }

    private ProductResponse productFallback(Long id, Throwable throwable){
        System.out.println("Fallback executed");
        throw new ProductServiceUnavailableException();
    }
}