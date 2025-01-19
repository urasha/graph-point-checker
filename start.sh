cd user-service
mvn clean package
cd ..

cd point-service
mvn clean package
cd ..

docker-compose build --no-cache
docker-compose up