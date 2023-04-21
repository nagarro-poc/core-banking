package org.bfsi.gateway.filter;

import org.bfsi.gateway.validator.RouterValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    public AuthenticationFilter(){
        super(Config.class);
    }

    @Autowired
    private RouterValidator routerValidator;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (routerValidator.isSecured.test(request)) {
                if (this.isAuthHeaderMissing(request)) {
                    return this.onError(exchange, "Authorization header is missing",
                            HttpStatus.UNAUTHORIZED);
                }
                String authHeader = request.getHeaders().getOrEmpty("Authorization").get(0);

                if (this.isBearerMissingInAuthHeader(authHeader)) {
                    return this.onError(exchange, "Authorization header is incorrect",
                            HttpStatus.UNAUTHORIZED);
                }

                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", authHeader);

                HttpEntity entityRequest = new HttpEntity<>("request", headers);

                try {
                    restTemplate.exchange("http://identity-service/api/v1/auth/validate",
                            HttpMethod.GET, entityRequest, String.class);

                } catch (HttpClientErrorException e) {
                    return this.onError(exchange, e.getMessage(),
                            HttpStatus.valueOf(e.getStatusCode().value()));
                }
            }
            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMsg,
                               HttpStatus httpStatus) {

        byte[] bytes = errorMsg.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(httpStatus);
        return response.writeWith(Flux.just(buffer));
    }

    private boolean isAuthHeaderMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private boolean isBearerMissingInAuthHeader(String authHeader) {
        return (null == authHeader || !authHeader.startsWith("Bearer "));
    }

    public static class Config {

    }
}
