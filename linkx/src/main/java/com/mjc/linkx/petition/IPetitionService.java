package com.mjc.linkx.petition;

public interface IPetitionService {

    IPetition insert(IPetition petition);

    IPetition findById(Long id);

    Boolean delete(Long id);


}
