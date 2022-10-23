# JAVA 환경변수란

## 환경변수(Environment Variable)란

위키피디아에서는 <strong>환경변수(Environment Variable)</strong>에 대해 다음과 같이 설명하고 있다.

> An **environment variable** is a dynamic-named value that can affect the way
running porcesses will behave on a computer.
> They are part of the encionment in which a process run.
> For example, a running process can query the value of the TEMP environment variable
to discover a suitable location to store temporary files, or the HOME or USEPROFILE variable
to find the directory structure owned by the user running the process.

간단하게 정리하면 **환경변수**란 프로세스가 컴퓨터에서 동작하는 방식에 영향을 미치는 동적인 값들의 모임이자 운영체제가 참조하는 **변수**인 것 같다.

## JAVA 환경변수 설정 이유
JAVA 환경변수를 설정하지 않은 경우, 만약 터미널에서 Java를 실행시키려고 할 때 해당 디렉토리로 이동하지 않은 경우에 동작하지 않는다.
즉 Java를 실행하기 위해서 **항상** 해당 디렉토리 위치에서만 실행해야한다는 것이다.

반대로 JAVA 환경변수를 설정한 경우에는 어느곳에서 jdk > bin 디렉토리에 있는 프로그램들을 실행시킬 수 있다.
즉 **JAVA 환경변수 설정을 해야하는 이유**는 어느 위치에서든지 JAVA를 인식하고 실행시키기 위해서이다.
특히 Java로 개발하는 경우 jdk > bin > **javac**(자바 컴파일러)를 통해서 프로젝트의 클래스 파일들을 컴파일하는데
환경변수가 설정이 되어 있지 않으면 Eclipse, Android Studio와 같은 IDE에서 컴파일이 안되는 현상이 발생할 수 있다.

## JAVA 환경 변수 설정 In Mac
### 1. JDK 설치
나는 `JDK 11`를 사용하기 위해서 Oracle에서 `macOS Arm 64 DMG Installer`를 다운받았다.
설치 받은 파일을 실행 시켜 아래와 같은 `설치 마법사`를 통해서 `JDK 11`를 설치한다.

<img width="613" alt="JDK dmg 설치" src="https://user-images.githubusercontent.com/40654227/197379784-14fe2d57-4b0f-4268-a5ae-0f026943dd9c.png">ㅎ

### 2.
