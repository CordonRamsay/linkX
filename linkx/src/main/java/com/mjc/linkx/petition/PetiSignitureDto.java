package com.mjc.linkx.petition;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PetiSignitureDto extends PetitionDto implements IPetiSigniture{
    private Long id;

    private Long petiId;

    private String sigDt;


}
