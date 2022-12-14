
<div align="center">

# π± Plant Diary π±

## π Intro π€

<b>Plant Diary</b>λ μλ¬Ό μΌκΈ°λ₯Ό κ³΅μ νλ μλΉμ€μλλ€.

μμ μ΄ κ°κΎΈλ μλ¬Όμ μ¬μ§κ³Ό μ΄μΌκΈ°λ₯Ό λλ λ³΄μΈμ!

## π¨ [WireFrame](https://www.figma.com/file/v0hO2KISARZllANfzOHyL7/Plant-Diary?node-id=0%3A1) π

<img width="661" alt="Screen Shot 2022-09-08 at 7 20 59 PM" src="https://user-images.githubusercontent.com/60090391/189098421-1d6fc6a7-eccc-4c9c-905d-c7e9c4c805ea.png">

---

## π©π»βπ» Contributors π§π»βπ»


| [μ€μμ§](https://github.com/blingblin-g) | [νμ±ν¬](https://github.com/sungheeHong) | [λ¬Έμ΄μ¬](https://github.com/Leeseul-Moon) |
|---------------------------------------|---------------------------------------| --- |
| κ²μκΈ / λκΈ CRUD                         | νμκ°μ / λ‘κ·ΈμΈ / λ§μ΄νμ΄μ§                    | Client μ λ° |
| Back-end                              | Back-end                              | Front-end |

## βοΈ Tech Stack π 
<img style="margin:5px; border: 2px solid white; border-radius: 20px" src="https://img.shields.io/badge/Java-green?style=flat-square&logo=java&logoColor=white"/>
<img style="margin:5px; border: 2px solid white; border-radius: 20px" src="https://img.shields.io/badge/Spring-green?style=flat-square&logo=Spring&logoColor=white"/>
<img style="margin:5px; border: 2px solid white; border-radius: 20px" src="https://img.shields.io/badge/React-blue?style=flat-square&logo=React&logoColor=white"/>
<img style="margin:5px; border: 2px solid white; border-radius: 20px" src="https://img.shields.io/badge/Redux-purple?style=flat-square&logo=Redux&logoColor=white"/>
<img style="margin:5px; border: 2px solid white; border-radius: 20px" src="https://img.shields.io/badge/AWS-232f3e?style=flat-square&logo=amazon&logoColor=white"/>

---

<br /><br />

## πΌ How to run βΈ

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
$ java -jar build/libs/{μ€ννμΌ}.jar
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

## π Directory Structure π

</div>

### Back-end


```shell
π¦ src
 β£ π main
 β β£ π java
 β β β π com
 β β β β π sparta
 β β β β β π plantdiary
 β β β β β β£ π command
 β β β β β β£ π config
 β β β β β β£ π configuration
 β β β β β β£ π controller
 β β β β β β£ π dto
 β β β β β β£ π entity
 β β β β β β£ π error
 β β β β β β£ π jwt
 β β β β β β£ π repository
 β β β β β β£ π service
 β β β β β β£ π shared
 β β π resources
 β β β£ π static
 β β β£ π templates
 β β β£ π application.properties
 β β β π aws.properties
 β π test
 β β£ π java
 β β β π com
 β β β β π sparta
 β β β β β π plantdiary
 β β β β β β£ π repository
 β β β β β β£ π service
 β β π resources
 β β β π application.properties
```

### Front-end

```shell
π¦ src
 β£ π components
 β β£ π CommentList
 β β£ π Header
 β β£ π ImageFileInput
 β β£ π JoinForm
 β β£ π LoginForm
 β β£ π MypageForm
 β β£ π PostForm
 β β π PostList
 β£ π imgs
 β£ π network
 β£ π pages
 β£ π redux
 β β£ π modules
 β£ π service
 β£ π util
```

<div align="center">

## πΎ Database Schema πΏ

![image](https://user-images.githubusercontent.com/60090391/189101923-3ec4e5b3-26e2-4986-8ed0-75808ba22b71.png)

</div>