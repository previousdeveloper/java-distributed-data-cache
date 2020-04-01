# Spring Boot Distributed Cache

Spring Boot Distributed Cache project implements (property-based) configuring of multiple backend providers.
Default implementation is couchbase.

It works best with [Spring Boot](https://github.com/spring-projects/spring-boot), implementing [auto-com.trendyol.spring.boot.cache.core.configuration](https://github.com/previousdeveloper/spring-boot-distributed-data-cache) mechanism.

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

        @com.trendyol.distributed.data.cache.core.provider.ResponseCache
    	@GetMapping("/welcome")
    	public String retrieveWelcomeMessage() {
    		return welcomeMessage;
    	}
    
    	@Autowired
    	private BasicConfiguration com.trendyol.spring.boot.cache.core.configuration;
    
    	@com.trendyol.distributed.data.cache.core.provider.ResponseCache
    	@RequestMapping("/dynamic-com.trendyol.spring.boot.cache.core.configuration")
    	public Map<String, Object> dynamicConfiguration() {
    		Map<String, Object> map = new HashMap<>();
    		map.put("message", com.trendyol.spring.boot.cache.core.configuration.getMessage());
    		map.put("number", com.trendyol.spring.boot.cache.core.configuration.getNumber());
    		map.put("key", com.trendyol.spring.boot.cache.core.configuration.isValue());
    		return map;
    	}
    }
```
@ResponseCache annonation automatically caches your response to backend com.trendyol.distributed.data.cache.core.provider.

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

- Redis Provider
```
distributed:
  cache:
    enabled: true
    platform: redis
    redis:
      url: "localhost"
      username: "username"
      password: "password"
```
