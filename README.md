# jpa-query-counter

### 만든 이유

서버 성능 개선을 위해, 성능 부하 테스트나 쿼리 카운팅을 하는 방식 등이 있다.
하지만 이런 성능 측정 관련 코드는 프로덕션 코드와는 분리되어야 한다고 생각했다.

- 성능 측정을 할 때만, dependencies 를 추가해서 측정하고. 완료된 후에는 간단하게 dependencies 만 제거할 수 있게 만들자.
- 인수 테스트 후, 요청 당 발생한 쿼리와 N+1이 의심되는 테스트에 대한 내용을 파일로 생성해준다.

### 사용법

```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.sogorae:jpa-query-counter:1.0.6'
}
```

### 사용 결과
<img width="962" alt="image" src="https://user-images.githubusercontent.com/48307960/191501803-fc20b73b-27b4-41a3-8cf2-3657f5392cff.png">


### 이슈
- SpringInterceptor에 의해 실행되는 쿼리는 인식하지 못하는 것으로 추정.
- Hibernate를 사용하지 않는 쿼리의 경우 카운트에 포함되지 않음.
