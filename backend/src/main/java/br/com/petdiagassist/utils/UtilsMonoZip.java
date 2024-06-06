package br.com.petdiagassist.utils;

import reactor.core.publisher.Mono;

import java.util.Optional;

public class UtilsMonoZip {

    public static <T> Mono<Optional<T>> optional(Mono<T> in) {
        return in.map(Optional::of).switchIfEmpty(Mono.just(Optional.empty()));
    }

}
