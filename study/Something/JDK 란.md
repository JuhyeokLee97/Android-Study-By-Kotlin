# JDK 란
## 개요

위키피디아에서는 <strong>JDK(Java Development Kit)</strong>에 대해 다음과 같이 설명하고 있다.

> The Java Development Kit (**JDK**) is a distribution of Java Technology by Oracle Corporation.
> It implements the Java Language Specification (**JLS**) and the Java Virtual Machine Specification
(**JVMS**) and provides the Standard Edition (**SE**) of the Java Application Programming Interface (**API**)
> It is derivatice of the community driven OpenJDK which Oracle steward.
> It provides software for working with Java application.
> Examples of included software are the virtual machine, a compiler, performance monitoring tools,
a debuger, and other utilities that Oracle considers useful for Java programmer.

즉 간단하게 정리하면, Java로 소프트웨어(ex. Android)를 개발할 수 있도록 여러 기능들을 제공하는 키트(패키지)라고 할 수 있다.

## JDK contents

JDK는 기본적인 프로그래밍 툴들의 컴포넌트를 가지고 있다.

- `appletviewr`: 이 도구는 웹 브라우저 없이 Java applet을 실행하고 디버깅하는데 사용할 수 있다.
- `apt`: 이 도구는 어노테이션을 사용할 수 있다.
- `java`: 이 도구는 Java Application을 로드할 수 있다. 구체적으로 Javac compiler를 통해 생성된 클래스 파일을 해석할 수 있다.
- `javac`: 이 도구는 소스 코드를 Java bytecode로 변환하는 **Java Complier**이다.
- `javadoc`: 이 도구는 소스 코드로부터 자동적으로 documentation을 생성할 수 있도록 하는 도구이다.
- `jar`: 이 도구는 JAR 파일 관리에 도움이 되는 것으로, 관련 클래스 라이브러리를 단일 JAR 파일로 패키지하는 아카이브이다.
- `jdb`: 이 도구는 디버깅할 수 있도록 한다.
