# Spring Redis-Webflux
This project for implement Reactive Redis Integration, use Spring Data Reactive Redis for reactive data access

### Spring Data Reactive Redis
- Performance Issues
- No support for Reactive CRUD repository
- Some annotations do not work with reactive type
### Spring + Redis
- **Primary Use-Case**
  - Caching Responses
  - To reduce load on the DB
  - To reduce external API calls
- **Why app itself can not cache?**
  - Waste of memory
  - Cache synchronization
### @Cacheable / @CacheEvict / @CachePut
- **@Cacheable**
  - Skip the method execution if key is present
  - Do the method execution only if the key is not present & store the result
- **@CacheEvict**
  - Do the method execution always & evict the corresponding cache
  - Evict happens after method execution. (use before Invocation property otherwise)
- **@CachePut**
  - Do the method execution always & update the corresponding cache
### How to run city-api
1. access external directory
```
cd external
```
2. run this command
```
java -jar city-api.jar 
```
- It uses port `3030` by default.
3. use open-city-api to web browser
```
http://localhost:3030/open-city-api/30005
```
- Response data returned by this API is from the `us.json` file