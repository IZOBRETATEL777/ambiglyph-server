package com.izobretatel777.ambiglyphserver.mapper;

import com.izobretatel777.ambiglyphserver.dao.entity.Homoglyph;
import com.izobretatel777.ambiglyphserver.dto.HomoglyphResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HomoglyphMapper {
    HomoglyphResponseDto toResponseDto(Homoglyph source);
}
