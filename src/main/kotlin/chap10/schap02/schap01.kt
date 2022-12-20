package chap10.schap02

import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

fun main() {
    c1()
}


fun c1(){
    /*  리플렉션
        리플렉션 API는 클래스, 함수, 프로퍼티의 런타임 표현에 접근할 수 있게 해주는 탑입, 함수, 프로퍼티 모음이다.
        작성한 프로그램이 컴파일 시점에 알 수 없는 클래스를 다뤄야 하는데,
        어떤 정해진 공통의 계약을 준수해야만 하는 경우에 리플렉션이 유용하다.

        모든 리플렉션 타입은 KAnnotatedElement의 자손이다.
        KAnnotatedElement는 함수, 프로퍼티, 클래스 등 구체적인 언어 요소에 정의된 애너테이션에 접근하는 기능을 제공한다.
    * */

    //Main 클래스와 연관된 애너테이션을 가져오고 싶을 때
    val component = Main::class.annotations
        .filterIsInstance<Component>()
        .firstOrNull() ?: return

    println("Component name: ${component.name}")

    val depText = component.dependency.compnetClasses
        .joinToString { it.simpleName ?: "" }

    println("Dependencies: $depText")
    /*출력물 결과
    *   Component name: Core
        Dependencies: IO, Logger
    * */


    /*
    *   지정자와 타입
    *
    *   코틀린 리플렉션에서 지정자는 타입을 정의하는 선언을 뜻한다.
    *   이런 선언은 KClassifier 인터페이스에 의해 표현되며, 이 인터페이스에는 두 가지 구체적인 변종이 있다.
    *
    *   KClass<T>는 컴파일 시점에 T 타입인 클래스나 인터페이스, 객체 선언을 런타임에 표현한다.
    *   KTypeParameter는 어떤 제네릭 선언의 타입 파라미터를 표현한다.
    *
    *   KClass 인스턴스를 얻는 방법은 두 가지이다. 첫 번째는 애너테이션을 설명할 때 이야기한 클래스 리터럴 구문을 사용하는 방법이다.
    *   다른 방법은 kotlin 확장 프로퍼티를 사용해 java.lang.Class의 인스턴스를 KClass로 변환하는 것이다.
    *   전체 이름을 갖고 클래스를 동적으로 찾을 때 이런 방법이 유용하다.
    *
    *
    * */

    //클래스 리터럴 구문을 사용하는 방법
    println(String::class.isFinal)  //true

    //::class 구문을 사용하면 임의의 식의 결괏값에 대한 런타임 클래스를 얻을 수 있다.
    println((1+2)::class)//class kotlin.Int
    println("abc"::class)//class kotlin.String

    val stringClass = Class.forName("java.lang.String").kotlin
    println(stringClass.isInstance("Hello"))    //true
    println(String::class.java) //class java.lang.String

    /*
    *   KClass API
    *
    *   val isAbstract : Boolean
    *   val isCompanion : Bolean
    *   val isData  : Boolean
    *   val isFinal : Boolean
    *   val isInner : Boolean
    *   val isOpen : Boolean
    *   val isSealed : Boolean
    *
    *   visibility라는 프로퍼티는 KVisibility 이넘으로 클래스 선언의 가시성 수준을 돌려준다.
    *   enum class Kvisibility {
    *       PUBLIC,
    *       PROTECTED,
    *       INTERNAL,
    *       PRIVATE
    *   }
    *
    *
    *   소스코드에서 사용되는 간단한 이름을 반홤/ 클래스 전체 이름을 얻는 방법
    *   val simpleName : String?
    *   val qualifiedName : String?
    * */

    println(Any::class.qualifiedName)   //kotlin.Any
    println(Any::class.jvmName)         //java.lang.Object

    /*
    *   isInstance() 함수는 주어진 객체가 이 함수의 수신 객체가 표현하는 클래스의 인스턴스인지 알려준다.
    *
    * */

    println(String::class.isInstance(""))       //true
    println(String::class.isInstance(12))       //false
    println(String::class.isInstance(null))     //true

    /*
    *   KClass 프로퍼티들은 멤버 선언에 접근할 수 있게 해준다.
    *       - constructors : 주생성자와 부생성자들을 KFunction 타입의 인스턴스로 돌려준다.
    *       - members : KCallable 인스턴스로 표현되는 멤버 함수와 프로퍼티 표현의 컬렉션을 돌려준다.
    *       - nestedClasses : 내포된 클래스와 객체들로 이뤄진 컬렉션이다. 동반 객체도 포함된다.
    *       - typeParameters : KTypeParameter에 의해 표현되는 타입 파라미터로 이뤄진 리스트
    * */



    val personClass = Class.forName("chap10.schap02.Person").kotlin
    val person = personClass.constructors.first().call("John", "Doe")
    val fullNameFun = personClass.members.first { it.name == "fullname" }


    println(fullNameFun.call(person, false))    //John Doe


    //supertypes 프로퍼티를 통해 얻을 수 있는 KType 인스턴스의 리스트를 들 수 있다.
    //supertypes 프로퍼티는 클래스가 직접 상속한 상위 타입만 돌려준다.
    println(Child::class.supertypes) // [chap10.schap02.Parent, chap10.schap02.IParent]

    /*  KTypeParameter의 프로퍼티 네개
            - val isReified : Boolean
            - val name : String
            - val upperBounds : List<KType>
            - val variance : KVariance
     */

    //upeerBounds는 KClass의 supertypes 프로퍼티와 비슷하게 상위 바운드 타입으로 이뤄진 리스트를 돌려준다.
    val parameters = MyMap::class.typeParameters
    println(parameters.joinToString { "${it.name} : ${it.upperBounds}" })   // K : [kotlin.Any], V : [kotlin.Any?]


    /*
    *   코틀린 타입은 다음과 같은 세 가지 성격을 지닌다.
    *
    *       - isMarkedNullable 프로퍼티가 제공하는 널 가능성. 예를 들면 이를 통해 List<String>과
    *         List<String>?를 구분할 수 있다.
    *       - classifier 프로퍼티를 통해 제공하는 지정자. 지정자는 타입을 정의하는 클래스, 인터페이스나 객체 선언을 가리킨다.
    *       - 타입 프로퍼티에 전달된 실제 타입 인자 목록.
    * */



    /*
    *   호출 기능
    *
    *   KCallable이 제공하는 멤버들을 보면
    *       - val isAbstract : Boolean
    *       - val isFinal : Boolean
    *       - val isOpen : Boolean
    *       - val isSuspend : Boolean
    *       - val visibility : KVisibility?
    *
    *   다음은 프로퍼티나 함수의 시그니처를 표현하는 프로퍼티가 속한 그룹이다.
    *       - val name : String
    *       - val typeParameters : List<KTypeParameter>
            - val parameters : List<KParameter>
            - val returnType : KType
    *
    *
    *   KParameter 인터페이스는 멤버 및 확장 선언의 수신 객체나 함수/생서나의 파라미터에 대한 정보를 포함한다.
    *       - val index : Int
    *       - val isOptional : Boolean
    *       - val isVararg : Boolean
    *       - val name : String?
    *       - val type : KType
    *       - val kind : KParameter
    *
    *   isOptional 프로퍼티는 파라미터에 디폴트 값이 있는지 여부를 돌려준다.
    *   kind 프로퍼티는 KParameter 인스턴스가 일반적인 값에 해당하는지,
    *   아니면 디스패치나 확장의 수신 객체인지를 알려준다.
    *   이 프로퍼티는 KParameter.Kind  이넘에 정의된 상수 중 하나를 반환한다.
    *       - INSTANCE : 멤버 선언의 디스패치 수신 객체
    *       - EXTENSION_RECEIVER :  확장 선언의 확장 수신 객체
    *       - VALUE : 일반적인 값
    *
    *
    */

    /*
    *   KFunction은 함수나 생성자를 표현한다. 이 인터페이스에 추가된 멤버는 모두 함수에 적용 가능한 변경자 검사를 위한 프로퍼티뿐이다.
    *       - val isInfix : Boolean
    *       - val isInline : Boolean
    *       - val isOperator : Boolean
    *       - val isSuspend : Boolean
    * */



    println(::combine.returnType)   // kotlin.String


}

fun combine(n : Int, s : String) = "$s$n"

interface MyMap<K: Any, out V>

open class GrandParent
open class Parent : GrandParent()
interface IParent
class Child : Parent(), IParent

class Person(val firstName : String, val familyName : String){
    fun fullname(familyFirst: Boolean) : String = if(familyFirst){
        "$familyName $firstName"
    } else {
        "$firstName $familyName"
    }
}

annotation class Dependency(vararg val compnetClasses : KClass<*>)

annotation class Component(
    val name : String = "Core",
    val dependency : Dependency = Dependency()
)

@Component("I/O")
class IO

@Component("Log", Dependency(IO::class))
class Logger

@Component(dependency = Dependency(IO::class, Logger::class))
class Main