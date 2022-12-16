package chap02.schap03

fun main() {
    c2()
}


fun c2(){
    /*
    *  문자열 관련 함수
    *
    *  isEmpty, isNotEmpty  문자열이 비어있는지 검사
    *  substring 부분 문자열 출력
    *  startWith,endsWith 접두사나 접미사인지 검사한다.
    *  indexOf 인자로 받은 문자나 문자열이 수신 객체인 문자열에 나타내는 첫 번째 인덱스를 반환한다.
    * */

    println("Hello".isEmpty()) //false
    println("".isEmpty()) //true
    println("Hello".isNotEmpty()) //true
    println("Hello".substring(2)) // "llo"
    println("Hello".substring(1,3)) //"el"
    println("Hello".startsWith("Hel")) //true
    println("Hello".endsWith("lo")) //true
}