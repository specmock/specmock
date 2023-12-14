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

/**
 * Represents an Example Response object.
 */
public class Example3Response {
    private String stringValue;

    /**
     * Empty constructor for ExampleResponse.
     */
    public Example3Response() {
    }

    /**
     * Constructs an ExampleResponse with the specified string value.
     *
     * @param stringValue The string value.
     */
    public Example3Response(String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     * Retrieves the string value.
     *
     * @return The string value.
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * Sets the string value.
     *
     * @param stringValue The string value to set.
     */
    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}
