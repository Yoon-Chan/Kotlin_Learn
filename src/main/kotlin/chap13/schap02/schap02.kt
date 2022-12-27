package chap13.schap02

import kotlinx.coroutines.*

suspend fun main() {

    /*
    *   결과
    *   Root 출력 후 에러 발생
    *
    * */
//    ex8()

    /*
    *   실행 결과
    *
    *   Root
        Caught java.lang.Exception: Error in task A
    * */
    //ex9()

    ex10()
}


fun c4(){
    /*
    *   예외 처리
    *   예외 처리의 경우, 코루틴 빌더들은 두 가지 기본 전략 중 하나를 따른다.
    *   첫 번째는 launch() 같은 빌더가 선택한 전략으로, 예외를 부모 코루틴에게 전달하는 것이다.
    *   이 경우 예외는 다음과 같이 전파된다.
    *       - 부모 코루틴이 (자식에게서 발생한 오류와) 똑같은 오류로 취소된다. 이로 인해 부모의 나머지 자식도 모두 취소된다.
    *       - 자식들이 모두 취소되고 나면 부모는 예외를 코루틴 트리의 윗부분으로 전달한다.
    *
    *   핸들러를 만드는 가장 간단한 방법은 인자가 두 개인 람다를 받는 CoroutineExceptionHandler() 함수를 쓰는 것이다.
    *
    *   예외를 처리하는 다른 방법은 async() 빌더에서 사용하는 방법으로, 던져진 예외를 저장했다가
    *   예외가 발생한 계산에 대한 await() 호출을 받았을 때 다시 던지는 것이다.
    * */


}

//예외처리 예제 문제
// 첫 번째 코루틴은 예외를 던져 최상위 작업이 취소되고, 최상위의 자식인 두 작업도 취소된다.
// 아무 커스텀 핸들러도 지정하지 않았기 때문에 프로그램은 Thread.uncaughtExceptionHandler에 등록된 디폴트 동작을 실행한다.
suspend fun ex8(){
    runBlocking {
        launch {
            throw Exception("Error in task A")
            println("Task A completed")
        }

        launch {
            delay(1000)
            println("Task B completed")
        }

        println("Root")
    }
}

//CoroutineExceptionHandler() 예제
suspend fun ex9(){

    val handler = CoroutineExceptionHandler { _, exception ->
        println("Caught $exception")
    }

    GlobalScope.launch(handler) {
        launch {
            throw Exception("Error in task A")
            println("Task A completed")
        }

        launch {
            delay(1000)
            println("Task B completed")
        }

        println("Root")
    }.join()

}

suspend fun ex10(){
    runBlocking {
        val deferred = async {
            throw Exception("Error in task A")
            println("Task A completed")
        }

        val deferredB = async {
            println("Task B completed")
        }

        deferred.await()
        deferredB.await()
        println("Root")
    }

}