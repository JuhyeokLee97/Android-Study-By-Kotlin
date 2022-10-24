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

<img width="613" alt="JDK dmg 설치" src="https://user-images.githubusercontent.com/40654227/197379784-14fe2d57-4b0f-4268-a5ae-0f026943dd9c.png">

다음과 같이 `JDK 11` 설치 위치를 확인할 수 있다.
```
/Library/Java/JavaVirtualMachines/jdk-11.0.16.jdk/Contents/Home/bin
```

### 2. PATH 설정
`./bin` 을 PATH에 등록하기 위해서 터미널에서 해당 디렉토리로 이동한다.

그리고 `vi ~/.zshrc` 명령어를 통해서 파일을 생성한다.

위 명령어를 통해서 **zshrc**창이 실행되면 `i`키를 누른 후 환경변수를 등록하는 아래 명령어를 입력한다.
`export PATH=${PATH}:/Library/Java/JavaCirturalMachines/jdk-11.0.16.jdk/Contents/Home/bin`

![JDK 환경 변수 설정](https://user-images.githubusercontent.com/40654227/197387615-94ba88f5-d334-434c-8811-9e1b67e692cc.png)

### 3. 마무리 확인
터미널에서 `echo $path` 명령어를 실행하면 아래와 같이 JDK 경로가 설정 된 것을 확인할 수 있다.
![JDK Path 설정 확인](https://user-images.githubusercontent.com/40654227/197387561-354b7fb3-91a5-4359-b101-72a267681438.png)




