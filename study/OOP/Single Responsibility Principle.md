# Single Responsibility Principle (SRP): 단일 책임 원칙

## 개요
개발 공부를 하면서 느끼는 어려움은 항상 용어와 추상적인 개념 설명인 것 같습니다.
**단일 책임 원칙** 명칭 그대로 **"하나의 책임만을 갖어야 한다"**인데요. 
다르게 말하면 **"하나의 클래스가 수정되기 위해서는 오직 하나의 이유여야만 한다."**

만약 개발 면접 또는 개발자들 사이에서 **SRP(단일 책임 원칙)**에 대해서 알고 있냐는 질문에 단순히 정의를 말한다면 다른 사람들이 느끼기에 정말 SRP를 이해하고 있구나라고 생각할까요?

그래서 저는 오히려 정의를 "외우기" 보다는 제 방식대로 이해하고 SRP를 위배한 예제와 이를 해결하는 코드들을 작성해보며 SRP 개념을 "체화" 하기 위한 방식으로 학습했습니다.

이 글을 보시는 여러분들도 단순히 개념(정의)만을 읽고 가시지 않고, SRP를 위배한 샘플 코드와 SRP를 따르기 위해 수정된 부분을 음미 하시며 죽어있는 지식이 아닌 살아있는 지식으로 SRP를 이해하시기 바랍니다. 

**참고). 프로그래밍 언어는 Kotlin 입니다.**

## 정의 
위키피디아에서는 SRP에 대해서 다음과 같이 설명하고 있습니다.
> A module should be responsible to one, and only one, actor

그리고 SRP라는 개념을 만드신 Robert C. Marti 님께서는 다음과 같이 말했습니다.
> A class should have only one reason to change

제가 개발을 하면서 그리고 SRP에 대해 학습하면서 느낀 SRP는 **"하나의 클래스에 다 때려박지 말아라!!"** 였습니다.
말이 좀 거칠어 보일 수 있지만, SRP를 지키기 위한 방법으로는 딱 맞는 표현과 설명인 것 같습니다.

SRP의 개념(정의)는 어디서든 검색해서 볼 수 있기 때문에 여기서 마치고 샘플 코드로 넘어가겠습니다!

## SRP 샘플 1 - 무인 계산기
### 서비스 개요

<img width="1000" alt="셀프 계산기 - 1" src="https://user-images.githubusercontent.com/40654227/224337582-a690151d-ea5e-4f19-84fb-73a71d654ab2.png">

우리가 외주를 받아서 대형마트에 있는 무인 계산기 소프트웨어를 만든다고 가정해보겠습니다.
그러면 우리는 **셀프 계산기** 클래스를 하나 만들어서 해당 클래스에서 필요한 기능들을 구현할 수 있습니다.
셀프 계산기에서 해야할 일은 다음과 같습니다.
- 상품 구매를 위해 바코드로 상품을 등록했을 경우, **구매 상품 목록에 등록**해야 합니다.
- 구매 상품 목록에 있는 상품 중 취소를 한 경우, ** 취소을 목록에서 제거**해야 합니다.
- 소비자가 담아온 상품을 빠트리지 않고 구매할 수 있도록 하기 위해 **상품 등록 전(인벤토리에 있는)의 개수를 파악**해야 합니다.
- 소비자가 상품을 구매(취소)하기 위해 상품을 등록하면 **통합 가격을 계산**해야 합니다.
- 소비자가 상품을 최종적으로 구매하기 위해서 **결제** 서비스가 필요합니다.
- 마지막으로, 셀프 계산기를 사용하는 업체에 매출정보를 제공하기 위해서 DB에 **상품 판매 정보를 저장**해야 합니다.

그러면 이제 위의 요구사항을 참고하여 **셀프 계산기** 클래스를 만들어 보겠습니다.

### SRP 를 위반한 코드
``` kotlin
/** 상품(Item) 정보를 담당하는 Data Class **/
data class Item(
	val id: Long,
	val name: String,
	val price: Int
)

/** 셀프 계산기 클래스 **/
class OrderMachine(var items: MutableList<Item>) {
	
	/** 상품 등록 **/
	fun addItem(item: Item) {
		items.add(item)
		updateInventory(item, -1)
		calculateTotalPrice()
	}

	/** 상품 취소 **/
	fun removeItem(item: Item){
		item.remove(item)
		updateInventory(item, -1)
		calculateTotalPrice()
	}

	/** 상품 등록 전 받침대 업데이트 **/
	private fun updateInventory(item: Item, quantity: Int) { ... }

	/** 총 합계금 계산 **/
	private fun calculateTotalPrice() { ... }

	/** 결제하기 **/
	fun purchase() { ... }

	/** 상품 판매 정보 저장 **/
	fun saveOrderToDatabase() { ... }
}
 
```

