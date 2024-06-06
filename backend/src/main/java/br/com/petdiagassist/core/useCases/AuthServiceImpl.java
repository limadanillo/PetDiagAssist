package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.config.security.JwtUtil;
import br.com.petdiagassist.core.document.AuthDocument;
import br.com.petdiagassist.core.dtos.bpswf.request.UserRequestLogin;
import br.com.petdiagassist.core.dtos.bpswf.response.AuthenticationResponse;
import br.com.petdiagassist.dataprovider.adapter.generic.GenericConverter;
import br.com.petdiagassist.dataprovider.database.mongodb.BlackListRepository;
import br.com.petdiagassist.error.exception.BadRequestErrorException;
import br.com.petdiagassist.error.exception.ForbiddenErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Locale;

/**
 * Class Auth Service
 *
 * @author Danillo Lima
 * @since 16/02/2022
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final MessageSource messageSource;

    private final UserService userService;

    private final GenericConverter genericConverter;

    private final BlackListRepository blackListRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder encoder;

    public Mono<AuthenticationResponse> generateToken(UserRequestLogin user) {
        return userService.findByEmail(user.getEmail())
                .map(userDb -> {
                    var token = jwtUtil.generateToken(userDb);
                    var expiration = jwtUtil.getClaimsFromToken(token).getExpiration();
                    var auth = new AuthenticationResponse(token, userDb.getName(), userDb.getEmail(), expiration.getTime());

                    if (!encoder.matches(user.getPassword(), userDb.getPassword())) {
                        throw new RuntimeException(new ForbiddenErrorException(
                                messageSource.getMessage("message.forbidden.error.incorrect.credentials", null, Locale.getDefault())));
                    }
                    return auth;
                });
    }


    public Mono saveTokenInBlacklist(AuthDocument auth) {
        return blackListRepository.existsByToken(auth.getToken()).flatMap(exists -> {
            if (!exists)
                return blackListRepository.save(auth);
            else return Mono.error(new BadRequestErrorException(messageSource.getMessage("message.bad.request.error.find.token", null, Locale.getDefault())));
        });
    }

}
