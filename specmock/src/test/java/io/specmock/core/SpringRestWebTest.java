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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.kotlin.KotlinModule;

import com.linecorp.armeria.client.WebClient;

import io.specmock.core.example.Example1Request;
import io.specmock.core.example.Example1Response;
import io.specmock.core.example.Example2Response;
import io.specmock.core.example.Example3Request;
import io.specmock.core.example.Example3Response;
import io.specmock.core.example.Example4Request;
import io.specmock.core.example.Example4Response;
import io.specmock.core.example.Example5Request;
import io.specmock.core.example.Example5Response;
import io.specmock.core.example.Example6Request;
import io.specmock.core.example.Example6Response;
import io.specmock.core.example.Example7Request;
import io.specmock.core.example.Example7Response;
import io.specmock.core.example.Example8Response;
import io.specmock.core.example.ExampleRestApi;

class SpringRestWebTest {
    private static final String RESPONSE_VALUE = "SUCCESS";
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new KotlinModule.Builder().build());
    private final WebClient webClient = WebClient.of("http://localhost:18080");
    private HttpSpecServer specServer;
    private Example1Request example1Request;
    private Example3Request example3Request;
    private Example4Request example4Request;
    private Example5Request example5Request;
    private Example6Request example6Request;
    private Example7Request example7Request;

    @BeforeEach
    void setUp() {
        example1Request = new Example1Request("EXAMPLE1", 10, 10L, BigDecimal.TEN);
        example3Request = new Example3Request("EXAMPLE3", 10, 10L, BigDecimal.TEN);
        example4Request = new Example4Request("EXAMPLE4", 10, 10L, BigDecimal.TEN);
        example5Request = new Example5Request("EXAMPLE5", 10, 10L, BigDecimal.TEN);
        example6Request = new Example6Request("EXAMPLE6", 10, 10L, BigDecimal.TEN);
        example7Request = new Example7Request("EXAMPLE7", 10, 10L, BigDecimal.TEN);

        final List<HttpSpec> specs = HttpSpec.springWebBuilder()
                                             .springWebBind(ExampleRestApi.class)
                                             .exchanges(
                                                     HttpExchange.builder()
                                                                 .requestObject(example1Request)
                                                                 .responseObject(
                                                                         new Example1Response(RESPONSE_VALUE)
                                                                 )
                                                                 .build(),
                                                     HttpExchange.builder()
                                                                 .responseObject(
                                                                         new Example2Response(RESPONSE_VALUE)
                                                                 )
                                                                 .build(),
                                                     HttpExchange.builder()
                                                                 .requestObject(example3Request)
                                                                 .responseObject(
                                                                         new Example3Response(RESPONSE_VALUE)
                                                                 )
                                                                 .build(),
                                                     HttpExchange.builder()
                                                                 .requestObject(example4Request)
                                                                 .responseObject(
                                                                         new Example4Response(RESPONSE_VALUE)
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
                                                                 .build(),
                                                     HttpExchange.builder()
                                                                 .requestObject(example7Request)
                                                                 .responseObject(
                                                                         new Example7Response(RESPONSE_VALUE)
                                                                 )
                                                                 .build(),
                                                     HttpExchange.builder()
                                                                 .responseObject(
                                                                         new Example8Response(RESPONSE_VALUE)
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
    void example1_POST_requestBody() throws Exception {
        final RestTemplate restTemplate = new RestTemplate();
        final Example1Response response = restTemplate.postForObject(
                "http://localhost:" + specServer.getPort() + "/example1",
                example1Request, Example1Response.class
        );
        Objects.requireNonNull(response);
        assertThat(response.getStringValue()).isEqualTo(RESPONSE_VALUE);
    }

    @Test
    void example2_GET_path() throws Exception {
        final RestTemplate restTemplate = new RestTemplate();
        final Example2Response response = restTemplate.getForObject(
                "http://localhost:18080/example2",
                Example2Response.class
        );
        Objects.requireNonNull(response);
        assertThat(response.getStringValue()).isEqualTo(RESPONSE_VALUE);
    }

    @Test
    void example3_PUT_requestBody() throws Exception {
        final Example3Response response = mapper.readValue(
                webClient.put("/example3", mapper.writeValueAsString(example3Request))
                         .aggregate().join().contentUtf8(),
                Example3Response.class
        );
        Objects.requireNonNull(response);
        assertThat(response.getStringValue()).isEqualTo(RESPONSE_VALUE);
    }

    @Test
    void example4_POST_multiplePathParam() throws Exception {
        final RestTemplate restTemplate = new RestTemplate();
        final Example4Response response1 = restTemplate.postForObject(
                "http://localhost:18080/example4_1",
                example4Request, Example4Response.class
        );
        Objects.requireNonNull(response1);
        assertThat(response1.getStringValue()).isEqualTo(response1.getStringValue());

        final Example4Response response2 = restTemplate.postForObject(
                "http://localhost:18080/example4_2",
                example4Request, Example4Response.class
        );
        Objects.requireNonNull(response2);
        assertThat(response2.getStringValue()).isEqualTo(response2.getStringValue());
    }

    @Test
    void example5_POST_multipleQueryParam() throws Exception {
        final RestTemplate restTemplate = new RestTemplate();
        final Example5Response response = restTemplate.postForObject(
                "http://localhost:18080/example5?queryParam1=QP1&queryParam2_annotate=QP2",
                example5Request, Example5Response.class
        );
        Objects.requireNonNull(response);
        assertThat(response.getStringValue()).isEqualTo(RESPONSE_VALUE);
    }

    @Test
    void example6_POST_multiplePathParam() throws Exception {
        final RestTemplate restTemplate = new RestTemplate();
        final Example6Response response = restTemplate.postForObject(
                "http://localhost:18080/example6/PP1/PP2",
                example6Request, Example6Response.class
        );
        Objects.requireNonNull(response);
        assertThat(response.getStringValue()).isEqualTo(RESPONSE_VALUE);
    }

    @Test
    void example7_Patch_requestBody() throws Exception {
        final Example3Response response = mapper.readValue(
                webClient.patch("/example7", mapper.writeValueAsString(example7Request))
                         .aggregate().join().contentUtf8(),
                Example3Response.class
        );
        Objects.requireNonNull(response);
        assertThat(response.getStringValue()).isEqualTo(RESPONSE_VALUE);
    }

    @Test
    void example8_Delete_path() throws Exception {
        final Example3Response response = mapper.readValue(
                webClient.delete("/example8").aggregate().join().contentUtf8(),
                Example3Response.class
        );
        Objects.requireNonNull(response);
        assertThat(response.getStringValue()).isEqualTo(RESPONSE_VALUE);
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
}
