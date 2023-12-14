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

class ArmeriaRouteGeneratorTest {
    @Test
    void armeriaRouteGenerate() {
        final ArmeriaRouteGenerator generator = new ArmeriaRouteGenerator();
        assertThat(
                generator.generate(
                        HttpSpec.builder().route(HttpRoute.get("/test")).build()
                ).methods()
        ).hasSize(1);

        assertThat(
                generator.generate(
                        HttpSpec.builder().route(HttpRoute.post("/test")).build()
                ).methods()
        ).hasSize(1);

        assertThat(
                generator.generate(
                        HttpSpec.builder().route(HttpRoute.put("/test")).build()
                ).methods()
        ).hasSize(1);

        assertThat(
                generator.generate(
                        HttpSpec.builder().route(HttpRoute.patch("/test")).build()
                ).methods()
        ).hasSize(1);

        assertThat(
                generator.generate(
                        HttpSpec.builder().route(HttpRoute.delete("/test")).build()
                ).methods()
        ).hasSize(1);
    }
}
