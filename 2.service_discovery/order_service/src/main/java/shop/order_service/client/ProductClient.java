package shop.order_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import shop.order_service.client.dto.ProductResponse;

@Component
public class ProductClient {

    private final RestClient productRestClient;

    public ProductClient(
            @LoadBalanced RestClient.Builder restClientBuilder) {
        this.productRestClient = restClientBuilder
                .baseUrl("http://product-service")
                .build();
    }

    @Retry(name = "productService")
    @CircuitBreaker(name = "productService")
    public ProductResponse getProductById(Long productId) {

        System.out.println("Calling product service.....");

        return productRestClient
                .get()
                .uri("/api/products/{id}", productId)
                .retrieve()
                .body(ProductResponse.class);
    }
}