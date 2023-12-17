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
package io.specmock.core.example;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Defines various endpoints for Examples API.
 */
public interface ExampleRestApi {
    /**
     * POST request to /example1.
     *
     * @param request The Example1Request object.
     * @return The Example1Response object.
     */
    @PostMapping(value = "/example1", consumes = MediaType.APPLICATION_JSON_VALUE)
    Example1Response example1_POST_requestBody(@RequestBody Example1Request request);

    /**
     * GET request to /example2.
     *
     * @return The Example2Response object.
     */
    @GetMapping(value = "/example2", consumes = MediaType.APPLICATION_JSON_VALUE)
    Example2Response example2_GET_path();

    /**
     * GET request to /example3.
     *
     * @param request The Example3Request object.
     * @return The Example3Response object.
     */
    @PutMapping(value = "/example3", consumes = MediaType.APPLICATION_JSON_VALUE)
    Example3Response example3_PUT_requestBody(@RequestBody Example3Request request);

    /**
     * POST request to /example4_1 or /example4_2.
     *
     * @param request The Example4Request object.
     * @return The Example4Response object.
     */
    @PostMapping(value = { "/example4_1", "/example4_2" }, consumes = MediaType.APPLICATION_JSON_VALUE)
    Example4Response example4_POST_multiplePathParam(@RequestBody Example4Request request);

    /**
     * POST request to /example5.
     *
     * @param request    The Example5Request object.
     * @param queryParam1 The first query parameter.
     * @param queryParam2 The second query parameter (annotated).
     * @return The Example5Response object.
     */
    @PostMapping(path = "/example5", consumes = MediaType.APPLICATION_JSON_VALUE)
    Example5Response example5_POST_multipleQueryParam(@RequestBody Example5Request request,
                                                      @RequestParam String queryParam1,
                                                      @RequestParam("queryParam2_annotate") String queryParam2);

    /**
     * POST request to /example6/{pathParam1}/{pathParam2_annotate}.
     *
     * @param request    The Example6Request object.
     * @param pathParam1 The first path parameter.
     * @param pathParam2 The second path parameter (annotated).
     * @return The Example6Response object.
     */
    @PostMapping(path = "/example6/{pathParam1}/{pathParam2_annotate}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    Example6Response example6_POST_multiplePathParam(@RequestBody Example6Request request,
                                                     @PathVariable String pathParam1,
                                                     @PathVariable("pathParam2_annotate") String pathParam2);

    /**
     * PATCH request to /example7.
     *
     * @return The Example7Response object.
     */
    @PatchMapping(value = "/example7", consumes = MediaType.APPLICATION_JSON_VALUE)
    Example7Response example7_Patch_path(@RequestBody Example7Request request);

    /**
     * DELETE request to /example8.
     *
     * @return The Example8Response object.
     */
    @DeleteMapping(value = "/example8", consumes = MediaType.APPLICATION_JSON_VALUE)
    Example8Response example8_Delete_path();
}

