package chap06.schap03


fun main() {
    c3()
}

fun c3(){
    /*
    *   인라인 클래스(값 클래스)
    *
    *   실무에서는 래퍼(wrapper) 클래스를 만드는 일이 꽤 흔하다.
    *   무엇보다 래퍼 클래스는 잘 알려진 어댑터 디자인 패턴의 핵심이기도 하다.
    *
    *   인라인 클래스 정의하려면 value class를 붙이면 된다.
    *   1.3에서는 inline이라는 키워드를 사용했지만, 자바에 값 클래스가 생겼으므로 이에 맞춰 코틀린 1.5에서는
    *   value로 키워드가 변경됐다. 또한 JVM 백엔드를 사용하는 경우애는
    *   @JvmInline을 value class앞에 반드시 붙여줘야 한다.
    *
    *   인라인 클래스도 자체 함수와 프로퍼티를 포함할 수 있다.
    * */

    println(Dollar(5).add(Dollar(20)).amount) //25
    println(Dollar(-100).isDebt)    //true

}

//인라인 클래스의 주생성자에는 불변 프로퍼티를 하나만 선언해야 한다.
//@JvmInline
//value class Dollar(val amount : Int)    //amount의 단위는 센트
//
//@JvmInline
//value class Euro(val amount : Int)      //amount의 단위는 센트


//인라인 클래스에서 자체적으로 함수를 추가하거나 프로퍼티를 만들 수 있다.
@JvmInline
value class Dollar(val amount: Int){
    fun add(d: Dollar) = Dollar(amount + d.amount)
    val isDebt get() = amount < 0
}