package chap04.schap02

fun main() {

}

fun c2(){
    // null 연산자

    /*
    *  코틀린에서 널연사자를 사용하려면 ?를 사용한다.
    *  예시 fun readInt() = readLine()?toInt()
    *
    *   이는 자바에서
    *   fun readInt() : Int? {
    *       val tmp = readLine()
    *       return if ( tmp != null) tmp.toInt() else null
    *   }
    *   와 같다.
    *
    *   ?을 사용하면 불필요한 if문을 줄일 수 있다.
    *
    *
    *
    *   엘비스 연산자  ?:은 연산자를 사용하면 널을 대신할 디폴트 값을 지정할 수 있다.
    *   예시로
    *   fun sayHello(name : String?) {
    *       println("Hello, " + (name ?: "Unknown))
    *   }
    *   이는 name이 null일 경우 null대신 Unknwon 문자열을 출력한다.
    * */

}