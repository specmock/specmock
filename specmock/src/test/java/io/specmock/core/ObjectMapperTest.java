package io.specmock.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ObjectMapperTest {

    private ObjectMapper objectMapper;
    private String value = "{\"localDateTimeValue\":\"2020-01-01T00:00:00\"}";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void unsupportedLocalDateTime() {
        assertThatThrownBy(() -> {
            objectMapper.readValue(value, LocalDateTimeRequest.class);
        }).isInstanceOf(InvalidDefinitionException.class);
    }

    @Test
    void supportLocalDateTime() throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());

        final LocalDateTimeRequest localDateTimeRequest = objectMapper.readValue(value, LocalDateTimeRequest.class);

        final LocalDateTime expected = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        assertThat(localDateTimeRequest.getLocalDateTimeValue()).isEqualTo(expected);
    }


    private static class LocalDateTimeRequest {
        private LocalDateTime localDateTimeValue;

        public LocalDateTime getLocalDateTimeValue() {
            return localDateTimeValue;
        }

    }
}
