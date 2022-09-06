# jpa-query-counter

### 만든 이유

서버 성능 개선을 위해, 성능 부하 테스트나 쿼리 카운팅을 하는 방식 등이 있다.
하지만 이런 성능 측정 관련 코드는 프로덕션 코드와는 분리되어야 한다고 생각했다.

- 성능 측정을 할 때만, dependencies 를 추가해서 측정하고. 완료된 후에는 간단하게 dependencies 만 제거할 수 있게 만들자.


### 사용법

```
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.sogorae:jpa-query-counter:1.0.0'
}
```

### 사용 결과
![image](https://user-images.githubusercontent.com/48307960/188566397-cae40d74-03db-4a44-bbc7-80dc6a1f4f2c.png)
