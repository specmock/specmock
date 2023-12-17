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
package io.specmock.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import io.specmock.core.example.Example1Request;
import io.specmock.core.example.Example1Response;
import io.specmock.core.example.Example3Response;

class HttpExchangeTest {
    private final Example1Request exampleRequest = new Example1Request("REQ", 1, 1L, BigDecimal.ONE);
    private final Example1Response exampleResponse = new Example1Response("RES");

    @Test
    void matchRequest() {
        final HttpExchange exchange = HttpExchange.builder()
                                                  .requestObject(exampleRequest)
                                                  .responseStatus(HttpStatus.OK)
                                                  .responseObject(exampleResponse)
                                                  .build();
        // Correct Class
        assertThat(exchange.isMatchRequest(Example1Request.class, Example1Response.class)).isTrue();
        assertThat(exchange.isNotMatchRequest(Example1Request.class, Example1Response.class)).isFalse();

        // Incorrect Class
        assertThat(exchange.isMatchRequest(Example1Request.class, Example3Response.class)).isFalse();
        assertThat(exchange.isNotMatchRequest(Example1Request.class, Example3Response.class)).isTrue();
    }

    @Test
    void isNotMatchPathParam() {
        final Map<String, String> exchangePathParamMap = new HashMap<>();
        exchangePathParamMap.put("pathParam1", "CORRECT");
        final HttpExchange exchange = HttpExchange.builder()
                                                  .pathParamMap(exchangePathParamMap)
                                                  .build();

        final Map<String, String> requestPathParamMap = new HashMap<>();
        requestPathParamMap.put("pathParam1", "INCORRECT");
        assertThat(exchange.isNotMatchPathParam(requestPathParamMap)).isTrue();
    }

    @Test
    void isNotMatchQueryParam() {
        final Map<String, String> exchangeQueryParamMap = new HashMap<>();
        exchangeQueryParamMap.put("queryParam1", "CORRECT");
        final HttpExchange exchange = HttpExchange.builder()
                                                  .queryParamMap(exchangeQueryParamMap)
                                                  .build();

        final Map<String, String> requestQueryParamMap = new HashMap<>();
        requestQueryParamMap.put("queryParam1", "INCORRECT");
        assertThat(exchange.isNotMatchQueryParam(requestQueryParamMap)).isTrue();
    }

    @Test
    void isNotMatchHeader() {
        final Map<String, String> exchangeHeaderMap = new HashMap<>();
        exchangeHeaderMap.put("header1", "CORRECT");
        final HttpExchange exchange = HttpExchange.builder()
                                                  .headerMap(exchangeHeaderMap)
                                                  .build();

        final Map<String, String> correctRequestHeaderMap = new HashMap<>();
        correctRequestHeaderMap.put("header1", "CORRECT");
        assertThat(exchange.isNotMatchHeader(correctRequestHeaderMap)).isFalse();

        final Map<String, String> incorrectRequestHeaderMap = new HashMap<>();
        incorrectRequestHeaderMap.put("header1", "INCORRECT");
        assertThat(exchange.isNotMatchHeader(incorrectRequestHeaderMap)).isTrue();
    }

    @Test
    void isNotMatchHeaderWhenEmptyHeaderMap() {
        final HttpExchange exchange = HttpExchange.builder()
                                                  .build();

        final Map<String, String> correctRequestHeaderMap = new HashMap<>();
        correctRequestHeaderMap.put("header1", "CORRECT");
        assertThat(exchange.isNotMatchHeader(correctRequestHeaderMap)).isFalse();

        final Map<String, String> incorrectRequestHeaderMap = new HashMap<>();
        incorrectRequestHeaderMap.put("header1", "INCORRECT");
        assertThat(exchange.isNotMatchHeader(incorrectRequestHeaderMap)).isFalse();
    }
}
