package chap10.schap01


fun main() {

}

fun c2(){

    /*
    *   내장 애너테이션
    *       - 코틀린은 몇 가지 내장 애너테이션을 제공한다. 이들은 컴파일러 수준에서 특별한 의미를 가진다.
    *       - 이런 애너테이션 중 상당수는 자바 언어의 메타 애너테이션과 비슷한 역할을 한다.
    * */

    /*
    *   @Retention 애너테이션은 애너테이션이 저장되고 유지되는 방식을 제어한다.
    *   AnnotationRetention 이넘 클래스에 적용된 세 가지 중 한 가지 저장 방식을 지정할 수 있다.
    *       - SOURCE : 컴파일 시점에만 존재하며 컴파일러의 바이너리 출력에는 저장되지 않는다.
    *       - BINARY : 컴파일러의 바이너리 출력에 저장되지만, 런타임에 리플렉션 API로 관잘할 수는 없다.
    *       - RUNTIME : 컴파일러의 바이너리 출력에 저장되며 런타임 리플레션 API를 통해 관찰할 수도 있다.
    *
    *
    * */

    /*  @Repeateable이 붙은 애너테이션을 같은 언어 요소에 두 번 이상 반복 적용할 수 있다.
        기본 애너테이션은 반복 적용할 수 없다.
    *
    * */

    /*  @MustBeDocumented는 애너테이션을 문서에 꼭 포함시키라는 뜻이다.

    * */

    /*  @Target은 애너테이션을 어떤 언어 요소에 붙일 수 있는지 지정한다.
        AnnotationTarget 이넘에 정의된 다음 상수들을 vararg로 지정하면 된다.
            - CLASS : 클래스, 인터페이스, 객체에 붙일 수 있다.(애너테이션 클래스 포함)
            - ANNOTATION_CLASS : 애너테이션 클래스에 붙일 수 있다.
            - TYPEALIAS : 타입 별명 정의에 붙일 수 있다
            - PROPERTY : 주 생성자에 정의된 val/var 프로퍼티를 포함해, 프로퍼티에 붙일 수 있다.(지역 변수x)
            - FIELD : 프로퍼티를 뒷받침하는 필드에 붙일 수 있다.
            - LOCAL_VARIABLE : 지역 변수에 붙일 수 있다.
            - VALUE_PARAMETER : 생성자, 함수, 프로퍼티 세터의 파라미터에 붙일 수 있다.
            - CONSTRUCTOR : 주생성자나 부생성자에 붙일 수 있다.
            - FUNCTION : 람다나 익명함수를 포함해, 함수에 붙일 수 있다.
            - PROPERTY_GETTER/PROPERTY_SETTER : 프로퍼티 게터/ 프로퍼티 세터에 붙일 수 있다.
            - FILE : 파일에 붙일 수 있다.
            - TYPE : 타입 지정에 붙일 수 있다.
            - EXPRESSION : 식에 붙일 수 있다
    * */

    /*  기타 애너테이션

        @StrictFp : 부동소수점 연산의 정밀도를 제한해서 여러 다른 플랫폼 간의 부동소수점 연산 이식성을 높여준다.
        @Synchronized : 애너테이션이 붙은 함수나 프로퍼티 접근자의 본문에 진입하기 전에 모니터를 획득하고
                        복문 수행 후 모니터를 해제하게 한다.
        @Volatile : 애너테이션이 붙은 뒷받침하는 필드를 변경한 내용을 즉시 다른 스레드에 관찰할 수 있게 한다.
        @Transient : 애너테이션이 붙은 필드를 직렬화 메커니즘이 무신한다.
    *
    * */

    /*
    *   @Suppress 애너테이션을 사용하면 지정한 이름의 컴파일러 경고를 표시하지 않게 할 수 있다.
    *   이 애너테이션은 식이나 파일을 포함하는 모든 대상에 붙일 수 있다.
    * */

    /*
    *   @Deprecated는 어떤 선언을 사용 금지 예정이라고 선언하면, 이 선언을 사용하지 않는 것을 클라이언트 코드에게 권장한다.
    *   왜 이 선언이 사용 금지 예정인지 알려주고, 이 선언 대신 쓸 수 있는 대안을 알려주는 메시지를 추가하는 것이 일반적이다
    *
    *   @Deprecated(
    *       "Use readInt() instead" //메세지
    *       ReplaceWith("readInt()")    //대안
    *   )
    *
    *   위에서 ReplaceWith또한 애너테이션이라는 점을 유의하자.
    *   @Depreacted를 사용하면서 그 안에 ReplaceWith를 지정할 수 있다.
    *   하지만 @ReplaceWith를 단독으로 사용할 수는 없다.
    * */
}

//Retention을 사용하지 않으면 Kotlin: Expression annotations with retention other than SOURCE are prohibited 에러가 발생
@Target(AnnotationTarget.EXPRESSION)
@Retention(AnnotationRetention.SOURCE)
annotation class NeedToRefactor

//@Repeatable
//반복할 수 잇는 애너테이션의 유지 시점을 반드시 SOURCE로 명시해야 한다.
@Repeatable
@Retention(AnnotationRetention.SOURCE)
annotation class Author(val name : String)

//2번 적용 예제
@Author("John")
@Author("Harry")
class Service
