package br.com.petdiagassist.entrypoint.rest;

import br.com.petdiagassist.core.useCases.ImageStorageServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("api/images")
public class ImageController {

    private final ImageStorageServiceImpl imageStorageServiceImpl;

    public ImageController(ImageStorageServiceImpl imageStorageServiceImpl) {
        this.imageStorageServiceImpl = imageStorageServiceImpl;
    }

    @PostMapping(path = "/upload/exam/{idExam}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<String>> upload(@RequestPart("file") FilePart filePart, @PathVariable UUID idExam) {
        return imageStorageServiceImpl.store(filePart, idExam)
                .map(fileId -> ResponseEntity.ok().body(fileId));
    }

    @GetMapping(value = "/download/exam/{idExam}")
    public Mono<ResponseEntity<byte[]>> loadImage(@PathVariable UUID idExam) {
        return imageStorageServiceImpl.load(idExam)
                .map(bytes -> ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(bytes))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/exam/{idExam}")
    public Mono<Void> delete(@PathVariable UUID idExam) {
        return imageStorageServiceImpl.delete(idExam);
    }
}

