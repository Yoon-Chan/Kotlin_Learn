package chap06.schap01


fun main() {
    c1()
}

fun c1(){

    /*
    *   이넘 클래스는 미리 정의된 상수들로 이뤄진 제한된 집합을 표현하는 특별한 클래스이다.
    *
    *   이넘 클래스 when 에서 모든 상수를 사용할 경우 else를 사용할 필요가 없다.
    *
    *   이넘 클래스에는 공통 멤버가 있다.
    *   ordinal과 name이라는 한 쌍의 프로퍼티가 들어있다.
    *   ordinal은 이넘 클래스 안에 정의된 이넘 값의 순서에 따른 인덱스고,
    *   name은 이넘 값의 이름이다.
    *
    *   valueOf() 메서드도 있다.
    *   valueOf() 메서드는 이넘 값의 이름을 문자열로 넘기면 그에 해당하는 이넘 값을 돌려주거나 이름이 잘못된 경우
    *   예외를 던진다.
    *
    *   코틀린 1.1 부터는 values()나 valueOf() 대신에  제네릭 최상위 메서드인 enumValues()와 enumValueOf()를 사용할 수도 있따.
    * */


    //이넘 클래스에서 함수를 만들어 사용할 수 있다.
    fun WeekDay.isWorkDay() =
            this == WeekDay.SATURDAY || this == WeekDay.SUNDAY

    println(WeekDay.MONDAY.isWorkDay())     // false
    println(WeekDay.SATURDAY.isWorkDay())   // true


    println(RainbowColor.BLUE.isCold)   //true
    println(RainbowColor.RED.isWarm)    //true
}

//이넘 클래스이다.
enum class WeekDay{
    MONDAY, TUESDAY, WENDESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY;

    val lowerCasename get() = name.lowercase()
}

//이넘 클래스 생성자가 있으면 각 이넘 상수의 정의 뒤에도 적절한 생성자 호출을 추가할 수 있다.
enum class RainbowColor(val isCold : Boolean){
    RED(false), ORANGE(false), YELLOW(false),
    GREEN(true), BLUE(true), INDIGO(true), VIOLET(true);

    val isWarm get() = !isCold
}