여러분이 보기에는 위의 코드가 **SRP**를 위반한 코드처럼 보이시나요?
물론 소제목에 "SRP 를 위반한 코드"라고 작성되어 있기 때문에 당연히 위반된 코드라고 생각하실 수 있습니다.

저는 SPR 위반된 코드인지를 판단하기 위해서는 **'해당 클래스가 변경되어야 할 이유가 몇 가지 일까...?'**를 고민해 봅니다.
다음 그 이유들이 의미적으로 하나의 이유인지 다른 이유인지를 보고 SPR 위반사항을 판단합니다.

<img src="https://user-images.githubusercontent.com/40654227/224472506-0705be93-ff7d-4073-b9bb-3f9e5d4a172c.png" width=1000/>

위의 샘플 코드는 넓은 의미에서는 수정사항이 생길 때, {상품 등록(취소) 및 구매, 인벤토리 관리, 가격 계산, 상품 판매 정보 저장}와 같은 이유로 수정이 될 수 있지만 모두 무인 계산기에 포함되는 기능이라고 생각하면 SRP를 위반하지 않고 있다고 볼 수 있습니다. 하지만 이 글에서는 SRP에 대해서 공부하기 위함이기 때문에 좁은 의미로 보고 각각의 수정이 필요한 이유가 다르다고 보겠습니다. (물론 누군가는 SRP 관점에서 위 코드는 당연히 위반했다고 할 수도 있습니다.)

그러면 앞서 말한 책임(수정될 이유)가 `OrderMachine` 클래스에서 4가지가 됩니다. 그리고 SPR를 따르기 위해서 우리는 각 4가지 책임에 맞게 클래스를 분리시켜주면 됩니다.


### SRP 를 따르기 위해 수정된 코드

**SRP**를 따르기 위해서 각 책임에 따라 클래스를 나누었습니다.
- 상품 등록(취소) 및 구매: `OrderMachine`
- 인벤토리 관리: `InventoryManger`
- 가격 계산: `PriceManger`
- 상품 판매 정보 저장: `DatabaseManger` 


``` kotlin
/** 상품(Item) 정보를 담당하는 Data Class **/
data class Item(
	val id: Long,
	val name: String,
	val price: Int
)

class InventoryManager {
	/** 상품 등록 전 받침대 업데이트 **/
	fun calculateTotalPrice(item: Item, quantity: Int) { ... }
}

class PriceManager {
	/** 총 합계금 계산 **/
	fun calculateTotalPrice(items: List<Item>): Int { ... }
}

class DatabaseManager {
	/** 상품 판매 정보 저장 **/
	fun saveOrderToDatabase(item: List<Item>, totalPrice: Int) { ... }
}

class OrderMachine(
	private val inventoryManager: InventoryManager,
	private val priceManager: PriceManager,
	private val dbManager: DatabaseManager
) {

	private val items: MutableList<Item> = mutableListOf()
	
	fun addItem(item: Item) {
		items.add(item)
		inventoryManager.updateInventory(item, -1)
		priceManager.calculateTotalPrice(items)
	}

	fun removeItem(item: Item) {
		items.remove(item)
		inventoryManager.updateInventory(item, -1)
		priceManger.calculateTotalPrice(items)
	}

	fun purchae(items) {
		val totalPrice = priceManager.calculateTotalPrice(items)
		dbManger.saveOrderToDatabase(items, totalPrice)
	}
}
```

이렇게 코드로 보면 **SRP**를 따르는 이유, 즉 장점을 확연히 느끼실 수 있을 것 같습니다.
SRP를 지키면서 개발하는 이유 중 하나가 **유지보수**입니다.
SRP를 위반한 코드를 바탕으로 '상품 구매 로직'이 수정되거나 'DB에 매출 정보를 저장하는 로직'이 수정된다고 했을 때, `OrderMachine` 클래스에서 수정을 해야합니다. 여기까지는 괜찮다고 볼 수 있습니다.
하지만 만약 `OrderMachine` 비슷한 기능을 하는 100개의 클래스가 생성되었는데, 각각의 클래스에서 모두 'DB에 매출 정보를 저장하는 로직'을 클래스 내부에서 구현되어 있다고 한다면!! 해당 기능의 수정이 필요하면 개발자는 모든 클래스에서 해당 부분을 수정해야합니다.
우리는 컴퓨터가 아닙니다. 사람이라서 당연히 실수할 수 있습니다. 만약 100개의 클래스 모두를 완.벽.하.게. 수정했지만 `OrderMachine`에서는 수정이 반영이 안되어있다면 어떻게 될까요? 당연히 예상하지 못한 버그가 발생하게 됩니다.

