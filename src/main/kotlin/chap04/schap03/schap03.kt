package chap04.schap03

fun main() {
    c3()
}

fun c3(){
    /*
    *   늦은 초기화 lateinit를 사용하여 초기화를 늦출 수 있다.
    *   단, 몇 가지 조건이 있다.
    *   1. 가변 프로퍼티인 var를 무조건으로 사용해야 한다.
    *   2. 널 타입이 아닌 타입이여야하고 Int나 Boolean 같은 원시 값을 표현하는 타입이 아니여야 한다.
    *   3. lateinit 프로퍼티를 정의하면서 초기화 식을 지정해 값을 바로 대입할 수 없다.
    * */


    /*
    *
    *  커스텀 접근자 사용하기
    *
    *   한 예로 커스텀 게터(getter)가 있다.
    *   아래 Personal 클래스에서 get()을 사용하여 fullName을 지정하는 것을 볼 수 있다.
    *   이는 자바에서 get(), set()과 같다.
    *   하지만 fullName은 val이며 변경할 수 없기 때문에 get()만 사용하고 set()은 사용할 수 없다.
    *
    *   다음 Personal의 age에는 var이므로 Set을 사용할 수 있다.
    * */
    val person = Personal("John", "Doe")
    println(person.fullName) // John Doe
    person.age = 20
    println(person.age) // 20


    /*
  *  지연 계산 프로퍼티와 위임
  *
  *   lazy 프로퍼티는 처음 읽을 때까지 그 값에 대한 계산을 미뤄두고 싶을 때 사용한다.
  *   주로 앞에 by를 붙여 사용한다.
  *
  *   예시
  *   val text : String by lazy { File("data.txt").readText() }
  * */
}

class Personal(val firstname : String , val familyName : String){
    val fullName: String
        get() {
            return "$firstname $familyName"
        }

    var age : Int? = null
        set(value) {
            //아래 if는 음수일 때 에러가 발생한다는 뜻
            if(value != null && value <= 0){
                throw IllegalArgumentException("Invalid age : $value")
            }
            // 양수이면 해당 값을 정의한다.
            // field 키워드를 통해 뒷받침하는 필트를 사용하지 않는 경우를 제외하면 항상 뒷받침하는 필드가 생긴다.
            field = value
        }

}

