package org.example.backmobile.Web.Dtos.Mapper;

import org.example.backmobile.Datas.Entity.Transaction;
import org.example.backmobile.Web.Dtos.Request.TransactionRequestDto;
import org.example.backmobile.Web.Dtos.Response.TransactionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import prog.dependancy.Web.Mappper.GenericMapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper extends GenericMapper<Transaction, TransactionRequestDto, TransactionResponseDto> {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
}
