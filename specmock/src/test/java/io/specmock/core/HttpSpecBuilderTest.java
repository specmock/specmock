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

import java.util.Collections;

import org.junit.jupiter.api.Test;

class HttpSpecBuilderTest {
    @Test
    void emptyRoute() {
        try {
            final HttpSpec spec = HttpSpec.builder()
                                    .exchange(HttpExchange.builder().build())
                                    .build();
            fail("should be failed " + spec);
        } catch (AssertionError e) {
            assertThat(e).isInstanceOf(AssertionError.class);
        }
    }

    @Test
    void singleExchange() {
        final HttpSpec spec = HttpSpec.builder()
                                      .route(HttpRoute.get("/test"))
                                      .exchange(HttpExchange.builder().build())
                                      .build();
        assertThat(spec.getExchanges()).hasSize(1);
    }

    @Test
    void multipleExchanges() {
        final HttpSpec spec = HttpSpec.builder()
                                      .route(HttpRoute.get("/test"))
                                      .exchanges(
                                              HttpExchange.builder().build(),
                                              HttpExchange.builder().build()
                                      )
                                      .build();
        assertThat(spec.getExchanges()).hasSize(2);
    }

    @Test
    void listExchanges() {
        final HttpSpec spec = HttpSpec.builder()
                                      .route(HttpRoute.get("/test"))
                                      .exchanges(
                                              Collections.singletonList(HttpExchange.builder().build())
                                      )
                                      .build();
        assertThat(spec.getExchanges()).hasSize(1);
    }
}
