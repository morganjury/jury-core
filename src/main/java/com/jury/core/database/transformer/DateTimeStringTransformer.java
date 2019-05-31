package com.jury.core.database.transformer;

import com.jury.core.exception.TransformerException;

import java.time.LocalDateTime;

public interface DateTimeStringTransformer extends Transformer<LocalDateTime, String> {

    String consume(LocalDateTime date) throws TransformerException;

    LocalDateTime produce(String object) throws TransformerException;

}
