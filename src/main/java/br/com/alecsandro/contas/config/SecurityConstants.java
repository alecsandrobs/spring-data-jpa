package br.com.alecsandro.contas.config;

public class SecurityConstants {

    // Authorizarion Bearer 9tufkr4yerwjkmrgepthweof
    static final String SECRET = "SegredoContasObra";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER = "Authorization";
    static final String SIGN_UP_URL = "/usuarios/sign-up";
    static final long EXPIRATION_TIME = 86400000000L;
//    static final long EXPIRATION_TIME = (Long) TimeUnit.MICROSECONDS.convert(1, TimeUnit.DAYS);

}
