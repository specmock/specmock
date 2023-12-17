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

import java.util.List;

/**
 * Represents an HTTP specification containing method, path, and associated exchanges.
 */
public final class HttpSpec {
    private final HttpRoute route;
    private final List<HttpExchange> exchanges;

    /**
     * Creates a new instance of HttpSpecBuilder.
     *
     * @return A new HttpSpecBuilder instance.
     */
    public static HttpSpecBuilder builder() {
        return new HttpSpecBuilder();
    }

    /**
     * Creates a new instance of HttpSpecBuilder.
     *
     * @return A new HttpSpecBuilder instance.
     */
    public static SpringWebHttpSpecBuilder springWebBuilder() {
        return new SpringWebHttpSpecBuilder();
    }

    HttpSpec(HttpRoute route, List<HttpExchange> exchanges) {
        this.route = route;
        this.exchanges = exchanges;
    }

    /**
     * Retrieves the HTTP route for this specification.
     *
     * @return The HTTP route.
     */
    public HttpRoute getRoute() {
        return route;
    }

    /**
     * Retrieves the list of HTTP exchanges associated with the specification.
     *
     * @return The list of HTTP exchanges.
     */
    public List<HttpExchange> getExchanges() {
        return exchanges;
    }
}
