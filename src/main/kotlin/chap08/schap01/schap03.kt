package chap08.schap01

fun main() {

    c3()
}

fun c3(){
    /*
    *   타입 검사와 캐스팅
    *
    *   어떤 인스턴스가 더 구체적인 타입에 속하는지 검사하고 필요할 때 타입을 변환할 수 있는 방법이 있으면 편리하다.
    *   아래 objects 프로퍼티는 Any 타입이며
    *   for문을 사용했을 경우 오류가 발생한다.
    *
    *   코틀린은 타입 검사와 캐스팅 연산을 통해 이런 경우를 처리할 수 있는 해법을 제공한다.
    *   is 연산자는 왼쪽 피연산자가 오른쪽에 주어진 타입인 경우 true를 반환한다.
    *
    *   !is 연산자는 is연산자와 반대되는 개념이다.
    *
    *   컴파일러는 검사 시점과 사용 시점 사이에 변수가 변경되지 않는다고 확신할 수 있을 때만 스마트 캐스트를 허용한다.
    *   먼저 프로퍼티나 커스텀 접근자가 정의된 변수에 대해서는 스마트 캐스트를 쓸 수 없다.
    *
    *
    * */

    //해당 프로퍼티는 Any 타입이다.
    val objects = arrayOf("1", 2 , "3", 4)

    //오류 발생 Any타입이므로 컴파일 되지 않는다.
//    for(obj in objects){
//        println(obj * 2)
//    }

    //is 연산자 예제
    for(obj in objects){
        println(obj is Int)
    }
/*  위의 예제의 결과
    false
    true
    false
    true*/

    //null 값은 모든 널이 될 수 있는 타입의 인스턴스로 간주되지만, 모든 널이 될 수 없는 타입의 인스터스는 아닌 것으로 간주된다.
    println(null is Int)    // false
    println(null is String?)    //true

    val o : Any = 123
    println((o as Int) + 1)             //124
    println((o as? Int)!! + 1)          //124
    println((o as? String ?: "").length)    //0
    //println((o as String).length + 1)       // java.lang.ClassCastException

    // o as Sting?과 o as? String 식의 차이를 잘 구분하자
    // o 가 String? 타입의 값이라면 이 둣 식이  똑같은 값을 가질 수 있지만, o가 널이 될 수 없는 타입의 값이라면 그렇지 않다.
    println(o as? String)   //null
    //println(o as String?)   // java.long.ClassCastException


}

fun c4(){
    /*
    *   공통 메서드
    *   kotlin.Any 클래스는 코틀린 클래스 계층 구조의 루트다.
    *   즉, 다른 모든 클래스는 Any를 직간접적으로 상속한다.
    *   클래스를 정의하면서 상위 클래스를 명시하지 않으면 컴파일러가 자동으로 상위 클래스를 Any로 가정한다.
    *
    *   equals() 는 코틀린에서 == 와 같은 역할을 한다. (반대는 !=)
    *   equals() 구현의 일번적인 요구 사항은 기본적으로 자바와 같다
    *       - 널이 아닌 객체가 널과 같을 수 없다.
    *       - 동등성 연산은 반사적이여야 한다. 즉, 모든 객체는 자기 자신과 동등해야 한다.
    *       - 동등성 연산은 대칭적이어야 한다. a==b면 b==a여야 한다.
    *       - 동등성 연산은 추이적이어야 한다. 즉, a==b이고 b==c이면 a==c여야 한다.
    *
    *
    *
    * */

}

