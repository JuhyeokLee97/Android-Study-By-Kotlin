
# Open-Closed Principle (OCP): 개방 폐쇄 원칙

## 개요
지난 번 [SRP(단일 책임 원칙)](https://devgeek.tistory.com/135)에 이어서 <strong>OOP-SOLID 2원칙인 Open-Closed Principle (OCP)</strong>에 대해 학습한 내용을 나누려고 합니다.
<strong>Open-Closed Principle (OCP)</strong>을 직독하자면 <strong>"열려있고 닫혀있는 원칙...?"</strong>이다.
처음에 **OCP** 개념을 봤을 때, '이게 대체 무슨 소리인가' 싶었습니다.
서로 반대되는 개념을 지켜야하는 원칙이라니... 바로 이해되지 않는 개념이었습니다.

하지만 이해가 안되더라도 계속해서 자료를 찾아보고 예제를 찾아보며 제 것으로 체화하기 위해서 곱씹고 또 곱씹었더니 제가 내린 **OCP** 개념은 다음 이미지와 같았습니다.

<img width=700
		src="https://user-images.githubusercontent.com/40654227/226610393-39602661-ce10-484c-b4a2-77a2cc400c6b.png"/>

지금 바로 보시면 '엥...?!' 하실 수도 있습니다. 제 글을 다 읽어 보시고 여유가 되시면 그 때 다시 한 번 봐주시고 제가 잘 표현했는지 감상해주시면 감사하겠습니다.

각설하고 본 내용으로 들어가겠습니다!!

"SRP 포스팅과 같은 맥락으로 개념정리 후, **OCP**를 위배한 샘플코드와 이를 **OCP**에 부합하게 수정한 샘플 코드로 독자님들이 개념만 **암기**하여 죽어있는 지식이 아닌 살아있는 지식을 체화할 수 있도록 진행하겠습니다!!!"

**참고). 프로그래밍 언어는 Kotlin 입니다.**

## 정의 
위키피디아에서는 **OCP**에 대해서 다음과 같이 설명하고 있습니다.
> Software entities (classes, modules, function, etc.) should be open for extension, but closed form modification.
> that is, such an entity can allow its behavior to be extended without modifying its source code.



제가 개발을 하면서 그리고 OCP에 대해 학습하면서 느낀 **OCP**는 **"기능을 추가할 때에는 기존의 코드를 수정하지 않고도 가능해야한다.!!"** 였습니다.
OCP를 처음 접하신 분이시라면 제가 말도 안되는 소리를 하는구나 하실 수도 있습니다. 맞습니다 어떻게 기능을 추가하는데 코드를 수정하지 말라고 하는 것인가 싶습니다. 하지만 OCP를 위반한 샘플 코드와 수정된 코드를 보시면 제가 말한 내용이 이해되실 거라고 믿습니다! :)

OCP의 개념(정의)는 어디서든 검색해서 볼 수 있기 때문에 여기서 마치고 샘플 코드로 넘어가겠습니다!

## OCP 샘플 - 직원 관리 시스템 수정
### 서비스 개요

제가 직원 수가 정말 적은 스타트업에 신입으로 취업에 성공했다고 가정해보겠습니다.
저의 첫 담당 업무는 기존에 개발되어 있는 [직원 관리 시스템]에서 급여 관리에 해당하는 부분을 수정하는 것이었습니다.
요구사항은 정말 간단합니다.
- 업무별 급여가 달라져서 급여 관리 시스템 로직에 수정이 필요합니다.
- 업무 카테고리는 { 개발자, 디자이너, 프로젝트 매니저, 일반사원 } 입니다.

의욕이 넘치는 신입인 저는 다음과 같은 기존 코드에서 `Payroll` 클래스에서 `calculatePay()` 함수를 수정하면 되겠다는 것을 파악하고 스스로 수정할 부분을 찾았음에 스스로를 칭찬했습니다.

``` kotlin
data class EmployeeData(
	val name: String,
	
)
```


## Sample Code

### Violated Code
``` kotlin
data class EmployeeData(
	val name: String,
	val age: Int,
	val salary: Double,
	val email: String,
	val phone: String,
	val role: String
)

// Payroll class to calculate employee pay
class Payroll {
	fun calculatePay(employee: EmployeeData): Double {
		// code to calculate employee's pay
		when(employee.role) {
			"AOS" -> 1000
			"iOS" -> 250
			"SERVER" -> 400
			"FRONT" -> 300
			"FLUTTER" -> 200
		}
	}

	fun calculateTax(employee: EmployeeData): Double {
		// code to caluclate employee's tax
	}

	fun calculateBonus(employee: EmployeeData): Double {
		// code to calculate employee's bonus
	}
}
```

- 위반 사항
    - 일반적으로 새로운 role 이 생긴경우 이에 대해 확장하기 위해서는 `caluclatePay` 에서 `when` 에 새로운 상태값을 추가해야한다.
    - 예를 들어, 개발자 직군 뿐만 아니라 모든 직원들에 대한 급여를 처리하기를 원한다면 각 직군에 대한 확장이 필요한데 `PROJECT_MANAGER`, `Designer`, `HR` 등이 추가된다면 Payroll 클래스에서 `PROJECT_MANAGER`, `Designer`, `HR` 에 대해서 확장하기 위해서는 `calculatePay` 함수를 수정해야한다.
