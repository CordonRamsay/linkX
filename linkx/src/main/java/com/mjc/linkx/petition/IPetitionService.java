package com.mjc.linkx.petition;

import com.mjc.linkx.user.IUser;

import java.util.List;

public interface IPetitionService {



    IPetition insert(PetitionDto dto, Long id);

    IPetition findById(Long id);

    Boolean delete(Long id);

    List<PetitionDto> findAllByNameContains(SearchPetiDto dto);

    Integer countAllByNameContains(SearchPetiDto dto);

    void addagreeQty(Long id, IUser user);



}