**SRP**를 따르게 되면 각 기능(책임)을 하나의 클래스에서 관리하고 있기 때문에, 100개의 클래스가 추가되었다고 하더라도 `DatabaseManager` 클래스를 의존하여 기능이 구현되어있다면 `DatabaseManager`에서만 수정을 하면 되고 이를 의존하고 있는 클래스에서는 별 다른 수정사항이 발생하지 않습니다.

어떤가요?? **SRP**를 따르는 경우 유지보수 관점에서 매우 편리해지지 않을까요?? 그래서 SRP를 따라야한다고 다들 이야기 하는 거겠죠?

다음으로는 **무인 계산기**보다는 좀 더 복잡한(?) 클래스를 가지고 **SRP**를 위반했는지 위반했다면 어떻게 수정해야할 지 가볍게 코드로 다루도록 하겠습니다.
앞의 내용을 통해 SRP를 이해하셨다면 안 보셔도 무방하다고 생각합니다. 또는 이해한 부분이 맞는지 확인 차 가볍게 읽는 것도 추천드립니다! :) 

## SRP 샘플 2 - 직원 관리 서비스
### 서비스 개요

<img src="https://user-images.githubusercontent.com/40654227/224527101-608e346d-f57e-4541-ba5c-6645a5b6494f.png" width = 1000/>

제가 스타트업에 취직한 상황을 가정해보겠습니다.
직원이 별로 많지 않아서 신입 개발자인 저에게 직원 관리 시스템을 대표님께서 만들어달라고 요청이 왔습니다.
기능 요청사항은 다음과 같습니다.
- 직원 개인 정보
- 급여 관리
- 인상 정보 관리
- 공지 서비스 

그러면 신입인 저는 위 이미지와 같이 클래스를 다음과 같이 작성했습니다.

``` kotlin
class Employee {

	/** 직원 개인 정보 **/
	var name: String = ""
	var age: Int = 0
	var salary: Int = 0
	var email: String = ""

	/** 급여 관리 **/
	fun calculatePay(): Int { 
		// code to calculate employee's pay
	}

	/** 인사 정보 관리 **/
	fun saveToDatabase() {
		// code to save employee to database
	}

	/** 공지 서비스 **/
	fun sendEamil(subject: String, message: String) {
		// code to send email to employee
	}
}
```

제 서비스 결과물에 만족을 하고 추가적으로 기능 요청사항이 생겼다고 하겠습니다.
그래서 신입인 저는  `Employee` 클래스를 기분 좋게 확장을 했습니다. 
확장한 코드는 **SRP를 위반한 코드**에서 설명하도록 하겠습니다.

### SRP 를 위반한 코드

추가적인 기능 요청사항은 다음과 같았습니다.
- 직원 개인 정보
	- 핸드폰 번호 추가
- 급여 관리
	- 세금 계산 기능 추가
	- 보너스 기능 추가
- 인상 정보 관리
	- 직원 정보 삭제 기능 추가
	- 직원 정보 이름으로 검색 기능 추가
- 공지 서비스 
	- 전체 공지 메일 서비스 추가
	
추가된 기능에 대해서 코드를 다음과 같이 작성해보았습니다.

``` kotlin
class Employee {

	/** 직원 개인 정보 **/
	var name: String = ""
	var age: Int = 0
	var salary: Int = 0
	var email: String = ""
	var phone: String = ""

	/** 급여 관리 **/
	fun calculatePay(): Int { ... }
	fun calculateTax(): Int { ... }
	fun calculateBonus(): Int { ... }
	
	/** 인사 정보 관리 **/
	fun saveToDatabase() { ... }
	fun deleteEmployee() { ... }
	fun findEmployeeByName(): Employee { ... }
	
	/** 공지 서비스 **/
	fun sendEamil(subject: String, message: String) { ... }
	fun sendBulkEmail(subject: String, message: String) { ... }
}

```

