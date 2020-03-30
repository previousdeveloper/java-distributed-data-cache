# Spring Boot Distributed Cache

Spring Boot Distributed Cache project implements (property-based) configuring of multiple backend providers.
Default implementation is couchbase.

It works best with [Spring Boot](https://github.com/spring-projects/spring-boot), implementing [auto-configuration](https://github.com/previousdeveloper/spring-boot-distributed-data-cache) mechanism.

```xml
<dependency>
    <groupId>com.trendyol</groupId>
    <artifactId>spring-boot-distributed-data-cache</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
### Getting Started

```java
    @RestController
    public class WelcomeResource {
    
    	@Value("${welcome.message}")
    	private String welcomeMessage;

        @ResponseCache
    	@GetMapping("/welcome")
    	public String retrieveWelcomeMessage() {
    		return welcomeMessage;
    	}
    
    	@Autowired
    	private BasicConfiguration configuration;
    
    	@ResponseCache
    	@RequestMapping("/dynamic-configuration")
    	public Map<String, Object> dynamicConfiguration() {
    		Map<String, Object> map = new HashMap<>();
    		map.put("message", configuration.getMessage());
    		map.put("number", configuration.getNumber());
    		map.put("key", configuration.isValue());
    		return map;
    	}
    }
```
ResponseCache annonation automatically caches your response to backend provider.

```java
//Cache if response header hash this key
@ResponseCache(responseHeaderName="bla")


//Cache if enabled
@ResponseCache(enabled=true)

//Cache expire time
@ResponseCache(expireInMinutes=10)
```

### Configuration Properties
### /src/main/resources/application.yaml

- Couchase Provider
```
distributed:
  cache:
    enabled: true
    platform: couchbase
    couchbase:
      bootstrapHosts: "localhost"
      username: "bucketusername"
      password: "bucketpassword"
      bucket: "buckname"
```

- Couchase Provider
```
distributed:
  cache:
    enabled: true
    platform: redis
    redis:
      url: "localhost"
      username: "bucketusername"
      password: "bucketpassword"
```