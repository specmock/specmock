# <img src="https://dodn.io/specmock/logo_circle.png" align="right" width="100">SpecMock

SpecMock provides Mock Server for various specs, offering a lightweight, fast, and easy-to-use experience.

[![Maven Central](https://img.shields.io/maven-central/v/io.specmock/specmock.svg?label=Maven%20Central&color=)](https://search.maven.org/search?q=g:%22io.specmock%22%20AND%20a:%22specmock%22)
[![CI](https://github.com/specmock/specmock/actions/workflows/ci.yml/badge.svg)](https://github.com/specmock/specmock/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/specmock/specmock/graph/badge.svg?token=MH9F8QVR41)](https://codecov.io/gh/specmock/specmock)
[![License](https://img.shields.io/badge/License-Apache%202.0-brightgreen.svg)](https://opensource.org/licenses/Apache-2.0)
[![Twitter](https://img.shields.io/twitter/url?style=social&url=https%3A%2F%2Ftwitter.com%2FSpecMock)](https://twitter.com/SpecMock)

## How to use

### SpringWebBind

The following example demonstrates the creation of a mock server based on the `@RestController` or `@FeignClient` with Spring Web Bind annotations.

```java
@RestController OR @FeignClient
public class OR interface ExampleApi {
    @RequestMapping(method = RequestMethod.POST, value = "/example", consumes = MediaType.APPLICATION_JSON_VALUE)
    ExampleResponse examplePost(@RequestBody ExampleRequest request);
}
```

It automatically maps the Path and Method. You simply need to set the data you want to send in the request and the data you expect to receive in response.

```java
@BeforeEach
void setUp() {
    specServer = HttpSpecServer.builder()
                               .port(18080)
                               .spec(HttpSpec.springWebBuilder()
                                    .springWebBind(ExampleApi.class) // Target Spring-Web-Bind class
                                    .exchanges(
                                         HttpExchange.builder()
                                                     .requestObject(new ExampleRequest("REQ")) // Setting request data
                                                     .responseObject(new ExampleResponse("RES")) // Setting response data
                                                     .build()
                                    )
                                    .build()
                               )
                               .build();
    specServer.start();
}

@Test
void example1_POST_requestBody() throws Exception {
    RestTemplate restTemplate = new RestTemplate();
    ExampleResponse response = restTemplate.postForObject("http://localhost:18080/example", new ExampleRequest("REQ"), Example1Response.class);

    // Asserting that the response received from the mock server
    // has the expected string value 'RES'
    assertThat(response.getStringValue()).isEqualTo("RES");
}
```

