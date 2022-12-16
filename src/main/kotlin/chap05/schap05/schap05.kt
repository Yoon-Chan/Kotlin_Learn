package chap05.schap05

fun main() {
    c5()
}


fun c5(){
    /*
    *
    *   영역 함수
    *   코틀린 표준 라이브러리에는 어떤 식을 계산한 값을 문맥 내부에서 임시로 사용할 수 있도록
    *   해주는 몇 가지 함수가 들어있다.
    *
    *   영역 함수가 기본적으로 하는 일은 인자로 제공한 람다를 간단하게 실행해주는 것이다.
    *   몇 가지 관점의 조합이 있다.
    *
    *   1. 문맥 식을 계산한 값을 영역 함수로 전달할 때 수신 객체로 전달하는가, 일반적인 함수 인자로 전달하는가
    *   2. 영역 함수의 람다 파라미터가 수신 객체 지정 람다인가 아닌가
    *   3. 영역 함수가 반환하는 값이 람다의 결괏값인가, 컨텍스트 식을 계산한 값인가.?
    *
    *   전체적으로는 run, let, with, apply, also라는 다섯 가지 표준 영역 함수가 있다.
    *   모든 영역 함수는 인라인 함수이기 때문에 런타임 부가 비용이 없다.
    *
    *   영역 함수는 조심히 사용해야 하며, 남용하면 오히려 코드 가독성이 나빠지고 실수하기도 쉬워진다는 점을 명심하자!!
    * */

    /*
    *    run과 with 함수
    *
    *   run() 함수는 확장 람다를 받는 확장 함수이며 람다의 결과를 돌려준다.
    *   기본적인 사용 패턴은 객체 상태를 설정한 다음, 이 객체를 대상으로 어떤 결과를 만들어내는 람다를 호출하는 것이다.
    * */

    val isReceived = Address().run {
        //Address 인스터스를 this로 사용할 수 있다.
        zipCode = 123456
        city = "London"
        street = "Baker Street"
        house = "221b"
        post("Hello!") //반환값
    }

    Address().run {
        //Address 인스터스를 this로 사용할 수 있다.
        zipCode = 123456
        city = "London"
        street = "Baker Street"
        house = "221b"
        showCityAddress() //클래스 안에 함수 호출도 가능.
    }

    //만약 OK가 아닌 다른 값을 넣으면 메세지를 보낸다.
    if(!isReceived){
        println("Message is not delivered")
    }

    // 문맥이 없는 run을 사용할 수 있다.
    val address = run {
        val city = readlnOrNull() ?: return
        val street = readlnOrNull() ?: return
        val house = readlnOrNull() ?: return
        Address(city, street, house)
    }
    println(address.showCityAddress())


    /*
    *   with() 함수는 run()과 상당히 비슷하다.
    *   유일한 차이는 with()가 확장 함수 타입이 아니므로 문맥 식을 with의 첫 번째 인자로 전달해야 한다는 점 뿐이다.
    *   아래와 같이 Address사용하면 해당 중괄호 내에 있는 this는 Address객체로 사용할 수 있다.
    * */
    val message = with(Address("London", "Baker Street", "221b")){
        "Address : $city, $street, $house"
    }
    println(message)

}

fun Address.showCityAddress() = println("$street, $house")

class Address constructor(var city: String = "", var street : String = "", var house : String = ""){
    var zipCode : Int = 0

    fun post(message : String) : Boolean {
        println("Message for {$zipCode, $city, $street, $house} : $message")
        return readlnOrNull() == "OK"
    }
}


fun c6(){
    /*
    *   let 함수
    *   let 함수는 run과 비슷하지만 확장 함수 타입의 람다를 받지 않고 인자가 하나뿐인 함수 타입의 람다를 받는다는 점이 다르다.
    *
    *   let의 일반적인 사용법 중에는 널이 될 수 있는 값을 안전성 검사를 거쳐서 널이 될 수 없는 함수에 전달하는 용법이 있다.
    * */

    Address("London", "Baker Street", "221b").let {
        //let은 중괄호 안에 it을 이용하여 사용할 수 있다.
        println("To city : ${it.city}")
        it.post("Hello")
    }

    Address("London", "Baker Street", "221b").let {addr ->
        //let은 중괄호 안에 it 대신 addr이라는 자기가 정한 변수를 사용할 수 있다.
        println("To city : ${addr.city}")
        addr.post("Hello")
    }


    /*
    *
    *   apply / also 함수
    *
    *   apply() 함수는 확장 람다를 받는 확장 함수이며 자신의 수신 객체를 반환한다.
    *   also() 함수는 apply() 함수와 달리 인자가 하나 있는 람다를 파라미터로 받는다.
    *
    *
    * */
    val message = "Hello"

    Address().apply {
        city = "London"
        street = "Baker Street"
        house = "221b"
    }.post(message)

    Address().also {
        //also는 apply와 달리 it을 사용한다.
        //let과 같이 it을 다른 변수로 바꿔서 사용할 수 있다.
        it.city = "London"
        it.street = "Baker Street"
        it.house = "221b"
    }.post(message)

}