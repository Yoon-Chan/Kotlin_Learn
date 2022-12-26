package chap13.schap02

import kotlinx.coroutines.*

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
    THis is task B
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