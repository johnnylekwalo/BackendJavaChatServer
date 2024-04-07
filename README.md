# BackendJavaChatServer

## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.assessment.javachatserver` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

## Setting up the application
Our application will have the following configuration which can be set using Spring Initializr :

Java version : 17

Type : Maven Project

Dependencies : Web , Security

##### Creating a Chat Model

Our chat model is the message payload which will be exchanged between the client side and server side of the application.

```
@Entity
@Table
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "chat_user_id")
    private ChatUser chatUser;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private String content;
    private LocalDateTime timestamp;
    
    ...
```


##### Creating our Chat Controller
RESTful endpoints for message sending, and message retrieval.
Our controller will be responsible for handling all message methods present in our chat application
```

@Controller
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // Endpoint to send a message
    @PostMapping("/messages")
    public ResponseEntity<?> sendMessage(@RequestBody ChatMessage chatMessage) {
        ChatMessage chatMessageSent = chatService.createMessages(chatMessage);
        return ResponseEntity.ok(chatMessageSent);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessage>> getMessages() {
        List<ChatMessage> messages = chatService.getMessages();
        return ResponseEntity.ok(messages);
    }

    // Endpoint to get chat history for a room
    @GetMapping("/chat/{chatRoomName}/history")
    public ResponseEntity<List<ChatMessage>> getChatHistory(@PathVariable String chatRoomName) {
        List<ChatMessage> history = chatService.getChatHistory(chatRoomName);
        return ResponseEntity.ok(history);
    }

    // Additional endpoints as needed
    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Void> getChatHistory(@PathVariable Long id) {
        chatService.getDeleteChatMessage(id);
        return ResponseEntity.noContent().build();
    }
}

```

##### Mandatory: User authentication with basic username/password login. The credentials can be hardcoded

```


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                .build();
    }

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

}

```


##### Mandatory: Creation of a single chat room upon server startup. No need to create multiple rooms.
DataInitializer implements CommandLineRunner
```

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ChatUserRepository chatUserRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Override
    public void run(String... args) throws Exception {
        // Check if data is already initialized to prevent duplication
        if (chatUserRepository.count() == 0) {
            // Create and save dummy users
            ChatUser chatUser1 = new ChatUser();
            chatUser1.setUsername("test");
            chatUser1.setPassword("test");
            chatUserRepository.save(chatUser1);

        } else {
            System.out.println("Database already initialized");
        }


            // Create and save dummy users
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setName("test");
            chatRoomRepository.save(chatRoom);


        if (chatMessageRepository.count() == 0) {
            // Create and save dummy users
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setContent("test");
            chatMessageRepository.save(chatMessage);
        } else {
            System.out.println("Chat Message Database already initialized");
        }
    }
}

```

##### Mandatory: Persistent storage of chat messages in a Database
H2 data embedded 
```

spring :
  application:
    name: javachatserver
  datasource:
    url: jdbc:h2:mem:testdb
    sdriverClassName: org.h2.Driver
    username: sa
    password: password
    enabled: true
  
  # H2 Configuration
  jpa:
      database-platform: org.hibernate.dialect.H2Dialect
  
  # H2 Console
  h2.console.path: /h2-console
  
  # Spring Security Configuration
  security:
      user:
        name: user
        password: password

```


##### Mandatory: Unit testing

```
    @BeforeEach
    public void initTest() {
        chatMessage = createEntity(em);
    }

    @Test
    @Transactional
    void createChatMessage() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ChatMessage
        var returnedChatMessage = om.readValue(
                restChatMessageMockMvc
                        .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(chatMessage)))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString(),
                ChatUser.class
        );
    }

```

##### Mandatory: Testing via Postman

PostMan Test integrated into github:

- [Postman Tests](https://github.com/johnnylekwalo/BackendJavaChatServer/blob/main/Postman%20Collections/Assessment.json)


##### Bonus: CI/CD skeleton 
Continuous deployment workflow using GitHub Actions and Docker for a Spring Boot application with scalability considerations

Dockerize Your Spring Boot Application
```
# Use the official OpenJDK base image for Java 17
FROM adoptopenjdk/openjdk17:alpine-jre

# Set the working directory in the container
WORKDIR /app

# Copy the packaged Spring Boot application JAR file into the container
COPY target/BackendJavaChatServer.jar /app/BackendJavaChatServer.jar

# Specify the command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "BackendJavaChatServer.jar"]

```

Dockerize Your Spring Boot Application
```
# Use the official OpenJDK base image for Java 17
FROM adoptopenjdk/openjdk17:alpine-jre

# Set the working directory in the container
WORKDIR /app

# Copy the packaged Spring Boot application JAR file into the container
COPY target/BackendJavaChatServer.jar /app/BackendJavaChatServer.jar

# Specify the command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "BackendJavaChatServer.jar"]

```
GitHub Actions Workflow

```
name: CI/CD Pipeline

on:
push:
branches:
- main

jobs:
build:
runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v2

      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'

      - name: Build with Maven
        run: mvn clean package -DskipTests

      - name: Run integration tests
        run: mvn verify -Pintegration-tests

      - name: Run code quality checks
        run: mvn clean verify sonar:sonar -Dsonar.projectKey=my-project -Dsonar.organization=my-org -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=$SONAR_TOKEN
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}

      - name: Build Docker Image
        run: docker build -t your-docker-username/your-application-name:${{ github.sha }} .

      - name: Login to Docker Hub
        uses: docker/login-action@v1
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}

      - name: Push Docker Image to Docker Hub
        run: docker push your-docker-username/your-application-name:${{ github.sha }}

test:
runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v2

      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'

      - name: Test with Maven
        run: mvn test

      - name: Run integration tests
        run: mvn verify -Pintegration-tests

      - name: Run code quality checks
        run: mvn clean verify sonar:sonar -Dsonar.projectKey=my-project -Dsonar.organization=my-org -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=$SONAR_TOKEN
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}

      - name: Build Docker Image
        run: docker build -t your-docker-username/your-application-name:${{ github.sha }} .

      - name: Login to Docker Hub
        uses: docker/login-action@v1
        with:
          username: ${{ secrets.DOCKER_USERNAME }}
          password: ${{ secrets.DOCKER_PASSWORD }}

      - name: Push Docker Image to Docker Hub
        run: docker push your-docker-username/your-application-name:${{ github.sha }}
```

Dockerize Your Spring Boot Application
```
# Use the official OpenJDK base image for Java 17
FROM adoptopenjdk/openjdk17:alpine-jre

# Set the working directory in the container
WORKDIR /app

# Copy the packaged Spring Boot application JAR file into the container
COPY target/BackendJavaChatServer.jar /app/BackendJavaChatServer.jar

# Specify the command to run your Spring Boot application when the container starts
CMD ["java", "-jar", "BackendJavaChatServer.jar"]

```

Deploy to Production Environment

For deploying to a production environment, you would typically use an orchestrator like Kubernetes or Docker Swarm. You can define a deployment configuration file (e.g., Kubernetes Deployment YAML) to deploy your Docker container at scale.