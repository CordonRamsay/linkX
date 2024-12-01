package com.mjc.linkx.petition;

import com.mjc.linkx.user.IUser;

import java.util.List;

public interface IPetitionService {



    IPetition insert(PetitionDto dto, Long id);

    IPetition findById(Long id);

    Boolean delete(Long id);

    List<PetitionDto> findAllByNameContains(SearchPetiDto dto);

    Integer countAllByContains(SearchPetiDto dto);


    List<PetitionDto> findAll();

    void updatePlaying(Long id, Boolean playing);

    List<PetitionDto> findTopAgreedPetitions();

    boolean hasUserAgreed(SignatureDto dto);


    void addagreeQty(Long petiId,IUser user);


    List<PetitionDto> findAllByNameContainsOld(SearchPetiDto searchPetiDto);

    Integer countAllByContainsOld(SearchPetiDto searchPetiDto);
}
