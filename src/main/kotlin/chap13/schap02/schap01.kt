package chap13.schap02

import kotlinx.coroutines.*
import java.io.File
import java.lang.Exception
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.atomic.AtomicInteger

suspend fun main() {
/*  ex1() 실행 결과
    Preparing to start...
    Job started
    */
    ex1()

/*
    ex2() 실행 결과
    2 children running
    This is task A
    This is task B
*/
    ex2()

/*
    ex3() 실행 결과
    This is task A
    THis is task B
    0 children running
    */
    ex3()

/*
    ex5() 실행결과 모든 스레드가 끝나기 전에 종료가 된다.
    Parent Started
    Child 1 started
    Child 2 started
    */

    ex5()

/*  ex7() 실행 결과
    실행할 때마다 순서가 다를 수 있다.

    WorkerThread-3
    WorkerThread-1
    WorkerThread-2*/
    ex7()
}


fun c1(){
    /*
    *   코루틴 흐름 제어와 잡 생명 주기
    *
    *   잡(job)은 동시성 작업의생명 주기를 표현하는 객체다.
    *   잡을 사용하면 작업 상태를 추적하고 필요할 때 작업을 취소할 수 있다.
    *
    *   활성화상태(Active)는 작업이 시작됐고 아직 완료나 취소로 끝나지 않았다는 뜻이다.
    *   이 상태가 보통 디폴트 상태이다.
    *   lauch()나 async()는 CoroutineStart 타입의 인자를 지정해서 잡의 초기 상태를 선택하는 기능을 제공한다.
    *       - CoroutineStart.DEFAULT는 디폴트 동작이며, 잡을 즉시 시작한다.
    *       - CoroutineStart.LAZY는 잡을 자동으로 시작하지 말라는 뜻이다. 이 경우에는 잡이 신규 상태가 되고
    *         시작을 기다리게 된다.
    *
    *   신규 상태의 잡에 대해 start()나 join()메서드를 호출하면 잡이 시작되면서 활성화 상태가 도니다.
    *
    *
    *
    * */


}
fun c2(){
    /*
    *   취소
    *   잡의 cancel() 메서드를 호출하면 잡을 취소할 수 있다.
    *   이 메서드는 더 이상 필요 없는 계산을 중단시킬 수 있는 표준적인 방법을 제공한다.
    *   즉, 취소 가능한 코루틴이 스스로 취소가 요청됐는지 검사해서 적절히 반응해줘야 한다.
    *
    *
    *   타임 아웃
    *   작업이 완료되기를 무작정 기다릴 수 없을 경우 타임아웃을 설정할 때가 있다.
    *   코루틴 라이브러리에서 withTimeout() 함수를 제공한다.
    *   withTimeoutOrNull() 이란 함수도 있다. 이 함수는 예외를 던지는 대신 널 값을 돌려준다.
    *
    *
    *   코루틴 디스패치하기
    *
    *   코루틴은 스레드와 무관하게 일시 중단 가능한 계산을 구현할 수 있게 해주지만, 코루틴을 실행하려면
    *   여전히 스레드와 연관시켜야 한다. 코루틴 라이브러리에는 특정 코루틴을 실행할 때 사용할 스레드를 제어하는 작업을 담당하는 특별한 컴포넌트가 있다.
    *   이 컴포넌트를 코루틴 디스패치(dispatcher)라고 부른다.
    * */

}
suspend fun ex1(){
    runBlocking {
        val job = launch(start = CoroutineStart.LAZY) {
            println("Job started")
        }

        delay(100)

        println("Preparing to start...")
        job.start()
    }
}

//children 프로퍼티를 통해 완료되지 않은 자식 잡들을 얻을 수 있다.
suspend fun ex2(){
    runBlocking {
        val job = coroutineContext[Job.Key]!!

        launch { println("This is task A") }
        launch { println("THis is task B") }

        // 2 children running
        println("${job.children.count()} children running")
    }

}

suspend fun ex3(){
    runBlocking {
        val job = coroutineContext[Job.Key]!!

        val jobA = launch { println("This is task A") }
        val jobB = launch { println("THis is task B") }

        jobA.join()
        jobB.join()


        // 2 children running
        println("${job.children.count()} children running")
    }

}

//cancel() 예제
suspend fun ex4(){
    val squarePrinter = GlobalScope.launch(Dispatchers.Default) {
        var i = 1
        //만약 while(true)인 경우 취소 호출이 되어도 계속 계산이 된다.
        //다른 방법으로는 CancellationException을 발생시키면서 취소에 반을할 수 있게
        // 일시 중단 함수를 호출하는 것이다.
        // 이는 잡을 취소하는 과정이 진행 중이라는 사실을 전달하는 토큰 역할을 하기 위해 코루틴 라이브러리 내부에서 쓰이는 예외이다.
        // 코루틴 라이브러리에 정의된 delay()나 join() 등의 모든 일시 중단 함수가 이 예외를 발생시켜준다.
        // 한 가지 예를 추가하면 yield()를 들 수 있다.
        while (isActive){
            println(i++)
        }
    }

    delay(100)
    squarePrinter.cancel()

}


suspend fun ex5(){
    runBlocking {
        val parentJob =launch {
            println("Parent Started")

            launch {
                println("Child 1 started")
                delay(500)
                println("Child 1 completed")
            }

            launch {
                println("Child 2 started")
                delay(500)
                println("Child 2 completed")
            }

            delay(500)
            println("Parent completed")
        }

        delay(100)
        parentJob.cancel()
    }
}

//withTimeout() 함수 예제
//파일을 50밀리초 안에 읽을 수 있다면 withTimeout()은 결과를 돌려주지만
//초과하는 경우 예외를 던쳐 파일 읽는 코루틴이 취소된다.
suspend fun ex6(){
    runBlocking {
        val asyncData = async { File("data.txt").readText() }

        try{
            val text = withTimeout(50) {asyncData.await()}
            println("Data loaded : $text")

        }catch (e : Exception){
            println("Timeout exceeded")
        }
    }
}

//코루틴 디스패처 예제
suspend fun ex7(){
    val id = AtomicInteger(0)

    val executor = ScheduledThreadPoolExecutor(5) {
        runnable ->
        Thread(
            runnable,
            "WorkerThread-${id.incrementAndGet()}"
        ).also { it.isDaemon = true }
    }

    executor.asCoroutineDispatcher().use { dispatcher ->
        runBlocking {
            for(i in 1..3){
                launch(dispatcher) {
                    println(Thread.currentThread().name)
                    delay(1000)
                }
            }
        }
    }
}