# WebFlux threading example

Test application that shows WebFlux threading model and capabilities.

# What it does

The app contains of a three very basic http endpoints that shows different threading approach to request processing by
WebFlux.

* GET '/karma' - generates a simple Flux on event loop's thread (default behavior)
* GET '/delayedKarma' - delays elements emitting to emulate downstream call (processing on `parallel` thread pool)
* GET '/fairKarma' - sub-streams elements on `boundedElastic` pool

# How to play with it

### Build and Run

```shell
./gradlew clean build

docker build -t local/webflux-threading .

docker run -d --rm --name webflux-threading -p 8081:8080 --cpus=1 local/webflux-threading
```

### Call endpoint

```shell
# Generate karma in event loop's thread
curl localhost:8081/karma

# Dedicate processing to 'parallel' thread pool
curl localhost:8081/delayedKarma

# Process substream on boundedElastic pool
curl localhost:8081/fairKarma
```

### Check logs

```shell
docker logs webflux-threading 
```