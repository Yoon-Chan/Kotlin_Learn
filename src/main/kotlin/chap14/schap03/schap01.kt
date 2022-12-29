package chap14.schap03

fun main() {

}

fun c1(){
    /*
    *   픽스처와 설정
    *
    *   실제 테스트를 진행하기 위해 필요한 환경과 자원을 초기화하고 테스트가 끝나면 정리해야 하는 경우가 있다.
    *   이런 테스트 환경을 테스트 픽스처라고 말한다.)
    *   코테스트에서는 TestListener 인터페이스를 구현해 픽스처를 지정할 수 있다.
    *   TestListener 인터페이스는 BeforeTestListener 등으 ㅣ개별 픽스처 인터페이스를 한데 모아둔 리스너다.
    * */

    /*
    *   테스트 설정
    *
    *   config() 함수를 통해 여러가지 테스트 실행 파라미터를 지정할 수 있다.
    *   config() 함수를 사용해 조정할 수 있는 파라미터를 살펴보자
    *       - invocations : 테스트 실행 횟수
    *       - threads : 테스트를 실행할 스레드 개수
    *       - enabled : 테스트를 실행해야 할지 여부
    *       - timeout : 테스트 실행에 걸리는 최대 시간.
    *
    *   defaultConfig() 함수도 있다. 이 함수를 오버라이드해서 한 명세에 속한 모든 테스트 케이스의 설정을 한꺼번에 변경할 수도 있다
    *
    *   테스트 사이에 테스트 케이스 인스턴스를 공유하는 방법을 지정하는 기능이 있다. 이를 격리 모드라고 부른다.
    *   isolationMode 프로퍼티를 오버라이드 해야 한다. 이 프로퍼티는 IsolationMode 이넘에 속하는 세 가지 상수 중 하나를 지정한다.
    *       - SingleInstance : 테스트 케이스의 인스턴스가 하나만 만들어진다. 이 옵션이 디폴트 값
    *       - InstancePerTest : 문맥이나 테스트 블록이 실행될 때마다 케이스의 새 인스턴스를 만든다.
    *       - InstancePerLeaf : (말단에 있는) 개별 테스트 블록을 실행하기 전에 테스트가 인스턴스와 된다.
    *
    * */


}