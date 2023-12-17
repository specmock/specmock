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

import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;

/**
 * This class represents an Armeria-based HTTP specification server that manages various HTTP specs.
 */

public final class ArmeriaHttpSpecServer {
    private final Server server;

    /**
     * Constructs an Armeria HTTP specification server based on the given port and HTTP specifications.
     *
     * @param port  The port number on which the server operates.
     * @param specs The list of HTTP specifications to handle.
     */
    public ArmeriaHttpSpecServer(int port, List<HttpSpec> specs) {
        final ServerBuilder serverBuilder = Server.builder();
        serverBuilder.http(port);

        final ArmeriaRouteGenerator routeGenerator = new ArmeriaRouteGenerator();
        for (HttpSpec spec : specs) {
            serverBuilder.service(routeGenerator.generate(spec), new ArmeriaHttpSpecHandler(spec));
        }
        server = serverBuilder.build();
    }

    /**
     * Starts the HTTP server.
     */
    public void start() {
        server.start().join();
    }

    /**
     * Stops the HTTP server.
     */
    public void terminate() {
        server.stop();
    }
}
