package br.com.petdiagassist.core.useCases;

import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ImageStorageService {
    Mono<String> store(FilePart filePart, UUID idExam);

    Mono<Void> delete(UUID fileId);

    Mono<byte[]> load(UUID fileId);
    Mono<Void> delete(String imageReferenceId);
}
