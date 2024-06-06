package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.core.entity.ExamVeterinarianEntity;
import br.com.petdiagassist.core.entity.ExamVeterinarianPk;
import br.com.petdiagassist.dataprovider.database.postgresql.ExamRepository;
import br.com.petdiagassist.dataprovider.database.postgresql.ExamVeterinarianRepository;
import br.com.petdiagassist.dataprovider.database.postgresql.VeterinarianRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ExamVeterinarianServiceImpl implements ExamVeterinarianService {

    private final ExamRepository examRepository;
    private final VeterinarianRepository veterinarianRepository;
    private final ExamVeterinarianRepository examVeterinarianRepository;

    public ExamVeterinarianServiceImpl(ExamRepository examRepository, VeterinarianRepository veterinarianRepository, ExamVeterinarianRepository examVeterinarianRepository) {
        this.examRepository = examRepository;
        this.veterinarianRepository = veterinarianRepository;
        this.examVeterinarianRepository = examVeterinarianRepository;
    }


    @Override
    public Mono assignExamToVeterinarian(UUID idExam, UUID idUser) {

        var examMono = examRepository.findById(idExam);
        var veterinarianEntityMono = veterinarianRepository.findByUserId(idUser);

        return Mono.zip(examMono, veterinarianEntityMono)
                .flatMap(tuple -> {
                    var exam = tuple.getT1();
                    var veterinarian = tuple.getT2();
                    var examVeterinarian = new ExamVeterinarianEntity(exam.getId(), veterinarian.getId());
                    return examVeterinarianRepository.save(examVeterinarian);
                }).switchIfEmpty(Mono.error(new RuntimeException("Exam or Veterinarian not found")));
    }
}
