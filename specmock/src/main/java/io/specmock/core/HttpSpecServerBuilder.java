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
 * Builder class for constructing HttpSpecServer instances.
 */
public final class HttpSpecServerBuilder {
    private int port = 18080;
    private final List<HttpSpec> specs = new ArrayList<>();

    HttpSpecServerBuilder() {
    }

    /**
     * Sets the port number for the server.
     *
     * @param port The port number to set.
     * @return The HttpSpecServerBuilder instance.
     */
    public HttpSpecServerBuilder port(int port) {
        this.port = port;
        return this;
    }

    /**
     * Adds a single HTTP specification to the server.
     *
     * @param spec The HTTP specification to add.
     * @return The HttpSpecServerBuilder instance.
     */
    public HttpSpecServerBuilder spec(HttpSpec spec) {
        specs.add(spec);
        return this;
    }

    /**
     * Adds multiple HTTP specifications to the server.
     *
     * @param specs The array of HTTP specifications to add.
     * @return The HttpSpecServerBuilder instance.
     */
    public HttpSpecServerBuilder spec(HttpSpec... specs) {
        this.specs.addAll(Arrays.asList(specs));
        return this;
    }

    /**
     * Adds a list of HTTP specifications to the server.
     *
     * @param specs The list of HTTP specifications to add.
     * @return The HttpSpecServerBuilder instance.
     */
    public HttpSpecServerBuilder spec(List<HttpSpec> specs) {
        this.specs.addAll(specs);
        return this;
    }

    /**
     * Builds a new HttpSpecServer instance based on the configured parameters.
     *
     * @return The constructed HttpSpecServer instance.
     */
    public HttpSpecServer build() {
        return new HttpSpecServer(port, specs);
    }
}
