# Terra international assignment
<hr>

## 프로젝트 설정
- Java 17
- Gradle
- Spring Boot 3.2.5
- Dependency
    - spring-boot-starter-web
    - spring-boot-starter-data-jpa
    - spring-boot-devtools
    - springdoc-openapi-starter-webmvc-ui
    - spring-boot-starter-test
    - junit-platform-launcher
    - h2database
    - mariadb-java-client
    - lombok
<hr>

## 프로젝트 실행 방법
1. Maria DB 설치 및 terra database 생성
   - CREATE DATABASE TERRA
2. 프로젝트 clone 후 해당 프로젝트에서 gradle build
   - ./gradlew build
3. 빌드된 jar 파일 prod 환경으로 실행
   - java -Dspring.profiles.active=prod -jar build/libs/assignment-0.0.1-SNAPSHOT.jar
<hr>

## 요구사항 및 해결방법

### 데이터 베이스 요구 사항
- H2 DB 를 개발 및 테스트, MariaDB를 운용 DB 로 사용
  - application-test, application-dev, application-prod 환경 설정 파일을 따로 만들어 적용

<br>

### 기능 요구사항
- 1분 단위 CPU 사용률 수집 및 DB 저장
  - OperatingSystemMXBean 을 이용해 CPU 사용률 수집
  - UsageService에서 위 OperatingSystemMXBean를 불러와 CPU 사용률 DB에 저장
  - 스케줄러를 통해 1분 단위로 반복 수행

- 분 단위 조회 : 지정한 시간 구간의 분단위 CPU 사용률 조회
  - 년, 월, 일, 시작 시간, 종료 시간을 파라미터로 받아와 DB에서 해당 데이터 조회
  - LocalDate.now().minusWeeks(1L)를 통해 최근 1주일 데이터 조회 기능 구현

- 시 단위 조회: 지정한 날짜의 시  단위 CPU 최소/최대/평균 사용률을 조회합니다.
  - 년, 월, 일 을 파라미터로 받아와 DB에서 해당 기간 MIN(), MAX(), AVG() 쿼리를 이용해 조회
  - LocalDate.now().minusMonths(3L)를 통해 최근 3달 데이터 조회 기능 구현
    
- 일 단위 조회: 지정한 날짜 구간의 일  단위 CPU 최소/최대/평균 사용률을 조회합니다.
    - 년, 월, 시작일, 종료일 을 파라미터로 받아와 DB에서 해당 기간 MIN(), MAX(), AVG() 쿼리를 이용해 조회
    - LocalDate.now().minusYears(1L)를 통해 최근 1년 데이터 조회 기능 구현

- Swagger를 사용하여 API 문서화
  - spring doc 를 이용한 Swagger ui 문서 작성
  - http://localhost:8090/swagger-ui/index.html#/
<hr>

## API 문서
## usage
### 1. Get Usage per min
| URL | Method | URL Params                                                         |
|----|--------|--------------------------------------------------------------------|
| /api/v1/usages/perMin    | Get    | year : int, month : int, day : int, startHour : int, endHour : int |
- Success response example
```json
{
  "resCode": {
    "code": "S-05",
    "keyWord": "Found"
  },
  "msg": "조회 완료",
  "data": [
  {
    "yearColumn": 2024,
    "monthColumn": 5,
    "dayColumn": 2024,
    "hourColumn": 1,
    "minColumn": 1,
    "cpuUsage": 15.43
  }
  ],
  "isSuccess": true
}
```

- Error response example
```json
{
  "resCode": {
    "code": "F-01",
    "keyWord": "Bad Request"
  },
  "msg": "잘못된 요청 파라미터 입니다.",
  "data": null,
  "isSuccess": false
}
```

<br>

### 2. Get Usage per hour
| URL | Method | URL Params                                                         |
|----|--------|--------------------------------------------------------------------|
| /api/v1/usages/perMin    | Get    | year : int, month : int, day : int |
- Success response example
```json
{
  "resCode": {
    "code": "S-05",
    "keyWord": "Found"
  },
  "msg": "조회 완료",
  "data": [
    {
      "yearColumn": 2024,
      "monthColumn": 5,
      "dayColumn": 1,
      "hourColumn": 1,
      "minCpuUsage": 8.41,
      "maxCpuUsage": 8.41,
      "avgCpuUsage": 8.41
    }
  ],
  "isSuccess": true
}
```

- Error response example
```json
{
  "resCode": {
    "code": "F-01",
    "keyWord": "Bad Request"
  },
  "msg": "잘못된 요청 파라미터 입니다.",
  "data": null,
  "isSuccess": false
}
```

<br>

### 3. Get Usage per day
| URL | Method | URL Params                                                           |
|----|--------|----------------------------------------------------------------------|
| /api/v1/usages/perMin    | Get    | year : int, month : int, startDay : int, endDay : int|
- Success Response
```json
{
  "resCode": {
    "code": "S-05",
    "keyWord": "Found"
  },
  "msg": "조회 완료",
  "data": [
    {
      "yearColumn": 2024,
      "monthColumn": 1,
      "dayColumn": 1,
      "minCpuUsage": 5.25,
      "maxCpuUsage": 5.25,
      "avgCpuUsage": 5.25
    }
  ],
  "isSuccess": true
}
```

- Error Response
```json
{
  "resCode": {
    "code": "F-01",
    "keyWord": "Bad Request"
  },
  "msg": "잘못된 요청 파라미터 입니다.",
  "data": null,
  "isSuccess": false
}
```

<br>

