package chap13.schap01

import kotlinx.coroutines.*
import java.lang.System.*

suspend fun main() {
    c2()
    /*
    *   main 함수가 suspend가 아닌 경우 결과 거의 동시에 끝난다.
    *       Task 2 finished in 152 ms
            Task 1 finished in 152 ms
    *
    *   이 뜻은 두 작업이 실제로 병렬적으로 실행됐다는 점이다.
    *   코루틴 라이브러리는 필요할 때 실행 순서를 강제할 수 있는 도구도 제공한다.
    *
    * */

    //async() 예제 결과 : abcabcabc
    ex1()


    //runBlocking() 예제
    /*
    *   Primary task : kotlinx.coroutines.DefaultExecutor
    *   Background task : DefaultDispatcher-worker-2
    * */
    ex2()

}

fun c1(){
    /*
    *   코루틴 (중요!)
    *
    *   코틀린에서는 코루틴이라는 강력한 메커니즘 덕분에 익숙한 명령형 스타일로 코드를 작성하면
    *   컴파일러가 코드를 효율적인 비동기 계산으로 자동 변환해준다.
    *   이런 메커니즘은 실행을 잠시 중단했다가 나중에 중단한 지점부터 실행을 다시 재개할 수 있는
    *   일시 중단 가능한 함수라는 개념을 중심으로 이뤄진다.
    *
    *   대부분 코루틴 기능이 별도 라이브러리로 제공되기 때문에 명시적으로 프로젝트 설정에 이를 추가해야 한다.
    *
    *   만약 메이븐이나 그레이들 같은 빌드 시스템을 채택하지 않고 인텔리 IDEA를 사용하고 있다면 다음과 같은 단계를 거쳐 코루틴 라이브러리를 추가할 수 있다.
    *
    *   1. Project View 패널에 루트노드에서 f4를 누르거나 마우스 오른쪽 클릭을 해서 Open Module Settins를 선택하자.
    *   2. 왼쪽에 Libraries를 클릭하고 맨 위에 있는 툴바에서 + 버튼을 클릭한 다음, From Maven ... 옵션을 선택하자
    *   3. 라이브러리의 메이븐 아티팩트 좌표를 입력하고 OK를 클릭하라
    *   좌표 예제 (org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3)
    *
    * */

    /*
    *   코루틴과 일시 중단 함수
    *
    *   전체 코루틴 라이브러리를 뒷받침하는 기본 요소는 일시 중단이다. 이 함수는 일반적인 함수를 더 일반화해
    *   함수 본문의 원하는 지점에서 함수에 필요한 모든 런타임 문맥을 저장하고 함수 실행을 중단한 다음,
    *   나중에 필요할 때 다시 실행을 계속 진행할 수 있게 한 것이다.
    *   코틀린에서는 이런 함수에 suspend라는 변경자를 붙인다.
    *
    *   delay함수는 코루틴 라이브러리에 정의된 일시 중단 함수다. 이 함수는 Thread.sleep()과 비슷한 일을 한다.
    *   코틀린은 일반 함수가 일시 중단함수를 호출하는 것을 금지한다.
    *
    *   일반 함수에서 일시 중단 함수를 호출하는 가장 분명한 방법은 main()을 suspend로 표시하는 것이다.
    *
    *   코루틴 빌더는 CoroutineScope 인스턴스의 확장 함수로 쓰인다.
    *   CoroutineScope에 대한 구현 중 가장 기본적인 것으로 GlobalScope 객체가 있다.
    *   이 객체를 사용하면 독립적인 코루틴을 만들 수 있고, 이 코루틴은 자신만의 작업을 내포할 수 있다.
    *   이제 자주 사용하는 launch(), async(), runBlocking()이라는 코루틴 빌더를 살펴본다.
    *
    *
    * */


}

@OptIn(DelicateCoroutinesApi::class)
fun c2(){
    /*
    *   코루틴 빌더
    *
    *   launch() 함수는 코루틴을 시작하고, 코루틴을 실행중인 작업의 상태를 추적하고 변경할 수 있는 Job객체를 돌려준다.
    *   이 함수는 CoroutineScope.() -> Unit 타입의 일시 중단 람다를 받는다.
    *
    *   코루틴은 스레드보다 훨씬 가볍다.
    *   launch() 빌더는 동시성 작업이 결과를 만들어내지 않는 경우에 적합하다.
    *   이 빌더는 Unit 타입을 반환하는 람다를 인자로 받는다
    *   만약 결과가 필요한 경우에는 async()라는 다른 빌더 함수를 사용해야 한다. 이 함수는 Deferred의 인스턴스를 돌려주고,
    *   이 인스턴스는 Job의 하위 타입으로 await() 메서드를 통해 계산 결과에 접근할 수 있게 한다.
    *
    *   runBlocking() 빌더는 디폴트로 현재 스레드에서 실행되는 코루틴을 만들고 코루틴이 완료될 때까지 현재 스레드의 실행을
    *   블럭시킨다. 코루틴이 성공적으로 끝나면 일시 중단 람다의 결과가 runBlocking() 호출의 결괏값이 된다.
    *   코루틴이 취소되면 runBlocking()은 예외를 던진다. 반면에 블럭된 스레드가 인터럽트되면 runBlocking()에 의해 시작된
    *   코루틴도 취소된다.
    *
    *   블러킹 동작 때문에 runBlocking()을 다른 코루틴 안에서 사용하면 안된다.
    *   runBlocking()은 블러킹 호출과 넌블러킹 호출 사이의 다리 역할을 하기 위해 고안된 코루틴 빌더이므로,
    *   테스트나 메인 함수에서 최상위 빌더로 사용하는 등의 경우에만 runBlocking()을 써야 한다.
    * */
    val time = currentTimeMillis()

    GlobalScope.launch {
        delay(100)
        println("Task 1 finished in ${currentTimeMillis()-time} ms")
    }

    GlobalScope.launch {
        delay(100)
        println("Task 2 finished in ${currentTimeMillis()-time} ms")
    }

    Thread.sleep(200)

}
//suspend 변경자
//만약 suspend 변경자를 빼면 delay함수에서 오류가 발생한다.
suspend fun foo(){
    println("Task started")
    delay(100)  // suspend 변경자 없을 시 error : delay is a suspend function
    println("Task finished")
}

//async()예제
suspend fun ex1(){
    val message = GlobalScope.async {
        delay(100)
        "abc"
    }

    val count = GlobalScope.async {
        delay(100)
        1+2
    }

    delay(200)
    val result = message.await().repeat(count.await())
    println(result)

}

//runBlocking() 예제
suspend fun ex2(){
    GlobalScope.launch {
        delay(100)
        println("Background task : ${Thread.currentThread().name}")
    }

    runBlocking {
        println("Primary task : ${Thread.currentThread().name}")
        delay(200)
    }
}