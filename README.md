# ToDo-List

![facebook_cover_photo_2](https://user-images.githubusercontent.com/66009926/212352004-68603d94-d3d1-4687-b48c-0cfe9a2d2526.png)

## 📔 **Project Info**

#### Development
<pre class="highlight highlight-html">
Based on <a href="https://spring.io/projects/spring-boot">Spring Boot</a> with Java Language
</pre>

#### Requirement
```
Spring Boot : 2.7.7 이상
Java : 11 이상
```

#### Build Setup
<pre class="highlight highlight-html">

// 1단계 : Build
sudo ./gradlew build

// 2단계 : Jar 파일 만들기
sudo ./gradlew bootJar

// 3단계 : Jar 파일 실행하기
sudo java -jar build/libs/todo-0.0.1-SNAPSHOT.jar
</pre>

#### Branch Management
```bash

#Branch naming rules
main
실제 product 코드가 담긴 브랜치

dev
새로운 기능 배포 준비를 위한 개발 브랜치

feature/#1-이슈-제목
기능 추가를 위한 브랜치, 이슈 넘버를 기입하여 구분

bugfix/#1-이슈-제목
hotfix/#1-이슈-제목
이슈 해결을 위한 브랜치, 이슈 넘버를 기입하여 구분

#Contribute method
1. main 브랜치는 실제 서비스 운영 중인 코드가 있습니다.
2. dev 브랜치에서는 테스트 중인 새로운 기능의 코드가 있습니다.
3. 팀원 이슈, 기능에 따라 브랜치로 분기하여 작업 후 dev 브랜치에 Pull request
4. 개발 완료시 dev 브랜치를 main 브랜치에 병합

```

<br>

## 📔 **Summary**

앞으로 1달 동안 Todo List를 만드는 프로젝트를 통해 팀원들 간의 협업을 연습하고 코어 기술인 My batis와 Spring Boot를 제대로 사용하여 실전 프로젝트를 대비한다.

## 📕 **Background**

1. 실전 프로젝트를 연습하기 위한 워밍업
2. 팀원들 간의 손발을 맞추기 위함(멘토)
3. 실제 서비스 배포가 아닌 코어기술(마이바티스 & Spring Boot)를 써보기 위함

## 📗 **Goals**

1. Github로 협업을 하기 위한 최소한의 준비
    1. 브랜치 만들어서 작업하는 것
    2. PR 날리는 법
    3. 코드 리뷰 하는 법
2. 테스트 코드 작성해보기
    1. Given - When - Then 패턴으로 구현하기
3. 마이바티스 & Spring Boot 만을 사용해서 구현한다.
- 재연 : 회원가입할 때 -> 이메일 검증을 해봤으면 한다.
- 선진 : 최대한 코드 깔끔하게 짜기

## 📙 **Non-Goals**

1. CI & CD는 하지 않는다.
2. 로깅 & 모니터링도 하지 않는다.
3. 인프라 CLOUD & 컨테이너 부분은 고려하지 않는다.
4. 디자인 부분은 고려하지 않는다.

## 🔎 시스템 구조도

![Untitled](https://user-images.githubusercontent.com/66009926/212352136-237a3f96-ec55-4435-8164-b93fd20dfedd.png)

## 🔎 와이어 프레임

![Untitled 1](https://user-images.githubusercontent.com/66009926/212352041-a1db85a6-39c6-4d94-bb0f-76ebcff10f5f.png)

## 🔎 팀원 소개

| 김선진 | 조재연 |
| --- | --- |
| <img src= "https://avatars.githubusercontent.com/u/66009926?v=4" width = "200"> |  <img src= "https://avatars.githubusercontent.com/u/59726665?v=4" width = "200"> |
