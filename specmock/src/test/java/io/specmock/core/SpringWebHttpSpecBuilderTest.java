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

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.specmock.core.example.ExampleEmptyApi;

class SpringWebHttpSpecBuilderTest {
    @Test
    void emptySpringWebBind() {
        final List<HttpSpec> specs = HttpSpec.springWebBuilder()
                                             .springWebBind(ExampleEmptyApi.class)
                                             .build();
        assertThat(specs).hasSize(0);
    }

    @Test
    void singleExchange() {
        final List<HttpSpec> specs = HttpSpec.springWebBuilder()
                                             .exchange(HttpExchange.builder().build())
                                             .build();
        assertThat(specs).hasSize(0);
    }

    @Test
    void listExchanges() {
        final List<HttpSpec> specs = HttpSpec.springWebBuilder()
                                             .exchanges(
                                                     Collections.singletonList(HttpExchange.builder().build())
                                             )
                                             .build();
        assertThat(specs).hasSize(0);
    }
}
