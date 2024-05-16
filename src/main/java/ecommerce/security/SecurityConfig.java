package ecommerce.security;


import ecommerce.security.filter.JwtTokenValidator;
import ecommerce.service.impl.UserDetailServiceImpl;
import ecommerce.utils.JwtUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtUtils jwtUtils;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,AuthenticationProvider authenticationProvider) throws Exception {
        return httpSecurity
                .csrf(crsf -> crsf.disable()) //aparece mas en formularios,pero aca no tamos usando //esta bien este
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//la sesion dependera de la expiracion del token.Si vence el token vuelves a iniciar sesion
                .authorizeHttpRequests(http -> {
                    // Configurar los endpoints publicos
                    http.requestMatchers("/api/users/**").permitAll();
                    // Cofnigurar los endpoints privados
                    http.requestMatchers(HttpMethod.POST,"/api/categorias/**").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.POST,"/api/ordenes/**").hasRole("ADMIN");
                        // Configurar el resto de endpoint - NO ESPECIFICADOS
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils,userDetailService), BasicAuthenticationFilter.class)
                .build();
    }

//    Administra la autenticacion
@Bean
public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
}

// Permite conectarnos a la bd usando el passwordencoder y userdetailsService
// El UserDetailsService es el q se conecta a la bd para verificar la autenticacion
@Bean
public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService){
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetailService);
    return provider;
}

//aqui se encarga de encriptar y desencriptar para validar las contraseñas
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
