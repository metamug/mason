curl -d "releaseDate=2002-04-20&name=X-Men&rating=4.0" -X POST http://localhost:7000/rest/v1.0/movie

curl -d "user=admin&pass=admin" -X POST http://localhost:7000/rest/v1.0/jwt/token

curl -v -F user=admin -F pass=admin http://localhost:7000/rest/v1.0/jwt/token

