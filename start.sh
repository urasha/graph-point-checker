cd backend
mvn clean package 
cd .. 
docker-compose build --no-cache 
docker-compose up