    package br.com.wswork.bestcommerceapi.config;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.http.HttpMethod;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
    import org.springframework.security.config.http.SessionCreationPolicy;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.web.SecurityFilterChain;
    import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfigurations {

        @Autowired
        SecurityFilter securityFilter;
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
            return httpSecurity
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                                                                 .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                                                 .requestMatchers(AUTH_WHITELIST).permitAll()
                                                                 .requestMatchers(ENDPOINTS_WITH_USER_ROLE).hasAnyRole("USER", "ADMIN")
                                                                 .requestMatchers(ENDPOINTS_WITH_ADMIN_ROLE).hasRole("ADMIN")
                    )
                    .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

        public static String[] ENDPOINTS_WITH_USER_ROLE = {
                "/api/v1/best-commerce/address/**",
                "/api/v1/best-commerce/category/all",
                "/api/v1/best-commerce/category/{id}",
                "/api/v1/best-commerce/customer/all",
                "/api/v1/best-commerce/customer/{id}",
                "/api/v1/best-commerce/customertype/all",
                "/api/v1/best-commerce/customertype/{id}",
                "/api/v1/best-commerce/product/name/{name}",
                "/api/v1/best-commerce/product/{id}",
                "/api/v1/best-commerce/product/all/categories",
                "/api/v1/best-commerce/product/all",
                "/api/v1/best-commerce/sale/all",
                "/api/v1/best-commerce/sale/{id}",
                "/api/v1/best-commerce/sale/all/customer/{id}",
                "/api/v1/best-commerce/sale/all/store/all",
                "/api/v1/best-commerce/sale/all/store/{id}",
                "/api/v1/best-commerce/sale/all/store/name/{name}"
        };

        public static String[] ENDPOINTS_WITH_ADMIN_ROLE = {
                "/api/v1/best-commerce/category/add",
                "/api/v1/best-commerce/category/modify/{id}",
                "/api/v1/best-commerce/category/delete/{id}",
                "/api/v1/best-commerce/customer/add",
                "/api/v1/best-commerce/customer/modify",
                "/api/v1/best-commerce/customer/delete/{id}",
                "/api/v1/best-commerce/customertype/add",
                "/api/v1/best-commerce/customertype/modify/{id}",
                "/api/v1/best-commerce/customertype/delete/{id}",
                "/api/v1/best-commerce/product/add",
                "/api/v1/best-commerce/product/name/{name}",
                "/api/v1/best-commerce/product/modify/{id}",
                "/api/v1/best-commerce/product/delete/{id}",
                "/api/v1/best-commerce/sale/modify/{id}",
                "/api/v1/best-commerce/sale/delete/{id}",
                "/api/v1/best-commerce/store/add",
                "/api/v1/best-commerce/store/modify/{id}",
                "/api/v1/best-commerce/store/name/{name}",
                "/api/v1/best-commerce/store/delete/{id}"
        };

        public static String[] AUTH_WHITELIST = {
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/swagger-ui/index.html"
        };

        @Bean
        public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }

       @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

    }