package tpo.api.ecommerce.config;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class JwtAuthenticationFilterTests {

	@Mock
	private HttpServletRequest request;

	@Mock
	private HttpServletResponse response;

	@Mock
	private FilterChain filterChain;

	@InjectMocks
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	private JwtProperties jwtProperties;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		jwtProperties = new JwtProperties();
		jwtProperties.setSecretKeyString(UUID.randomUUID().toString());

		jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtProperties);
	}

	@Test
	void doFilterInternalValidToken() throws ServletException, IOException {
		String token = createValidToken("2", "test@test.com");

		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);

		jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

		verify(filterChain).doFilter(request, response);
	}

	@Test
	void doFilterInternalInvalidToken() throws ServletException, IOException {
		String invalidToken = "invalidToken";

		when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + invalidToken);

		assertThrows(MalformedJwtException.class,
				() -> jwtAuthenticationFilter.doFilterInternal(request, response, filterChain));
	}

	private String createValidToken(String id, String email) {
		Claims claims = Jwts.claims().setSubject(id);
		claims.put("email", email);

		return Jwts.builder().setClaims(claims)
				.signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKeyString().getBytes()), SignatureAlgorithm.HS256)
				.compact();
	}

}
