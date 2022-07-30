`` String.format("%s", DecimalFormat("#,###").format(item.purchaseCost)) `` 
# DecimalFormat

 - **DecimalForamt** 클래스 **NumberFormat**의 구체화된 서브 클래스로 십진수 형식을 지정할 수 있다.
 - 숫자 구문을 분석하거나 포맷팅 할 수 있는 기능을 갖는다.
 - `정수(123)`, `고정 소수점 숫자(123.4)`, `과학적 표기법(1.23E4)`, `백문율(12%)`, `통화 금액($123)` 등을 포함한 다양한 숫자 종류를 지원한다.
 

### [[JAVA] 10진수 형식 클래스(DECIMALFORMAT) - 세자리마다 쉼표, 소수점, 지수 나타내기](https://reakwon.tistory.com/156)

### [Java DecimalFormat Pattern](https://jenkov.com/tutorials/java-internationalization/decimalformat.html)
|sysntax|description|
|--|--|
|0|	A digit - always displayed, even if number has less digits (then 0 is displayed)|
|#	|A digit, leading zeroes are omitted.|
|.|	Marks decimal separator|
|,	|Marks grouping separator (e.g. thousand separator)|
|E|	Marks separation of mantissa and exponent for exponential formats.|
|;	|Separates formats|
|-|	Marks the negative number prefix|
|%|	Multiplies by 100 and shows number as percentage|
|?|	Multiplies by 1000 and shows number as per mille|
|¤|	Currency sign - replaced by the currency sign for the Locale. Also makes formatting use the monetary decimal separator instead of normal decimal separator. ¤¤ makes formatting use international monetary symbols.|
|X|	Marks a character to be used in number prefix or suffix|
|'|	Marks a quote around special characters in prefix or suffix of formatted number.|
