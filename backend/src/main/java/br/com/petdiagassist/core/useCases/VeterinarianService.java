package br.com.petdiagassist.core.useCases;

import br.com.petdiagassist.core.dtos.bpswf.request.VeterinaryRequest;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface VeterinarianService {
    Mono createVeterinary(VeterinaryRequest veterinaryRequest);
    Mono getVeterinaryByUserId(UUID id);
    Mono updateVeterinarian(UUID userId, VeterinaryRequest veterinaryRequest);
    Mono deleteVeterinarian(UUID userId);
}
