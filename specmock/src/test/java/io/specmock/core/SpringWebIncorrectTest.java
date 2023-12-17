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
import static org.assertj.core.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import io.specmock.core.example.Example1Request;
import io.specmock.core.example.Example1Response;
import io.specmock.core.example.Example5Request;
import io.specmock.core.example.Example5Response;
import io.specmock.core.example.Example6Request;
import io.specmock.core.example.Example6Response;
import io.specmock.core.example.ExampleApi;

class SpringWebIncorrectTest {
    private static final String RESPONSE_VALUE = "SUCCESS";
    private HttpSpecServer specServer;
    private Example1Request example1Request;
    private Example5Request example5Request;
    private Example6Request example6Request;

    @BeforeEach
    void setUp() {
        example1Request = new Example1Request("EXAMPLE1", 10, 10L, BigDecimal.TEN);
        example5Request = new Example5Request("EXAMPLE5", 10, 10L, BigDecimal.TEN);
        example6Request = new Example6Request("EXAMPLE6", 10, 10L, BigDecimal.TEN);

        final List<HttpSpec> specs = HttpSpec.springWebBuilder()
                                             .springWebBind(ExampleApi.class)
                                             .exchanges(
                                                     HttpExchange.builder()
                                                                 .requestObject(example1Request)
                                                                 .headerMap(example1_HeaderMap()
                                                                                 .toSingleValueMap())
                                                                 .responseObject(
                                                                         new Example6Response(RESPONSE_VALUE)
                                                                 )
                                                                 .responseObject(
                                                                         new Example1Response(RESPONSE_VALUE)
                                                                 )
                                                                 .build(),
                                                     HttpExchange.builder()
                                                                 .requestObject(example5Request)
                                                                 .queryParamMap(example5_QueryMap())
                                                                 .responseObject(
                                                                         new Example5Response(RESPONSE_VALUE)
                                                                 )
                                                                 .build(),
                                                     HttpExchange.builder()
                                                                 .requestObject(example6Request)
                                                                 .pathParamMap(example6_PathMap())
                                                                 .responseObject(
                                                                         new Example6Response(RESPONSE_VALUE)
                                                                 )
                                                                 .build()
                                             )
                                             .build();

        specServer = HttpSpecServer.builder()
                                   .port(18080)
                                   .spec(specs)
                                   .build();
        specServer.start();
    }

    @Test
    void example1_POST_requestBodyWithNotMatchHeader() throws Exception {
        try {
            final RestTemplate restTemplate = new RestTemplate();

            final HttpHeaders headers = new HttpHeaders();
            headers.add("header1", "H1X");
            headers.add("header2", "H2X");

            final HttpEntity<Example1Request> entity = new HttpEntity<>(example1Request, headers);
            restTemplate.postForEntity(
                    "http://localhost:18080/example1",
                    entity, Example1Response.class
            );
            fail("should be failed");
        } catch (HttpClientErrorException e) {
            assertThat(e).isInstanceOf(HttpClientErrorException.class);
        }
    }

    @Test
    void example5_POST_multipleQueryParamWithNotMatchQueryParam() throws Exception {
        try {
            final RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(
                    "http://localhost:18080/example5?queryParam1=QP1X&queryParam2_annotate=QP2X",
                    example5Request, Example5Response.class
            );
            fail("should be failed");
        } catch (HttpClientErrorException e) {
            assertThat(e).isInstanceOf(HttpClientErrorException.class);
        }
    }

    @Test
    void example6_POST_multiplePathParamWithNotMatchPathParam() throws Exception {
        try {
            final RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(
                    "http://localhost:18080/example6/PP1X/PP2X",
                    example6Request, Example6Response.class
            );
            fail("should be failed");
        } catch (HttpClientErrorException e) {
            assertThat(e).isInstanceOf(HttpClientErrorException.class);
        }
    }

    @Test
    void example6_1_POST_NotMatchExchangeRequestToActualRequest() throws Exception {
        try {
            final RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(
                    "http://localhost:18080/example6/PP1/PP2",
                    new Example6Request("EXAMPLEXX", 10, 10L, BigDecimal.TEN), Example6Response.class
            );
            fail("should be failed");
        } catch (HttpClientErrorException e) {
            assertThat(e).isInstanceOf(HttpClientErrorException.class);
        }
    }

    @Test
    void example6_2_POST_JsonProcessingException() throws Exception {
        try {
            final RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(
                    "http://localhost:18080/example6/PP1/PP2",
                    "BROKEN_JSON", Example6Response.class
            );
            fail("should be failed");
        } catch (HttpServerErrorException e) {
            assertThat(e).isInstanceOf(HttpServerErrorException.class);
        }
    }

    @Test
    void example6_3_POST_NotFoundExchanges() throws Exception {
        try {
            final RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForObject(
                    "http://localhost:18080/example6/PP1XX/PP2XX",
                    "BROKEN_JSON", Example6Response.class
            );
            fail("should be failed");
        } catch (HttpClientErrorException e) {
            assertThat(e).isInstanceOf(HttpClientErrorException.class);
        }
    }

    @AfterEach
    void tearDown() {
        specServer.terminate();
    }

    private static Map<String, String> example5_QueryMap() {
        final Map<String, String> map = new HashMap<>();
        map.put("queryParam1", "QP1");
        map.put("queryParam2_annotate", "QP2");
        return map;
    }

    private static Map<String, String> example6_PathMap() {
        final Map<String, String> map = new HashMap<>();
        map.put("pathParam1", "PP1");
        map.put("pathParam2_annotate", "PP2");
        return map;
    }

    private static HttpHeaders example1_HeaderMap() {
        final HttpHeaders headers = new HttpHeaders();
        headers.add("header1", "H1");
        headers.add("header2", "H2");
        return headers;
    }
}
