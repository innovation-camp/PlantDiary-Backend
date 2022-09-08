
<div align="center">

# 🌱 Plant Diary 🌱

## 👋 Intro 🤟

<b>Plant Diary</b>는 식물 일기를 공유하는 서비스입니다.

자신이 가꾸는 식물의 사진과 이야기를 나눠보세요!

## 🎨 [WireFrame](https://www.figma.com/file/v0hO2KISARZllANfzOHyL7/Plant-Diary?node-id=0%3A1) 🖌

<img width="661" alt="Screen Shot 2022-09-08 at 7 20 59 PM" src="https://user-images.githubusercontent.com/60090391/189098421-1d6fc6a7-eccc-4c9c-905d-c7e9c4c805ea.png">

## 🤟 Swagger Address 🤟

```
{BASE_URL}/swagger-ui.html
```

---

## 👩🏻‍💻 Contributors 🧑🏻‍💻


| [윤수진](https://github.com/blingblin-g) | [홍성희](https://github.com/sungheeHong) | [문이슬](https://github.com/Leeseul-Moon) |
|---------------------------------------|---------------------------------------| --- |
| 게시글 / 댓글 CRUD                         | 회원가입 / 로그인 / 마이페이지                    | Client 전반 |
| Back-end                              | Back-end                              | Front-end |

## ⚙️ Tech Stack 🛠
<img style="margin:5px; border: 2px solid white; border-radius: 20px" src="https://img.shields.io/badge/Java-green?style=flat-square&logo=java&logoColor=white"/>
<img style="margin:5px; border: 2px solid white; border-radius: 20px" src="https://img.shields.io/badge/Spring-green?style=flat-square&logo=Spring&logoColor=white"/>
<img style="margin:5px; border: 2px solid white; border-radius: 20px" src="https://img.shields.io/badge/React-blue?style=flat-square&logo=React&logoColor=white"/>
<img style="margin:5px; border: 2px solid white; border-radius: 20px" src="https://img.shields.io/badge/Redux-purple?style=flat-square&logo=Redux&logoColor=white"/>
<img style="margin:5px; border: 2px solid white; border-radius: 20px" src="https://img.shields.io/badge/AWS-232f3e?style=flat-square&logo=amazon&logoColor=white"/>

---

<br /><br />

## 🛼 How to run ⛸

</div>

### Back-End

#### clone repository
```shell
$ git clone https://github.com/innovation-camp/PlantDiary-Backend.git
```

#### into the repository
```shell
$ cd PlantDiary-Backend
```

#### build
```shell
$ ./build gradlew
```

#### run server
```shell
$ java -jar build/libs/{실행파일}.jar
```

### Front-end

#### clone repository
```shell
$ git clone https://github.com/innovation-camp/PlantDiary-Frontend.git
```

#### into the repository
```shell
$ cd PlantDiary-Frontend
```

#### install packages
```shell
$ yarn
```

#### start app
```shell
$ yarn start
```

<div align="center">

## 📁 Directory Structure 📂

</div>

### Back-end


```shell
📦 src
 ┣ 📂 main
 ┃ ┣ 📂 java
 ┃ ┃ ┗ 📂 com
 ┃ ┃ ┃ ┗ 📂 sparta
 ┃ ┃ ┃ ┃ ┗ 📂 plantdiary
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 command
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 config
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 configuration
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 controller
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 dto
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 entity
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 error
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 jwt
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 repository
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 service
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 shared
 ┃ ┗ 📂 resources
 ┃ ┃ ┣ 📂 static
 ┃ ┃ ┣ 📂 templates
 ┃ ┃ ┣ 📜 application.properties
 ┃ ┃ ┗ 📜 aws.properties
 ┗ 📂 test
 ┃ ┣ 📂 java
 ┃ ┃ ┗ 📂 com
 ┃ ┃ ┃ ┗ 📂 sparta
 ┃ ┃ ┃ ┃ ┗ 📂 plantdiary
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 repository
 ┃ ┃ ┃ ┃ ┃ ┣ 📂 service
 ┃ ┗ 📂 resources
 ┃ ┃ ┗ 📜 application.properties
```

### Front-end

```shell
📦 src
 ┣ 📂 components
 ┃ ┣ 📂 CommentList
 ┃ ┣ 📂 Header
 ┃ ┣ 📂 ImageFileInput
 ┃ ┣ 📂 JoinForm
 ┃ ┣ 📂 LoginForm
 ┃ ┣ 📂 MypageForm
 ┃ ┣ 📂 PostForm
 ┃ ┗ 📂 PostList
 ┣ 📂 imgs
 ┣ 📂 network
 ┣ 📂 pages
 ┣ 📂 redux
 ┃ ┣ 📂 modules
 ┣ 📂 service
 ┣ 📂 util
```

<div align="center">

## 💾 Database Schema 💿

![image](https://user-images.githubusercontent.com/60090391/189101923-3ec4e5b3-26e2-4986-8ed0-75808ba22b71.png)

</div>
