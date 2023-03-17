
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
