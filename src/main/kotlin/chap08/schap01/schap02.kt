package chap08.schap01

fun main() {
    c2()
}

fun c2(){
    /*
    *   하위 클래스 초기화
    *   초기화 순서를 보여주는 예제를 보자
    *
    *   super 키워드는 부생성자가 상위 클래스의 생성자를 위임 호출한다는 사실을 컴파일러에게 알려준다.
    *
    *   하위 클래스 초기화 문제가 발생하는 것이 있다.
    *   한 문제로 this 누출 문제를 들 수 있다.
    *   this 누출은 상위 클래스가 현재의 인스턴스를 코드에 누출하는데, 현재 인스턴스는 일반적으로 아직 초기화되지 앟은 인스턴스의 상태에 의존할 수 있기 때문이다.
    *   this 누출 문제는 코틀린의 널이 될 수 없는 타입의 변수가 값이 널이 될 수도 있는 아주 드문 경우이다.
    *
    *
    *
    * */

    Trunk()
    /*
        가장 최상위 클래스부터 출력이 되는 것을 볼 수 있다.
    *   Initializing Vehicle
        Initializing Car2
        Initializing Trunk
    * */
}

open class Vehicle{
    init {
        println("Initializing Vehicle")
    }

}

open class Car2 : Vehicle(){
    init {
        println("Initializing Car2")
    }
}

class Trunk : Car2(){
    init {
        println("Initializing Trunk")
    }
}

