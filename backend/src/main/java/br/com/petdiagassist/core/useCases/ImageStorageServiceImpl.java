package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.core.entity.ExamEntity;
import br.com.petdiagassist.dataprovider.database.postgresql.ExamRepository;
import org.bson.types.ObjectId;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


import java.util.UUID;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ImageStorageServiceImpl implements ImageStorageService {
    private final ReactiveGridFsTemplate gridFsTemplate;
    private final ExamRepository examRepository;

    public ImageStorageServiceImpl(ReactiveGridFsTemplate gridFsTemplate, ExamRepository examRepository) {
        this.gridFsTemplate = gridFsTemplate;
        this.examRepository = examRepository;
    }

    public Mono<String> store(FilePart filePart, UUID idExam) {
        return gridFsTemplate.store(filePart.content(), filePart.filename())
                .map(ObjectId::toHexString)
                .flatMap(fileId -> {
                    return examRepository.findById(idExam)
                            .map(exam -> {
                                exam.setImageReferenceId(fileId);
                                return exam;
                            })
                            .flatMap(examRepository::save)
                            .map(exam -> fileId);
                });
    }


    public Mono<Void> delete(UUID fileId) {
        return examRepository.findById(fileId)
                .map(ExamEntity::getImageReferenceId)
                .flatMap(imageReferenceId -> gridFsTemplate.delete(query(where("_id").is(imageReferenceId)))
                        .then());
    }

    public Mono<byte[]> load(UUID fileId) {
        return examRepository.findById(fileId)
                .map(ExamEntity::getImageReferenceId)
                .flatMap(imageReferenceId -> {
                    return gridFsTemplate.findOne(new Query(Criteria.where("_id").is(imageReferenceId)))
                            .log()
                            .flatMap(gridFsTemplate::getResource)
                            .flatMap(resource -> {
                                return DataBufferUtils.join(resource.getDownloadStream())
                                        .map(dataBuffer -> {
                                            byte[] bytes = new byte[dataBuffer.readableByteCount()];
                                            dataBuffer.read(bytes);
                                            DataBufferUtils.release(dataBuffer); // Libera o DataBuffer para evitar memory leak
                                            return bytes;
                                        });
                            });
                });
    }

    public Mono<Void> delete(String imageReferenceId) {
        return gridFsTemplate.delete(query(where("_id").is(imageReferenceId)))
                .then();
    }
}
