package chap14.schap02


fun main() {

}


fun c1(){
    /*
    *   단언문 - chap13_2.kt 파일에서 예제 확인 가능
    *
    *   shoulBe가 아닌 커스텀 매처를 정의하려면 Matcher 인터페이스를 구현하고 이 인터페이스의 test() 메서드를 오버라이드 해야 한다.
    *   abstract fun test(value : T) : MatcherResult
    *   MatcherResult 객체는 매칭 결과를 표현한다. 이 클래스는 데이터 클래스로 다음과 같은 프로퍼티가 있다.
    *       - passed : 단언문을 만족하는지(true) 만족하지 않은지(false)를 나타냄
    *       - failureMessage : 단언문 실패를 보여주고 단언문을 성공시키려면 어떤 일을 해야 하는지 알려주는 메시지
    *       - negatedFailureMessage : 매처를 반전시킨 버전을 사용했는데 매처가 실패하는 경우 표시해야 하는 메시지
    *
    *   Matcher 인터페이스의 구현은 자동으로 and/or/invert 연산을 지원한다. 이 연산들은 불 연산 규칙에 따라 매처를 합성해준다.
    *   이런 연산을 활용해 복잡한 술어로 구성된 단언문을 구성할 수 있다.
    *
    *   매처가 지원하는 또 하나의 연산으로 compose()가 있다. 이 연산을 사용하면 기존 매처에 타입 변환 함수를 추가함으로써
    *   새로운 타입에 대한 매처를 만들어준다.
    *
    * */

}

fun c2(){
    /*
    *   1.인스펙터
    *
    *   매처 외에도 매처와 관련된 인스펙터라는 개념을 지원한다.
    *   인스펙터는 컬렉션 함수에 대한 확장 함수로, 주어진 단언문이 컬렉션 원소 중 어떤 그룹에 대해 성립하는지 검증할 수 있게 해준다.
    *       - forAll()/forNone() : 단언문을 모든 원소가 만족하는지(forAll()), 어느 원소도 만족하지 않는지(forNone()) 검사
    *       - forExactly(n) : 단언문을 정확히 n개의 원소가 만족하는지 검사한다. n=1인 경우에 특화된 forOne() 함수도 있다.
    *       - forAtLeast(n)/forAtMost(n) : 단언문을 최수 n개의 원소가 만족하는지(forAtLeast(n)), 최대 n개의 원소가 만족하는지(forAtMost(n)) 검사한다.
    *                                      n=1이라면 forAtLeastOne()/forAtMostOne()을 쓸수 있고, forAny()도 쓸 수 있다. forAny()는 forAtLeastOne()과 같다.
    *       - forSome() : 단언문을 만족하는 원소가 존재하되, 모든 원소가 단언문을 만족하지는 않음을 검사한다.
    *
    *
    *
    *   2.예외처리
    *
    *   코테스트는 어떤 코드가 특정 예외에 의해 중단됐는지 검사하는 shouldThrow() 단언문을 제공한다. 이 단언문은
    *   try/catch로 예외를 잡아내는 방식을 간편하게 대신할 수 있다.
    *
    *   코테스트에서는 assertSoftly 블록을 사용해 이런 기본 동작을 우회할 수 있다.
    *   이 블록은 내부에서 발생한 AssertionError 예외를 잡아낸 후 누적시키면서 모든 단언문을 실행한다.
    *   블록이 끝나면 assertSoftly는 누적시킨 모든 예외를 한 AssertionError에 넣고 호출한 쪽에 던진다.
    *
    *
    *   3. 비결정적 코드 테스트하기
    *
    *   여러 번 시도해야 테스트를 통과하곤 하는 비결정적 코드를 다뤄야 한다면 타임아웃과 반복 실행을 편리하게 처리할 수 있는
    *   eventually() 함수가 있다. 이 함수는 정해진 기간 안에 주어진 단언문을 만족시키는 경우가 한 번이라도 생기는지 검사한다.
    *   continually() 함수는 단언문이 최초 호출 시 성림하고 그 이후 지정한 기간 동안 계속 성림한 채로 남아있는지 검사한다.
    *
    *
    *   4. 속성 기반 테스트
    *
    *   코테스트는 술어를 지정하면 코테스트가 자동으로 술어를 검증하기 위한, 임의의 테스트 데이터를 생성해주는 속성 기반 테스트를 지원한다.
    *   이 기법은 수동으로 준비하고 유지하기 어려운 큰 데이터 집합에 대해 성립해야 하는 조건을 테스트해야 하는 경우 유용하다.
    *   단, 속성 기반 테스트를 사용하려면 kotest-property 모듈을 의존 관계에 추가해야 한다.
    *   두 객체(수신 객체와 인자로 받은 객체)보다 항상 작거나 같은지 검사할 때 checkAll로 감쌀 수 있다.
    *   단언문 대신 불 값을 반환하는 식을 사용할 수도 있다. 이 경우 forAll 안에 불 값을 반환하는 람다를 넣으면 된다.
    *   forAll과 반대로 모든 테스트 집합 원소에 대해 false를 반환할 때만 성공하는 검사가 필요하면 forNone을 사용할 수 있다.
    *
    *   코테스트 속성 기반 테스트의 생성기는 Gen이라는 추상 클래스를 상속하는데, 크게 임의의 값을 생성하는 생성기와 정해진 값을 모두 돌려주는
    *   생성기로 나뉜다. 두 유형의 생성기를 표현하는 추상 클래스는 다음과 같다.
    *       - Arb : 미리 하드코딩된 에지케이스와 무한한 난수 샘플을 생성해주는 생성기
    *       - Exhaustive : 주어진 공간에 속한 모든 데이터를 생성.
    *
    * */
}

