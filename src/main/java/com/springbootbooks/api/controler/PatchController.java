/* (C)2023 */
package com.springbootbooks.api.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.IOException;

public interface PatchController {
    ObjectMapper objectMapper = JsonMapper.builder().build();

    default <Book, BookRequest> Book patch(Book book, BookRequest patch) throws Exception {
        ObjectReader objectReader = objectMapper.readerForUpdating(book);
        // Patch method is using only for the filed with annotation  @JsonView(Views.Intern.class)
        // in BookRequest
        String patchNode =
                objectMapper.writerWithView(Views.Intern.class).writeValueAsString(patch);
        try {
            return objectReader.readValue(patchNode);
        } catch (IOException e) {
            throw new Exception(e);
        }
    }
}
