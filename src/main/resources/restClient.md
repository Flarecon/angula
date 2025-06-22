Here's the full **Spring Boot `RestClient` guide** covering:

✅ Basic CRUD
✅ Headers & Auth
✅ DTO mapping
✅ File upload
✅ Error handling
✅ Injecting as a Spring Bean

---

## 🔧 1. **RestClient Setup**

### ✅ Manual (for quick use):

```java
RestClient restClient = RestClient.builder()
    .baseUrl("https://api.example.com")
    .defaultHeader("Content-Type", "application/json")
    .build();
```

---

### ✅ As a Spring Bean:

```java
@Configuration
public class RestClientConfig {
    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.baseUrl("https://api.example.com").build();
    }
}
```

Then inject:

```java
@Autowired
RestClient restClient;
```

---

## 📗 2. **CRUD Examples**

### 🟢 GET (with path variables)

```java
UserDTO user = restClient.get()
    .uri("/users/{id}", 101)
    .retrieve()
    .body(UserDTO.class);
```

---

### 🟡 POST (with body)

```java
UserDTO newUser = new UserDTO("Alok", 25);
UserDTO createdUser = restClient.post()
    .uri("/users")
    .body(newUser)
    .retrieve()
    .body(UserDTO.class);
```

---

### 🟠 PUT (update)

```java
UserDTO updatedUser = new UserDTO("Alok", 26);
restClient.put()
    .uri("/users/{id}", 101)
    .body(updatedUser)
    .retrieve()
    .toBodilessEntity();
```

---

### 🔴 DELETE

```java
restClient.delete()
    .uri("/users/{id}", 101)
    .retrieve()
    .toBodilessEntity();
```

---

## 🔐 3. **Headers & Auth**

### Static Token

```java
String token = "Bearer abc123";

restClient.get()
    .uri("/secure")
    .header("Authorization", token)
    .retrieve()
    .body(String.class);
```

### Dynamic Header Using `defaultHeader` or on each request.

---

## 📦 4. **Custom DTO Mapping**

```java
List<UserDTO> users = restClient.get()
    .uri("/users")
    .retrieve()
    .body(new ParameterizedTypeReference<List<UserDTO>>() {});
```

---

## 🧾 5. **Error Handling**

```java
String result = restClient.get()
    .uri("/users/{id}", 999)
    .retrieve(response -> {
        if (response.getStatusCode().is4xxClientError()) {
            throw new IllegalArgumentException("User not found");
        }
        return response.body(String.class);
    });
```

---

## 📁 6. **File Upload (Multipart)**

Spring’s `RestClient` supports `multipart/form-data` via `MultiValueMap`:

```java
MultiValueMap<String, Object> data = new LinkedMultiValueMap<>();
data.add("file", new FileSystemResource(new File("/path/to/file.txt")));
data.add("description", "sample file");

restClient.post()
    .uri("/upload")
    .contentType(MediaType.MULTIPART_FORM_DATA)
    .body(data)
    .retrieve()
    .toBodilessEntity();
```

---

## 🧠 Tips

* `.retrieve().body(Class.class)` → returns full deserialized body
* `.toBodilessEntity()` → use when response body is not needed
* Use `.header()` for custom headers per call

---

Want this wrapped into a reusable service class or interface-based client?
