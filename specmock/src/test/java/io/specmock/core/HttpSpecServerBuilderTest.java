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

import org.junit.jupiter.api.Test;

class HttpSpecServerBuilderTest {
    @Test
    void singleRouteServer() {
        final HttpSpecServer server = new HttpSpecServerBuilder()
                .spec(HttpSpec.builder().route(HttpRoute.get("/test")).build())
                .build();
        assertThat(server.getSpecs()).hasSize(1);
    }

    @Test
    void multipleRouteServer() {
        final HttpSpecServer server = new HttpSpecServerBuilder()
                .spec(
                        HttpSpec.builder().route(HttpRoute.get("/test1")).build(),
                        HttpSpec.builder().route(HttpRoute.get("/test2")).build()
                )
                .build();
        assertThat(server.getSpecs()).hasSize(2);
    }
}
