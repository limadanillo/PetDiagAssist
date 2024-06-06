package br.com.petdiagassist.core.useCases;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ExamVeterinarianService {
    Mono assignExamToVeterinarian(UUID idExam, UUID idVeterinarian);
}
