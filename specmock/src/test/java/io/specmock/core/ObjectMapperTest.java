package io.specmock.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/*
 * Copyright 2023 SpecMock
 * (c) 2023 SpecMock Contributors
 * SPDX-License-Identifier: Apache-2.0
 *
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class ObjectMapperTest {

    private ObjectMapper objectMapper;
    private static final String value = "{\"localDateTimeValue\":\"2020-01-01T00:00:00\"}";

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

        final LocalDateTimeRequest localDateTimeRequest = objectMapper.readValue(
                value, LocalDateTimeRequest.class);

        final LocalDateTime expected = LocalDateTime.of(
                2020, 1, 1, 0, 0, 0);
        assertThat(localDateTimeRequest.getLocalDateTimeValue()).isEqualTo(expected);
    }

    private static class LocalDateTimeRequest {
        private LocalDateTime localDateTimeValue;

        public LocalDateTime getLocalDateTimeValue() {
            return localDateTimeValue;
        }
    }
}
