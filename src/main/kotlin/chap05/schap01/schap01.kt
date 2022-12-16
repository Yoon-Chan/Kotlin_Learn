package chap05.schap01


fun main() {

    c1()
}

fun c1(){
    /*
    *   람다와 익명 함수
    *
    *   sum과 max 함수중 { } 부분이 람다식이라고 부른다.
    *   람다식의 파라미터 목록 : result, op이며
    *   람다식의 몸통(본문)이 식이 되는 식이나 문의 목록 : -> 이후의 식이다.
    *
    *   만약 인자가 없을 경우 ()으로 비울 수 있다.
    *   아래 함수 중 measureTime을 예로 들 수 있다.
    *
    *   함숫값을 만드는 다른 방법은 익명 함수를 사용하는 것이다.
    *   예시로
    *   fun sum(numbers : IntArray) =
    *       aggregate(numbers, fun(result, op) = result + op)
    *
    *   익명 함수의 문법은 일반 함수의 문법과 거의 똑같지만 몇 가지 차이점을 정리하면 다음과 같다.
    *
    *   익명 함수는 이름을 지정하지 않는다. fun 키워드 다음에 바로 파라미터 목록이 온다.
    *   파라미터 타입을 추론할 수 있으면 파라미터 타입을 지정하지 않아도 된다.
    *   익명 함수는 식이기 때문에 인자로 함수에 넘기거나 변수에 대입하는 등 일반 값처럼 쓸 수 있다.
    * */
    println(sum(intArrayOf(1,2,3))) // 6
    println(max(intArrayOf(1,2,3))) // 3
}

fun c2(){
    /*
    *   호출 가능 참조는 이미 함수 정의가 있고, 이 함수 정의를 함숫값처럼 고차 함수에 넘기고 싶을 때 사용한다.
    *   예제를 보면
    * */

    //아래에 ::isCapitalLetter가 호출 가능 참조이다.
    // ::isCapitalLetter는 isCapitalLetter() 함수와 같은 동작이다.
    //익명 함수에 원래 있는 함수를 호출하여 사용한 것이다.
    println( check("hello", ::isCapitalLetter) ) // false


    /*
    *   인라인 함수와 프로퍼티
    *
    *   코틀린은 함숫값을 사용할 때 발생하는 런타임 비용을 줄일 수 있는 해법을 제공한다.
    *   기본적인 아이디어는 함숫값을 사용하는 고차 함수를 호출하는 부분을 해당 함수의 본문으로 대체하는 인라인 기법을 쓰는 것이다.
    *
    *   inline함수를 쓰면 컴파일된 코드의 크기가 커지지만, 지혜롭게 사용하면 성능을 크게 높일 수 있다.
    *   inline 사용하면 객체를 새로 생성해서 하거나 함수를 또 호출하는 등 비효율적인 행동은 하지 않는데
    *   즉 inline 함수를 사용하면 main안에 즉, 람다를 호출하는 부분에 람다식 내부의 코드가 그대로 복사된다.(객체, 함수 생성 x)
    *
    *   noinline 키워드는 inline에서 제외시킬 때 사용한다.
    * */
    println(indexOf(intArrayOf(4,3,2,1)) { it < 3}) // 2 3보다 작은 부분이 2번째 인덱스에서 끊긴다.
}

//인덱스 위치를 찾아준다.
inline fun indexOf(numbers: IntArray, condition: (Int) -> Boolean) : Int {
    for(i in numbers.indices){
        if(condition(numbers[i])) return i
    }

    return -1
}

fun check(s : String, condition : (Char) -> Boolean) : Boolean {
    for(c in s){
        if (!condition(c)) return false
    }
    return true
}

fun isCapitalLetter(c : Char) = c.isUpperCase() && c.isLetter()

fun aggregate(
    numbers: IntArray,
    op : (resultSoFar : Int, nextValue : Int) -> Int
) : Int {
    var result = numbers.firstOrNull()
        ?: throw IllegalAccessException("Empty array")
    for(i in 1..numbers.lastIndex) result = op(result, numbers[i])
    return result
}

fun sum(numbers : IntArray)
    = aggregate(numbers) { result, op -> result + op }

fun max(numbers: IntArray)
    = aggregate(numbers) {result, op -> if (op > result) op else result }

fun measureTime(action : () -> Unit) : Long {
    val start = System.nanoTime()
    action()
    return System.nanoTime() - start
}