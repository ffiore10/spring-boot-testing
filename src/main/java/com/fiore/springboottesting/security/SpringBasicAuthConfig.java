//package com.fiore.springboottesting.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
////@Configuration
//public class SpringBasicAuthConfig{
//
//    @Autowired
//    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user")
//                .password(passwordEncoder().encode("password"))
//                .authorities("ROLE_USER")
//                .and()
//                .withUser("admin")
//                .password(passwordEncoder().encode("admin"))
//                .authorities("ROLE_ADMIN");
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(auth ->
//                {
//                    try {
//                        auth.anyRequest().authenticated().and().httpBasic().authenticationEntryPoint(authenticationEntryPoint);
//                    } catch (Exception e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .httpBasic(Customizer.withDefaults());
////                .antMatchers("/securityNone")
////                .permitAll()
////                .anyRequest()
////                .authenticated()
////                .and()
////                .httpBasic()
////                .authenticationEntryPoint(authenticationEntryPoint);
////        http.addFilterAfter(new BasicAuthenticationFilter(userDetailsService()), BasicAuthenticationFilter.class);
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password(new BCryptPasswordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password(new BCryptPasswordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }
//}