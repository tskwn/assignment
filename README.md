JAVA VERSION: 21

빌드
```
gradlew build
```

실행
```
java -jar build/libs/assignment-0.0.1-SNAPSHOT.jar
```

테스트 실행
- 테스트 데이터 초기화
```
curl --location 'http://localhost:8080/api/v1/product/init'
```

- 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
```
curl --location 'http://localhost:8080/api/v1/product/category-lowest-price'
```

- 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을
  조회하는 API
```
curl --location 'http://localhost:8080/api/v1/product/brand-category-lowest-price'
```

- 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
```
curl --location 'http://localhost:8080/api/v1/product/category-lowest-highest-price?category=%EC%83%81%EC%9D%98'
```  

- CRUD API 실행
```
## BRAND CREATE
curl --location 'http://localhost:8080/api/v1/brand/create' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Nike"
}'

## BRAND UPDATE
curl --location --request PUT 'http://localhost:8080/api/v1/brand/1/update' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Adidas"
}'

## BRAND DELETE
curl --location --request DELETE 'http://localhost:8080/api/v1/brand/1/delete'


## PRODUCT CREATE
curl --location 'http://localhost:8080/api/v1/brand/create' \
--header 'Content-Type: application/json' \
--data '{
    "name": "Nike"
}'

curl --location 'http://localhost:8080/api/v1/product/create' \
--header 'Content-Type: application/json' \
--data '{
    "brandName": "Nike",
    "categoryName": "TOP",
    "price": 35000
}'


## PRODUCT UPDATE
curl --location --request PUT 'http://localhost:8080/api/v1/product/1/update' \
--header 'Content-Type: application/json' \
--data '{
    "brandName": "Nike",
    "categoryName": "TOP",
    "price": 32000
}'

## PRODUCT DELETE
curl --location --request DELETE 'http://localhost:8080/api/v1/product/1/delete'
```