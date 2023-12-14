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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Builder class for constructing HttpSpec instances.
 */
public final class HttpSpecBuilder {
    private HttpRoute route;
    private List<HttpExchange> exchanges = new ArrayList<>();

    HttpSpecBuilder() {
    }

    /**
     * Sets the HTTP route for the specification.
     *
     * @param route The HTTP route to set.
     * @return The HttpSpecBuilder instance.
     */
    public HttpSpecBuilder route(HttpRoute route) {
        this.route = route;
        return this;
    }

    /**
     * Adds a single HTTP exchange to the specification.
     *
     * @param exchange The HTTP exchange to add.
     * @return The HttpSpecBuilder instance.
     */
    public HttpSpecBuilder exchange(HttpExchange exchange) {
        exchanges.add(exchange);
        return this;
    }

    /**
     * Adds multiple HTTP exchanges to the specification.
     *
     * @param exchange The array of HTTP exchanges to add.
     * @return The HttpSpecBuilder instance.
     */
    public HttpSpecBuilder exchanges(HttpExchange... exchange) {
        exchanges.addAll(Arrays.asList(exchange));
        return this;
    }

    /**
     * Sets a list of HTTP exchanges for the specification.
     *
     * @param exchanges The list of HTTP exchanges.
     * @return The HttpSpecBuilder instance.
     */
    public HttpSpecBuilder exchanges(List<HttpExchange> exchanges) {
        this.exchanges = exchanges;
        return this;
    }

    /**
     * Builds a new HttpSpec instance based on the configured parameters.
     *
     * @return The constructed HttpSpec instance.
     */
    public HttpSpec build() {
        assert route != null;
        return new HttpSpec(route, exchanges);
    }
}
