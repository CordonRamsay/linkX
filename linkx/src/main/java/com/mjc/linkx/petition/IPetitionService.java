package com.mjc.linkx.petition;

import com.mjc.linkx.user.IUser;

import java.util.List;

public interface IPetitionService {



    IPetition insert(PetitionDto dto, Long id);

    IPetition findById(Long id);

    Boolean delete(Long id);

    List<PetitionDto> findAllByNameContains(SearchPetiDto dto);

    Integer countAllByContains(SearchPetiDto dto);




    List<PetitionDto> findTopAgreedPetitions();

    boolean hasUserAgreed(Long petiId, Long userId);

    void addSignature(SignatureDto signature);

    void addagreeQty(Long petiId);


}
