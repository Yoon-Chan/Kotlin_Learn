package chap04.schap04


fun main() {
    c4()
}

fun c4(){
    /*
    *   동반 객체
    *
    *   object는 코틀린에서는 클래스를 정의하자마자 생성자와 속성을 정의할 수 있다.
    *   해당 키워드를 사용하면 별다른 정의 없이 싱글톤 구현을 지원해준다.
    *
    *   코틀린에는 기본적으로 static이 없다.
    *   대신 companion object를 통해 package수준의 최상위 함수와 객체선언을 사용할 수 있다.
    *
    *   companion 객체에는 이름을 지정할 수 있다.
    *   예시로
    *   companion object B { ... }
    *
    *   클래스 내에는 companion object를 오직 딱 하나만 설정할 수 있다.
    *   companion object는 static클래스 변수가 아니라 object 객체이다.
    *   static하게 동작하기만 할 뿐, 사실은 객체이다.
    *
    * */

    //오류 주 생성자는 private 접근제한자로 같은 클래스 내에서만 생성 가능하기 때문에 오류가 발생
    //var a = A("Test")

    //허나 companion object안에 설정된 bar()함수를 통해 클래스 A를 접근할 수 있다.
    var b = A.bar()

    println(MyClass2.Companion.prop)
    println(MyClass2.Companion.method())

    val comp1 = MyClass2.Companion // companion object객체라서 변수할당가능
    println(comp1.prop) //멤버 접근
    println(comp1.method())

    val comp2 = MyClass2
    println(comp2.prop) //멤버 접근
    println(comp2.method())

}

class A private constructor(val name : String){
    companion object{
        fun bar() : A{
            return A("zero")
        }
    }
}

class MyClass2{
    companion object {
        val prop = "나는 Companion object의 속성이다"
        fun method() = "나는 Companion object의 메소드다"
    }
}