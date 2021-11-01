# traq-ws-bot4j

[![GitHub release](https://img.shields.io/github/release/motoki317/traq-ws-bot4j.svg)](https://GitHub.com/motoki317/traq-ws-bot4j/releases/)

[traQ](https://github.com/traPtitech/traQ) WebSocket bot library for Java

Requires Java 17+

## Example usage

### pom.xml

Make sure to use the latest version shown below.

- traq4j
[![GitHub release](https://img.shields.io/github/release/motoki317/traq4j.svg)](https://GitHub.com/motoki317/traq4j/releases/)
- traq-ws-bot4j
[![GitHub release](https://img.shields.io/github/release/motoki317/traq-ws-bot4j.svg)](https://GitHub.com/motoki317/traq-ws-bot4j/releases/)

```xml
<project>
    <repositories>
        <repository>
            <id>GitHub traq4j</id>
            <url>https://raw.github.com/motoki317/traq4j/mvn-repo/</url>
        </repository>
        <repository>
            <id>GitHub traq-ws-bot4j</id>
            <url>https://raw.github.com/motoki317/traq-ws-bot4j/mvn-repo/</url>
        </repository>
    </repositories>
    <dependencies>
        <dependency>
            <groupId>com.github.motoki317</groupId>
            <artifactId>traq4j</artifactId>
            <version>${traq4j.version}</version>
        </dependency>
        <dependency>
            <groupId>com.github.motoki317</groupId>
            <artifactId>traq-ws-bot4j</artifactId>
            <version>${traq-ws-bot4j.version}</version>
        </dependency>
    </dependencies>
</project>
```

### Code

```java
public class Main {
    public static void main(String[] args){
          Bot bot = new Bot("accessToken", "https://q.trap.jp/api/v3", true);
          bot.onEvent(Event.MESSAGE_CREATED, MessageCreatedEvent.class, (e) -> {
              // do something...
          });
          bot.start();
    }
}
```
