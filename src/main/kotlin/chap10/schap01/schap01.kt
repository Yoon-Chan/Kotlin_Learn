package chap10.schap01


fun main() {
    c1()
}

fun c1(){
    /*
    *   애너테이션
    *
    *       - 애너테이션은 커스텀 메타데이터를 정의하고 이 메타데이터를 소스코드상의 선언, 식, 전체파일 등의 요소에 엮는 방법을 제공한다.
    *       - 자바 애너테이션은 인터페이스로 구성되지만, 코틀린 애너테이션은 특별한 종류의 클래스로 구성된다.
    *       - 일반 클래스와 달리 애너테이션 클래스에는 멤버나 부생성자, 초기화 코드가 없다.
    *         하지만 1.3부터는 내포된 클래스, 인터페이스, 객체(동반객체 포함)를 애너테이션 본문에 넣을 수 있다.
    *
    *       - 애너테이션 커스텀 애트리뷰트를 추가하고 싶다면 생성자 파라미터를 통해야만 한다.
    *
    *
    *   애너테이션 클래스에는 상위 타입을 명시할 수도, 애너테이션 크래스를 상속하는 클래스를 정의할 수도 없다.
    *
    *   애너테이션 파라미터로 사용할 수 있는 타입의 종류는 아래와 같다.
    *       - Int, Boolean, Double 등 원시 타입
    *       - String
    *       - 이넘
    *       - 다른 애너테이션
    *       - 클래스 리터럴
    *       - 위에 나열한 타입들로 이뤄진 배열
    * */

    //코틀린 애너테이션은 클래스의 일종이기는 하지만, 일반적인 클래스와 마찬가지 방식으로 클래스의 인스턴스를 만들 수 없다.
    /*
    *   @구문을 사용해야만 애너테이션 인스턴스를 생성할 수 있다.
    *   실제 애너테이션 인스턴스를 얻기 위해서는 리플레션 API를 사용할 수 있다.
    * */
    val ioComponent = Component("IO")

}

//애너테이션 클래스 생성
//애너테이션 파라미터를 항상 val로 선언해야 한다.
annotation class MyAnnotation(val text2 : String){
    companion object {
        val text: String
            get() = ""
    }
}

annotation class Component(val name :String = "Core")

//커스텀 애트리뷰트 추가 방법
@MyAnnotation("Some useful info") fun annotatedFun() {}

/*
*   애너테이션 이름 앞에 붙으며 : 으로 애너테이션 이름과 구분된다.
*       - property : 프로퍼티 자체를 대상으로 한다.
*       - field : 뒷받침하는 필드를 대상으로 한다
*       - get : 프로퍼티 게터를 대상으로 한다.
*       - set : 프로퍼티 세터를 대상으로 한다.
*       - param : 생성자 파라미터를 대상으로 한다.
*       - setparam : 프로퍼티 세터의 파라미터를 대상으로 한다.
*       - delegate : 위임 객체를 저장하는 필드를 대상으로 한다.
* */
class Person(@get:Component val name : String)
//[]구문으로 묶을 수 있다.
// class Person(@get : [A B] val name :String)

// receiver라는 대상을 사용하면 확장 함수나 프로퍼티의 수신 ㄱ개체에 애너테이션을 붙일 수 있다.
// fun @receiver: A Person.fullName() = "$firstName $familyName"

// file이라는 대상을 사용해 전체 파일에 대해 애너테이션을 붙일 수 있다. 다른 패키지 임포티나 패키지 지시자보다
// 더 앞인 파일의 시작 부분에 이런 애너테이션을 붙여야 한다.
// @file:JvmName("MyClss")

