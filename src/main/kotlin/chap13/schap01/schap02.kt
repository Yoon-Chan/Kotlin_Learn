package chap13.schap01

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

suspend fun main() {
    ex3()   //main에 suspend 변경자를 안붙이면 오류 발생
    /*  ex3() 함수 실행 결과

    *   Parent task started
        Task A started
        Task B started
        Parent task finished
        Task A finished
        Task B finished
        Shutting down...
    *
    *
    * */

    println()
    println()
/*
    ex4() 실행 결과
    위와 다르게 Task1,2가 모두 끝나면 Custom scope가 끝난다.
    Custom scope start
    Task 1 finished
    Task 2 finished
    Custom scope end

    */
    ex4()
}

fun c3(){
    /*
    *   코루틴 영역과 구조적 동시성
    *
    *   앞 예제는 전역 영역(global scope)에서 실행됐다.
    *   전역 영역이란 코루틴 생명주기가 전체 애플리케이션의 생명 주기에 의해서만 제약되는 영역이다.
    *
    *   어떤 코루틴을 다른 코루틴의 문맥에서 실행하면 후자가 전자의 부모가 된다.
    *   이 경우 자식의 실행이 모두 끝나야 부모가 끝날 수 있도록 부모와 자식의 생명 주기가 연관된다.
    *   이런 기능을 구조적 동시성(structured concurrency)이라고 부르며, 지역 변수 영역 안에서
    *   블럭이나 서브루틴을 사용하는 경우와 구조적 동시성을 비교할 수 있다.
    *
    *   coroutineScope() 호출로 코드 블럭을 감싸면 커스텀 영역을 도입할 수 있다.
    *   이 함수 호출은 람다의 결과를 반환하고, 자식들이 완료되기 전까지 실행이 완료되지 않는다.
    *   이 함수와 runBlocking() 함수의 가장 큰 차이는 coroutinScope()가 일시 중단 함수라 현재 스레드를
    *   블럭시키지 않는다는 점이다.
    *
    *
    * */
}

fun c4(){
    /*
    *   코루틴 문맥
    *
    *   코루틴마다 CoroutineContext 인터페이스로 표현되는 문맥이 연관돼 있으며, 코루틴을 감싸는 변수 영역의
    *   coroutineContext 프로퍼티를 통해 이 문맥에 접근할 수 있다.
    *   문맥은 키-값 쌍으로 이뤄진 불변 컬렉션이며, 코루틴에서 사용할 수 있는 여러 가지 데이터가 들어있다.
    *
    *       코루틴이 실행 중인 취소 가능한 작업을 표현하는 잡(job)
    *       코루틴과 스레드의 연관을 제어하는 디스패처(dispatcher)
    *
    *   일반적으로 문맥은 CoroutineContext.Element를 구현하는 아무 데이터나 저장할 수 있다.
    *   특정 원소에 접근하려면 get() 메서드나 인덱스 연산자에 키를 넘겨야 한다.
    *
    *   기본적으로 launch(), async() 등의 표준 코루틴 빌더에 의해 만들어지는 코루틴은 현재 문맥을 이어받는다.
    *
    * */

}

// 부모 코루틴의 주 본문이 더 빨리 끝나지만 자식이 모두 끝날 때까지 기다린다.
//runBlocking()이 메인 스레드를 블럭하고 있었기 때문에 부모 스레드가 끝나야 메인 스레드의 블럭이 풀리고 마지막 메시지가 출력된다.
suspend fun ex3(){
    runBlocking {
        println("Parent task started")

        launch {
            println("Task A started")
            delay(200)
            println("Task A finished")
        }


        launch {
            println("Task B started")
            delay(200)
            println("Task B finished")
        }

        delay(100)

        println("Parent task finished")
    }

    println("Shutting down...")
}

//coroutineScope()예제
suspend fun ex4(){
    runBlocking {
        println("Custom scope start")

        coroutineScope {
            launch {
                delay(100)
                println("Task 1 finished")
            }
            launch {
                delay(100)
                println("Task 2 finished")
            }
        }

        println("Custom scope end")
    }

}