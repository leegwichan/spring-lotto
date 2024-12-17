# 로또 

해당 프로젝트는 Kotlin & Spring 을 이용하여 로또 당첨을 확인할 수 있는 Web Application Server 이다.

### 실행 방법

```shell
./gradlew build generateSwaggerUI
java -jar build/libs/spring-lotto-0.0.1-SNAPSHOT.jar
```

### 목표

- Kotlin & Spring 을 이용하여 아래의 기술들을 사용해본다.
  - Spring Web MVC
  - Spring Data JPA
  - Kotest 를 이용한 단위 테스트
  - Rest Assured 를 이용한 통합 테스트
  - Rest Docs API Spec & Swagger UI를 이용한 문서화
- 위 기술들을 이용하여 아래 요구 사항을 만족시킨다.

### 요구 사항

- 로또 규칙
  - 로또 번호의 숫자 범위는 1~45까지이다.
  - 1개의 로또를 발행할 때 중복되지 않는 6개의 숫자를 뽑는다.
  - 당첨 번호 추첨 시 중복되지 않는 숫자 6개와 보너스 번호 1개를 뽑는다.
  - 당첨은 1등부터 5등까지 있다. 당첨 기준은 아래와 같다.
    - 1등(FIRST): 6개 번호 일치
    - 2등(SECOND): 5개 번호 + 보너스 번호 일치
    - 3등(THIRD): 5개 번호 일치
    - 4등(FOURTH): 4개 번호 일치
    - 5등(FIFTH): 3개 번호 일치

- 기능
  - 로또의 한 묶음을 '티켓'이라고 명명하며 티켓 단위로 로또(들)를 저장할 수 있다.
  - 저장한 티켓은 조회할 수 있다.
  - 당첨 번호, 보너스 번호와 티켓 ID를 통해 당첨 결과를 조회할 수 있다.

### 관련 링크

- API 문서 (Swagger) : http://localhost:8080/docs/swagger-ui/index.html
- H2 Console : http://localhost:8080/h2-console
  - JDBC URL : jdbc:h2:mem:db
  - username : sa
  - password : 