여러분이 보시기에 `Employee` 클래스는 어떤가요??
나름 코드 정렬도 주석을 추가해서 보기 좋게 했고, 괜찮은 코드처럼 보이실까요?
제 글을 처음부터 읽어주신 분이라면 **주석** 덕분에 **SRP** 위반사항이 더 잘 보이실 것 같습니다.
맞습니다. 해당 `Employee` 클래스는 대놓고 **SRP를 위반했습니다!**
그러면 SRP를 따르는 클래스를 만들기 위해서는 어떻게 해야할까요?
당연히 클래스에 있는 기능(책임)들을 나누어 보면 됩니다.
이 샘플에서는 크게 4가지로 나누어 볼 수 있습니다.
- 직원 개인 정보
- 급여 관리
- 인상 정보 관리
- 공지 서비스 

다음에서 해당 책임을 갖는 클래스들을 생성하여 SRP를 따르는 클래스들로 분리하도록 하겠습니다.

### SRP 를 따르기 위해 수정된 코드

``` kotlin
/** [책임]: 직원 개인 정보 **/
data class EmployeeData(
	val name: String,
	val age: Int,
	val salary: Int,
	val email: String,
	val phone: String
)

/** [책임]: 급여 관리 **/
class Payroll {

	fun calculatePay(employee: EmployeeData): Int { ... }
	fun calculateTax(employee: EmployeeData): Int { ... }
	fun calculateBonus(employee: EmployeeData): Int { ... }
}

/** [책임]: 인사 정보 관리 **/
class EmployeeRepository {

	fun saveToDatabase(employee: EmployeeData) { ... }
	fun deleteFromDatabase(employee: EmployeeData) { ... }
	fun findByName(name: String): EmployeeData { ... }
}

/** [책임]: 공지 서비스 **/
class EmailSender {
	
	fun sendEmail(to: String, subject: String, message: String) { ... }
	fun sendBlukEmail(toList: List<String>, subject: String, message: String) { ... }
}

/** [책임]: 직원 관리 서비스(몸통) **/
class EmployeeService(
	private val payroll: Payroll,
	private val employeeRepository: EmployeeRepository,
	private val emailSender: EmailSender
) {

	fun calculatePay(employee: EmployeeData): Int = payroll.calculatePay(employee)
	fun calculateTax(employee: EmployeeData): Int = payroll.calculateTax(employee)
	fun calculateBonus(employee: EmployeeData): Int = payroll.calculateBonus(employee)

	fun saveEmployee(employee: EmployeeData) {
		employeeRepository.saveToDatabase(employee)
	}
	fun deleteEmployee(employee: EmployeeData) {
		employeeRepository.deleteFromDatabase(employee)
	}
	fun findEmployeeByName(name: String): EmployeeData? = employeeRepository.findByName(name)

	fun sendEmailToEmployee(employee: EmployeeData, subject: String, message: String) {
		emailSender.sendEmail(employee, subject, message)
	}
	fun sendBulkEmail(toList: List<String>, subject: String, message: String) {
		emailSender.sendBulkEmail(toList, subject, message)
	}
}
```

### 마무리하며...
제가 SRP(Single Responsibility Principle)를 공부하면서 '**책임**이라는 개념의 범위는 어디까지인 것일까?', 'SRP를 따르기만 하면 다 좋은건가?' 그리고 'SRP를 왜 따라야하지?' 와 같은 생각이 머릿속에서 떠나지 않았습니다.
그리고 지속적으로 고민하고 샘플을 만들어보며 제가 일하는 곳에서 적용시키려 노력하면서 느낀점이 있었는데요. OOP - SOLID 원칙을 따르는 목적은 **표면적**으로는 "의존성을 낮춤으로서 결합도를 낮추고 재사용성을 높이고 에러/버그 발생 경우의 수를 줄임으로서 유지보수하기 쉽도록 한다."라고 한다면 **궁극적으로는 개발 비용을 줄이기 위함이지 않나 싶습니다.** 개발 비용이라고 한다면 개발에 소요되는 시간과 인력인데 무조건적으로 SRP를 지키기 위해서 코드베이스가 늘어나고 간단한 로직에 대해 불필요하게 클래스간의 관계가 깊어진다면... **주객전도** 되는 것이 아닌가 싶습니다.
그래서 OOP - SOLID 원칙을 따르는데는 개발자의 유연함과 관점 또한 중요한 요소인 것 같습니다.

부족한 글 읽어주셔서 감사합니다!
