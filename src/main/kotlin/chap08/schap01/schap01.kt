package chap08.schap01


fun main() {

}


fun c1(){
    /*
    *   상속
    *
    *   하위 클래스 선언
    *   주 생성자 뒤에 :을 넣고 상위 클래스가 될 클래스의 이름을 넣으면 된다.
    *
    *   자바와 달리 코틀린에서는 extends나 implements와 같은 특별한 키워드를 사용하지 않는다. 상속은 항상 :으로 표시
    *   코틀린에서 open 키워드는 클래스가 상속에 대해 열려 있다는 뜻이다. 즉, 해당 클래스를 해당 클래스가 상속에 대해 열려 있다는 뜻.
    *   아래에 Aircraft는 아무 변경자도 붙어있지 않으므로, 디폴트로 상속할 수 없는, 자바의 경우 final 클래스로 간주된다.
    *   따라서 이 클래스를 상속하면 오류가 발생한다.
    *
    *   데이터 클래스는 항상 final이며 open으로 선언할 수 없다.
    *   즉, 데이터 클래스는 상속이 불가능하다.
    *   하지만 이 제한은 코틀린 1.1부터 사라졌다.
    *
    *   인라인 클래스는 다른 클래스를 상속할 수도 없고, 다른 클래스의 상위 클래스 역할을 할 수도 없다.
    *
    *   객체(동반 객체 포함)는 자유롭게 열린 클래스를 사용할 수 있다.(예제 : 아래에 Person클래스)
    *   하지만 객체를 상속하거나 객체를 open으로 선언할 수는 없다.
    *
    *
    *   오버라이드하는 멤버를 final로 선언하면 더 이상 하위 클래스가 이 멤버를 오버라이드 할 수 없다.
    *   프로퍼티도 오버라이드할 수 있다.
    *   또한 불변 프로퍼티를 가변 프로퍼티로 오버라이드할 수 있다.
    *
    * */

    startAndStop(Car()) // I'm riding Stopped
    startAndStop(Boat())    // I'm sailing Stopped


}

fun startAndStop(vechicle: Vechicle){
    vechicle.start()
    vechicle.stop()
}

open class vechicle{
    var currentSpeed = 0

    fun start() = println("I'm moving")

    fun stop() = println("Stopped")
}

open class FlyingVehicle : vechicle() {
    fun takeOff() = println("Taking off")
    fun land() = println("Landed")
}

class Aircraft(val seats : Int) : FlyingVehicle(){

}

open class Person(val name : String, val age : Int){
    companion object : Person("Unkwon", 0)
}

object JohnDoe : Person("John Doe", 30)


open class Vechicle {
    //오버라이딩을 하기 위에 함수 앞에 open을 사용함
    open fun start() = println("I'm moving")

    fun stop() = println("Stopped")
}

class Car : Vechicle(){
    override fun start() = println("I'm rinding")
}


class Boat : Vechicle(){
    override fun start() = println("I'm sailing")
}

