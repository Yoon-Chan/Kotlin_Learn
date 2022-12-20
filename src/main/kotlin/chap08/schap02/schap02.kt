package chap08.schap02

fun main() {

}

fun c1(){
    /*
    *   추상 클래스와 인터페이스
    *
    *   자바와 마찬가지로 코틀린도 추상 클래스를 지원한다.
    *   추상클래스는 직접 인스턴스화할 수 없고 다른 클래스의 상위 클래스 역할만 할 수 있는 클래스를 뜻한다.
    *   추상 클래스로 만들려면 abstract라는 변경자 키워드를 붙여야 한다.
    *
    *   추상 클래스와 아닌 클래스의 차이는 추상 클래스의 생성자가 오직 하위 클래스의 생성자에
    *   위임 호출로만 호출될 수 있다는 점이다.(Person 클래스를 호출 할 때의 경우)
    *
    *   추상 클래스의 또 다른 특징은 추상 멤버를 정의할 수 있다는 것이다.
    *   추상 멤버는 타입, 파라미터, 반환 타입 등 함수나 프로퍼티의 기본적인 모습을 정의하지만 세부 구현은 생략한 멤버다.
    *
    *   추상 멤버 자체는 구현을 가질 수 없으므로 추상 멤버를 정의할 때는 몇 가지 제약이 있다.
    *       - 추상 프로퍼티를 초기화할 수 없고 명시적인 접근자나 by 절을 추가할 수 없다.
    *       - 추상 함수에는 본문이 없어야 한다.
    *       - 추상 프로퍼티와 함수 모두 명시적으로 반환 타입을 적어야 한다. 본문이나 초기화 코드가 없으므로 타입을 추론할 수 없기 때문이다.
    *
    *
    *
    *
    * */


}

fun c2(){
    /*
    *   인터페이스
    *
    *   코틀린 인터페이스 개념은 자바의 인터페이스와 상당히 비슷하다..
    *   근본적으로 인터페이스는 메서드나 프로퍼티를 포함하지만 자체적인 인스턴스 상태나 생성자를 만들 수 없는 타입이다.
    *   클래스와 달리 인터페이스 정의는 interface란느 키워드로 시작한다.
    *   인터페이스 멤버는 디폴트가 추상 멤퍼이다.
    *
    *   인터페이스는 클래스나 다른 인터페이스의 상위 타입이 될 수 있다.
    *   클래스가 인터페이스를 상속한 클래스에 있는 인터페이스 멤버를 상속해 구현할 때도 override 키워드를 추가해야 한다.
    *
    *
    *   코틀린 인터페이스도 다중 상속을 지원한다.
    *
    *   만약 두 인터페이스에서 시그니처를 가지는 멤버가 들어있는 다른 인터페이스르 둘 이상 상속할 때 흥미로운 문제가 생긴다.
    *   (이 뜻은 두 인터페이스가 같은 함수이름을 가졌을 때 경우)
    *   이 경우 이런 멤버들이 한 멤버로 합쳐지고 하위 타입은 이를 상속하는 것 같은 효과가 일어난다.
    *   이런 식으로 합쳐지는 멤버에 대한 구현이 둘 이상의 상위 타입에 존재하는 경우, super 호출 자체가 모호해 진다.
    *   이럴 때는 super를 상위 타입으로 한정시킨 키워드를 사용해야 한다.
    *
    *
    * */


}

//모호함 문제 해결방법
interface car2{
    fun move(){
        println("I'm riding")
    }
}

interface ship2{
    fun move(){
        println("I'm riding")
    }
}

class Amphibia : car2, ship2{
    override fun move() {
        super<car2>.move()
        super<ship2>.move()
    }
}

//다중 인터페이스 예제
interface Car{
    fun ride()
}

interface Aircraft{
    fun fly()
}

interface Ship{
    fun sail()
}

interface FlyingCar : Car,Aircraft

class Transformer : FlyingCar,Ship {
    override fun fly() {
        println("I'm Flying")
    }

    override fun ride() {
        println("I'm Riding")
    }

    override fun sail() {
        println("I'm Sailing")
    }

}


//추상 클래스
abstract class Entity(val name : String)

//Ok
class Person(name : String, val age : Int) : Entity(name)

//error
//val entity = Entity("Unknown")

//인터페이스 정의
interface Vehicle{
    val currentSpeed : Int
    fun move()
    fun stop()
}