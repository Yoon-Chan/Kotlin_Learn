package chap03.schap03


fun main() {
    c2()
}

fun c2(){
    // 범위, 진행, 연산

    val chars = 'a'..'h'// a 부터 h
    val twoDigits = 10 .. 99 // 10부터 99까지 모든 수
    val zero2One = 0.0 .. 1.0 // 0.0 부터 1까지의 모든 부동소수점 수

    //in 연산을 사용하면 어떤 값이 범위 안에 들어있는지 알 수 있다.

    val num = readLine()!!.toInt()
    println(num in 10..99) // num이 안에 있으면 true, 없으면 false
    println("def" in "abc" .. "xyz") //true
    println("zzz" in "abc" .. "xyz") //false

    val twoDigits2 = 10 until 100 // 10 부터 99와 같다.

    println(5 in 5..5) //true
    println(5 in 5 until 5)// false 5가 포함이 안된다.
    println(5 in 10..1) //false 10부터 1이 아니라 연산이 제대로 작동이 안된다.

    //10부터 1을 의미하는 것은 10 downTo 1을 사용한다.
    println(5 in 10 downTo 1) //true
    println(5 in 1 downTo 10) //false 진행이 안되는 식
}

fun c3(){
    // when문 중요!!
    // 코틀린은 여러 대안 중 하나를 선택할 수 있는 더 간결한 대안이 if문이 아닌 when을 제공한다.
    /*
    *
    * 예시 fun hexDigit(n: Int) : Char {
    *   when {
    *       n in 0..9 -> return '0' + n
    *       n in 10..15 -> return 'A' + n -10
    *       else -> return '?'
    *   }
    * }
    *
    *  when에 인자 한개를 넣어 비교를 할 수 있다.
    *
    *  fun numberDescription(n : Int, max : Int = 100) : String = when(n){
    *   0 -> "Zero"
    *   1,2,3 -> "Small"
    *   in 4..9 -> "Medium"
    *   in 10..max -> "Large"
    *   !in Int.MIN_VALUE until 0 -> "Negative"
    *   else -> "Huge"
    *  }
    * */



}