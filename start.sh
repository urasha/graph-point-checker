cd user-service
mvn clean package 
cd ..

cd point-service
mvn clean package
cd ..

#cd commom-security
#mvn clean install
#cd ..

docker-compose build --no-cache 
docker-compose up