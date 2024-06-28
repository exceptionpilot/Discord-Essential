<h1 align="center">Discord Essential</h1>

## Introduce:
Discord Essential is a Discord bot that provides some useful features for Discord servers. I am developing this project for fun, so I give no guarantee that it will be updated recently.
This project is build with [Maven](https://maven.org/), so if your haven't installed it yet you can follow this [Guide](https://www.baeldung.com/install-maven-on-windows-linux-mac).
With [Homebrew](https://brew.sh/) you can easily install it by running `brew install maven`.

## Features:

| Feature              | Description                                                                                |
|----------------------|--------------------------------------------------------------------------------------------|
| Welcome Banner Image | Send a welcome banner image to the new member when they join the server.                   |
| Stats Banner Image   | Show how many members are in the server, how many members are online. Tier Level 2 needed. |
| TikTok Live          | Announce Members about started livestreams.                                                |
| Ai Chat              | Chat with the bot.                                                                         |

## Build:
```shell
# Download dependencies
$ mvn dependency:go-offline -B

# Build the project
$ mvn package -DskipTests
```

## Run:
```shell
# Run the project
$ java -jar target/build.jar
```

## Docker:
```shell
# Docker compose (up or down)
$ docker-compose up -d
$ docker-compose down

# Build the image
$ docker build -t discord-essential .

# Run the container
$ docker run -d discord-essential

# Stop the container
$ docker stop <container_id>

# Remove the container
$ docker rm <container_id>
```