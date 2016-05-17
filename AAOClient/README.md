## This is a simple tool for connecting to AAO site of my college base on Spring Boot
This project was made in 2014,compiling with Java.So you need open/Oracle JDK1.7+ and Maven for project dependency.
this toy include some interesting technics,including Restful API，HttpClient via Java and Spring Data JPA.
## Build
```
>./mvnw .
```
## Some API implement
```
request：
GET /api/evaluation_list/{username}/
response：
{
  "result": true,
  "evaluation_list": [
    {
      "id": "271627",
      "classId": "73926",
      "className": "计算机图形学",
      "teacher": "",
      "evaluated": true 
    },
    {
      "id": "337241",
      "classId": "78089",
      "className": "计算机组成原理与接口技术",
      "teacher": "",
      "evaluated": true
    },
    {
      "id": "353426",
      "classId": "78462",
      "className": "数字图像处理技术",
      "teacher": "",
      "evaluated": true
    },
    {
      "id": "462682",
      "classId": "77837",
      "className": "软件代码开发技术",
      "teacher": "",
      "evaluated": true
    },
    {
      "id": "466531",
      "classId": "77941",
      "className": "操作系统",
      "teacher": "",
      "evaluated": true
    },
    {
      "id": "490933",
      "classId": "77700",
      "className": "计算机网络原理",
      "teacher": "",
      "evaluated": true
    },
    {
      "id": "489612",
      "classId": "78321",
      "className": "操作系统课程设计",
      "teacher": "",
      "evaluated": true
    },
    {
      "id": "481474",
      "classId": "78673",
      "className": "软件工程课程设计",
      "teacher": "",
      "evaluated": true
    },
    {
      "id": "495718",
      "classId": "72781",
      "className": "软件设计与体系结构",
      "teacher": "",
      "evaluated": true
    }
  ]
}
request：
POST /api/evaluation_teaching 
param
{
  "username": "sequarius",
  "token":"token"
  "level":"A", 
  "evaluation_list": [ 
    {
      "id": "271627",
      "classId": "73926",
    },
    {
      "id": "337241",
      "classId": "78089",
    }
  ]
}
response：
{
  "result": true,
  "results": [
    {
      "result": true, 
      "id": "271627"
    },
    {
      "result": true,
      "id": "337241"
    }
  ]
}
```

## License
The MIT License (MIT)

Copyright (c) 2016 Sequarius

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE U
