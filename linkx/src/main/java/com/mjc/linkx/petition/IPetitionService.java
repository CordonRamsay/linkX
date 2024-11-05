package com.mjc.linkx.petition;

public interface IPetitionService {

    static void save(PetitionDto petitionDto) {

    }

    IPetition insert(IPetition petition);

    IPetition findById(Long id);

    Boolean delete(Long id);


}
